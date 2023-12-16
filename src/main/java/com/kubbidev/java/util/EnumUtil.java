package com.kubbidev.java.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class EnumUtil {

    private EnumUtil() {
        throw new AssertionError("No com.kubbidev.java.util.EnumUtil instances for you!");
    }

    /**
     * Returns a comma-separated string of all enum constants in the given class.
     *
     * @param clazz The class of the enum.
     * @return A string representation of all enum constants.
     */
    public static String getEnums(Class<?> clazz) {
        StringBuilder str = new StringBuilder();
        for (String enumName : getEnumsList(clazz)) {
            if (enumName == null)
                continue;

            if (!str.isEmpty()) {
                str.append(",");
            }
            str.append(enumName);
        }
        return str.toString();
    }

    /**
     * Returns a list of all enum constant names in the given class.
     *
     * @param clazz The class of the enum.
     * @return A list containing the names of all enum constants.
     */
    public static List<String> getEnumsList(Class<?> clazz) {
        List<String> list = new ArrayList<>();
        if (!clazz.isEnum())
            return list;

        for (Object enumName : clazz.getEnumConstants()) {
            if (enumName == null)
                continue;

            list.add(enumName.toString());
        }
        return list;
    }

    /**
     * Toggles an enum value to the next one in sequence.
     *
     * @param en The current enum value.
     * @param <T> The type of the enum.
     * @return The next enum value in sequence.
     */
    public static <T extends Enum<T>> T toggleEnum(Enum<T> en) {
        T[] values = en.getDeclaringClass().getEnumConstants();

        int next = en.ordinal() + 1;
        return values[next >= values.length ? 0 : next];
    }

    /**
     * Gets an enum value of the specified type by its name.
     *
     * @param key       The name of the enum value.
     * @param enumClass The class of the enum.
     * @param <T>       The type of the enum.
     * @return The enum value, or null if the value does not exist.
     */
    public static <T extends Enum<T>> T getEnum(String key, Class<T> enumClass) {
        try {
            return Enum.valueOf(enumClass, StringUtil.enumCase(key));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets an enum value of the specified type by its name.
     *
     * @param key       The name of the enum value.
     * @param enumClass The class of the enum.
     * @param <T>       The type of the enum.
     * @return An optional containing the enum value, or an empty optional if the value does not exist.
     */
    public static <T extends Enum<T>> Optional<T> getIfPresent(String key, Class<T> enumClass) {
        if (!enumClass.isEnum())
            return Optional.empty();

        for (T constant : enumClass.getEnumConstants()) {
            if (constant.name().equals(key))
                return Optional.of(constant);
        }

        return Optional.empty();
    }
}
