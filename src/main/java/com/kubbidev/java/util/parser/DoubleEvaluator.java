package com.kubbidev.java.util.parser;

public class DoubleEvaluator {

    /**
     * A constant DoubleEvaluator instance with the value "0".
     */
    public static final DoubleEvaluator ZERO = new DoubleEvaluator("0");

    private final String expression;
    private int pos = -1;
    private int ch;

    /**
     * Constructs a new DoubleEvaluator instance with the given mathematical expression.
     *
     * @param expression The mathematical expression to be evaluated.
     */
    public DoubleEvaluator(String expression) {
        this.expression = expression;
    }

    private void nextChar() {
        this.ch = (++this.pos < this.expression.length()) ? this.expression.charAt(this.pos) : -1;
    }

    private boolean eat(int charToEat) {
        while (this.ch == ' ') nextChar();
        if (this.ch == charToEat) {
            nextChar();

            return true;
        }
        return false;
    }

    // Grammar:
    // expression = term | expression `+` term | expression `-` term
    // term = factor | term `*` factor | term `/` factor
    // factor = `+` factor | `-` factor | `(` expression `)` | number
    //        | functionName `(` expression `)` | functionName factor
    //        | factor `^` factor

    private double parseExpression() throws SyntaxErrorException {
        double x = parseTerm();
        for (; ; ) {
            if (eat('+')) x += parseTerm(); // addition
            else if (eat('-')) x -= parseTerm(); // subtraction
            else return x;
        }
    }

    private double parseTerm() throws SyntaxErrorException {
        double x = parseFactor();
        for (; ; ) {
            if (eat('*')) x *= parseFactor(); // multiplication
            else if (eat('/')) x /= parseFactor(); // division
            else return x;
        }
    }

    private double parseFactor() throws SyntaxErrorException {
        if (eat('+')) return +parseFactor(); // unary plus
        if (eat('-')) return -parseFactor(); // unary minus

        double x;
        int startPos = this.pos;

        if (eat('(')) { // parentheses
            x = parseExpression();
            if (!eat(')'))
                throw new SyntaxErrorException("Missing ')'");

        } else if ((this.ch >= '0' && this.ch <= '9') || this.ch == '.') { // numbers
            while ((this.ch >= '0' && this.ch <= '9') || this.ch == '.') nextChar();

            x = Double.parseDouble(expression.substring(startPos, this.pos));
        } else if (this.ch >= 'a' && this.ch <= 'z') { // functions
            while (this.ch >= 'a' && this.ch <= 'z') nextChar();

            String func = this.expression.substring(startPos, this.pos);
            if (eat('(')) {
                x = parseExpression();
                if (!eat(')')) throw new SyntaxErrorException("Missing ')' after argument to " + func);
            } else {
                x = parseFactor();
            }
            switch (func) {
                case "sqrt" -> x = Math.sqrt(x);
                case "sin" -> x = Math.sin(x);
                case "cos" -> x = Math.cos(x);
                case "tan" -> x = Math.tan(x);
                case "abs" -> x = Math.abs(x);
                case "ceil" -> x = Math.ceil(x);
                case "floor" -> x = Math.floor(x);
                case "atan" -> x = Math.atan(x);
                case "round" -> x = Math.round(x);
                case "exp" -> x = Math.exp(x);
            }
        } else {
            throw new SyntaxErrorException("Unexpected: " + (char) this.ch);
        }
        if (eat('^'))
            x = Math.pow(x, parseFactor()); // exponentiation

        return x;
    }

    /**
     * Evaluates the mathematical expression and returns the resulting double value.
     *
     * @return The evaluated double value of the mathematical expression.
     * @throws SyntaxErrorException If there is a syntax error in the expression or unknown functions are used.
     */
    public double evaluate() throws SyntaxErrorException {
        nextChar();

        double x = parseExpression();
        if (this.pos < this.expression.length())
            throw new SyntaxErrorException("Unexpected: " + (char) this.ch);
        return x;
    }
}