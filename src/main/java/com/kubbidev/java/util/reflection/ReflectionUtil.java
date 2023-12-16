package com.kubbidev.java.util.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public final class ReflectionUtil {

    private ReflectionUtil() {
        throw new AssertionError("No com.kubbidev.java.util.reflection.ReflectionUtil instances for you!");
    }

    private static final Map<Class<?>, Class<?>> builtInMap = new HashMap<>();

    static {
        builtInMap.put(Integer.class, Integer.TYPE);
        builtInMap.put(Long.class, Long.TYPE);
        builtInMap.put(Double.class, Double.TYPE);
        builtInMap.put(Float.class, Float.TYPE);
        builtInMap.put(Boolean.class, Boolean.TYPE);
        builtInMap.put(Character.class, Character.TYPE);
        builtInMap.put(Byte.class, Byte.TYPE);
        builtInMap.put(Short.class, Short.TYPE);
    }

    /**
     * Checks if the specified class exists in the classpath.
     *
     * @param clazz The name of the class to check.
     * @return true if the class exists, false otherwise.
     */
    public static boolean classExists(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Checks if any of the specified classes exist in the classpath.
     *
     * @param classNames The names of the classes to check.
     * @return true if any of the classes exist, false otherwise.
     */
    public static boolean classExists(String... classNames) {
        for (String className : classNames) {
            if (classExists(className)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieves an Enum constant from the given Enum class and constant name.
     *
     * @param clazz    The Enum class.
     * @param constant The name of the Enum constant.
     * @return The Enum constant.
     * @throws ReflectiveOperationException if the Enum constant is not found.
     */
    public static Enum<?> getEnum(Class<?> clazz, String constant) throws ReflectiveOperationException {
        Enum<?>[] enumConstants = (Enum<?>[]) clazz.getEnumConstants();
        for (Enum<?> e : enumConstants) {
            if (e.name().equalsIgnoreCase(constant)) {
                return e;
            }
        }
        throw new ReflectiveOperationException(String.format("Enum constant not found %s", constant));
    }

    /**
     * Retrieves the Enum constant at the specified ordinal position from the given Enum class.
     *
     * @param clazz   The Enum class.
     * @param ordinal The ordinal position of the Enum constant.
     * @return The Enum constant at the specified ordinal.
     * @throws ReflectiveOperationException if the Enum constant is not found.
     */
    public static Enum<?> getEnum(Class<?> clazz, int ordinal) throws ReflectiveOperationException {
        try {
            return (Enum<?>) clazz.getEnumConstants()[ordinal];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReflectiveOperationException(String.format("Enum constant not found %s", ordinal));
        }
    }

    /**
     * Retrieves the Enum constant with the given constant name from the specified Enum class.
     *
     * @param clazz    The Enum class.
     * @param enumName The name of the nested Enum class that contains the constant.
     * @param constant The name of the Enum constant to retrieve.
     * @return The Enum constant with the given constant name.
     * @throws ReflectiveOperationException if the Enum constant or nested Enum class is not found.
     */
    public static Enum<?> getEnum(Class<?> clazz, String enumName, String constant) throws ReflectiveOperationException {
        return getEnum(getSubClass(clazz, enumName), constant);
    }

    /**
     * Retrieves the subclass of the given class with the specified name.
     *
     * @param clazz      The class from which to retrieve the subclass.
     * @param className  The name of the subclass to retrieve.
     * @return The subclass of the given class with the specified name.
     * @throws ReflectiveOperationException if the subclass with the specified name is not found.
     */
    private static Class<?> getSubClass(Class<?> clazz, String className) throws ReflectiveOperationException {
        for (Class<?> subClass : clazz.getDeclaredClasses()) {
            if (subClass.getSimpleName().equals(className))
                return subClass;
        }

        for (Class<?> subClass : clazz.getClasses()) {
            if (subClass.getSimpleName().equals(className))
                return subClass;
        }

        throw new ClassNotFoundException("Sub class " + className + " of " + clazz.getSimpleName() + " not found!");
    }

    /**
     * Retrieves the Field object for the field with the specified name from the given class.
     *
     * @param clazz      The class from which to retrieve the field.
     * @param fieldName  The name of the field to retrieve.
     * @return The Field object representing the field with the specified name.
     * @throws ReflectiveOperationException if the field with the specified name is not found
     *                                       or cannot be accessed.
     */
    public static Field getField(Class<?> clazz, String fieldName) throws ReflectiveOperationException {
        Field f;
        try {
            f = clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            f = clazz.getField(fieldName);
        }
        f.setAccessible(true);
        return f;
    }

    /**
     * Retrieves the Method object for the method with the specified name from the given class.
     *
     * @param clazz         The class from which to retrieve the method.
     * @param methodName    The name of the method to retrieve.
     * @return The Method object representing the method with the specified name.
     * @throws ReflectiveOperationException if the method with the specified name is not found
     *                                       or cannot be accessed.
     */
    private static Method getMethod(Class<?> clazz, String methodName) throws ReflectiveOperationException {
        Method m;
        try {
            m = clazz.getDeclaredMethod(methodName);
        } catch (Exception e) {
            m = clazz.getMethod(methodName);
        }
        m.setAccessible(true);
        return m;
    }

    /**
     * Retrieves the Method object for the method with the specified name and parameter types
     * from the given class.
     *
     * @param clazz         The class from which to retrieve the method.
     * @param methodName    The name of the method to retrieve.
     * @param args          The parameter types of the method.
     * @return The Method object representing the method with the specified name and parameter types.
     * @throws ReflectiveOperationException if the method with the specified name and parameter types
     *                                       is not found or cannot be accessed.
     */
    private static Method getMethod(Class<?> clazz, String methodName, Class<?>... args) throws ReflectiveOperationException {
        Method m;
        try {
            m = clazz.getDeclaredMethod(methodName, args);
        } catch (Exception e) {
            m = clazz.getMethod(methodName, args);
        }
        m.setAccessible(true);
        return m;
    }

    /**
     * Retrieves the value of the field with the specified name from the given object and casts it
     * to the specified type.
     *
     * @param obj         The object from which to retrieve the field value.
     * @param fieldName   The name of the field to retrieve.
     * @param tClass      The class representing the type to which the field value should be cast.
     * @param <T>         The generic type to which the field value should be cast.
     * @return The value of the field with the specified name, cast to the specified type.
     * @throws ReflectiveOperationException if the field with the specified name is not found
     *                                       or cannot be accessed, or if the field value cannot be cast
     *                                       to the specified type.
     */
    public static <T> T getObject(Object obj, String fieldName, Class<T> tClass) throws ReflectiveOperationException {
        return tClass.cast(getField(obj.getClass(), fieldName).get(obj));
    }

    /**
     * Retrieves the value of the field with the specified type name from the given object.
     *
     * @param obj         The object from which to retrieve the field value.
     * @param typeName    The name of the type of the field to retrieve.
     * @return The value of the field with the specified type name.
     * @throws ReflectiveOperationException if the field with the specified type name is not found
     *                                       or cannot be accessed.
     */
    public static Object getFieldByType(Object obj, String typeName) throws ReflectiveOperationException {
        return getFieldByType(obj, obj.getClass(), typeName);
    }

    private static Object getFieldByType(Object obj, Class<?> superClass, String typeName) throws ReflectiveOperationException {
        return getFieldByTypeList(obj, superClass, typeName).get(0);
    }

    /**
     * Retrieves a list of values of all fields with the specified type name from the given object.
     *
     * @param obj         The object from which to retrieve the field values.
     * @param typeName    The name of the type of fields to retrieve.
     * @return A list containing the values of all fields with the specified type name.
     * @throws ReflectiveOperationException if the fields with the specified type name are not found
     *                                       or cannot be accessed.
     */
    public static List<Object> getFieldByTypeList(Object obj, String typeName) throws ReflectiveOperationException {
        return getFieldByTypeList(obj, obj.getClass(), typeName);
    }

    private static List<Object> getFieldByTypeList(Object obj, Class<?> superClass, String typeName) throws ReflectiveOperationException {

        List<Object> fields = new ArrayList<>();
        for (Field f : superClass.getDeclaredFields()) {

            if (f.getType().getSimpleName().equalsIgnoreCase(typeName)) {
                f.setAccessible(true);

                fields.add(f.get(obj));
            }
        }
        if (superClass.getSuperclass() != null) {
            fields.addAll(getFieldByTypeList(obj, superClass.getSuperclass(), typeName));
        }

        if (fields.isEmpty() && obj.getClass() == superClass) {
            throw new ReflectiveOperationException("Could not find field of type " + typeName + " in " + obj.getClass().getSimpleName());
        } else {
            return fields;
        }
    }

    /**
     * Invokes the constructor of the given class with the specified parameter types
     * and initializes the object with the provided arguments.
     *
     * @param clazz      The class whose constructor to invoke.
     * @param args       The parameter types of the constructor.
     * @param initArgs   The arguments to pass to the constructor for initialization.
     * @return The new instance of the class created by the constructor invocation.
     * @throws ReflectiveOperationException if the constructor with the specified parameter types
     *                                       is not found or cannot be accessed, or if object creation fails.
     */
    public static Object invokeConstructor(Class<?> clazz, Class<?>[] args, Object... initArgs) throws ReflectiveOperationException {
        return getConstructor(clazz, args).newInstance(initArgs);
    }

    /**
     * Invokes the constructor of the given class with the specified arguments
     * and initializes the object with the provided arguments.
     *
     * @param clazz      The class whose constructor to invoke.
     * @param initArgs   The arguments to pass to the constructor for initialization.
     * @return The new instance of the class created by the constructor invocation.
     * @throws ReflectiveOperationException if the constructor with the specified arguments
     *                                       is not found or cannot be accessed, or if object creation fails.
     */
    public static Object invokeConstructor(Class<?> clazz, Object... initArgs) throws ReflectiveOperationException {
        return getConstructorByArgs(clazz, initArgs).newInstance(initArgs);
    }

    private static Constructor<?> getConstructor(Class<?> clazz, Class<?>... args) throws ReflectiveOperationException {
        Constructor<?> c = clazz.getConstructor(args);
        c.setAccessible(true);

        return c;
    }

    private static Constructor<?> getConstructorByArgs(Class<?> clazz, Object... args) throws ReflectiveOperationException {

        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterTypes().length != args.length) {
                continue;
            }

            int i = 0;
            for (Class<?> parameter : constructor.getParameterTypes()) {
                if (!isAssignable(parameter, args[i])) {
                    break;
                }

                i++;
            }

            if (i == args.length) {
                return constructor;
            }
        }

        throw new ReflectiveOperationException(String.format("Could not find constructor with args %s in %s", Arrays.stream(args)
                        .map(s -> s.getClass().getSimpleName())
                        .collect(Collectors.joining(", ")),
                clazz.getSimpleName()));
    }

    private static boolean isAssignable(Class<?> clazz, Object obj) {

        clazz = convertToPrimitive(clazz);
        return clazz.isInstance(obj) || clazz == convertToPrimitive(obj.getClass());
    }

    public static Class<?> convertToPrimitive(Class<?> clazz) {
        return builtInMap.getOrDefault(clazz, clazz);
    }

    /**
     * Invokes the method with the specified name on the given object.
     *
     * @param clazz      The class of the object whose method to invoke.
     * @param obj        The object on which to invoke the method.
     * @param method     The name of the method to invoke.
     * @return The result of invoking the method on the object.
     * @throws ReflectiveOperationException if the method with the specified name is not found,
     *                                       or if the method cannot be accessed or invoked.
     */
    public static Object invokeMethod(Class<?> clazz, Object obj, String method) throws ReflectiveOperationException {
        return Objects.requireNonNull(getMethod(clazz, method)).invoke(obj);
    }

    /**
     * Invokes the method with the specified name and parameter types on the given object.
     *
     * @param clazz      The class of the object whose method to invoke.
     * @param obj        The object on which to invoke the method.
     * @param method     The name of the method to invoke.
     * @param args       The parameter types of the method.
     * @param initArgs   The arguments to pass to the method for invocation.
     * @return The result of invoking the method on the object.
     * @throws ReflectiveOperationException if the method with the specified name and parameter types
     *                                       is not found, or if the method cannot be accessed or invoked.
     */
    public static Object invokeMethod(Class<?> clazz, Object obj, String method, Class<?>[] args, Object... initArgs) throws ReflectiveOperationException {
        return Objects.requireNonNull(getMethod(clazz, method, args)).invoke(obj, initArgs);
    }

    /**
     * Invokes the method with the specified name on the given object.
     *
     * @param obj        The object on which to invoke the method.
     * @param method     The name of the method to invoke.
     * @return The result of invoking the method on the object.
     * @throws ReflectiveOperationException if the method with the specified name is not found,
     *                                       or if the method cannot be accessed or invoked.
     */
    public static Object invokeMethod(Object obj, String method) throws ReflectiveOperationException {
        return Objects.requireNonNull(getMethod(obj.getClass(), method)).invoke(obj);
    }
}
