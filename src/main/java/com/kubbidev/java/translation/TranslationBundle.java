package com.kubbidev.java.translation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.kubbidev.java.config.generic.adapter.ConfigurationAdapter;
import com.kubbidev.java.logging.LoggerAdapter;
import com.kubbidev.java.util.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A registry of translations. Used to register localized strings for translation keys.
 */
public class TranslationBundle {

    /**
     * The default locale used by messages
     */
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    private final LoggerAdapter logger;
    private final Set<Locale> installed = ConcurrentHashMap.newKeySet();

    private final Map<Locale, ConfigurationAdapter> translations = new ConcurrentHashMap<>();
    private final Function<Path, ConfigurationAdapter> adapter;

    private final Path translationsDirectory;

    public TranslationBundle(@NotNull LoggerAdapter logger, @NotNull Function<Path, ConfigurationAdapter> adapter, @NotNull Path translationsDirectory) {
        this.logger = logger;
        this.adapter = adapter;
        this.translationsDirectory = translationsDirectory;

        try {
            FileUtil.createDirectoriesIfNotExists(this.translationsDirectory);
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * Reload the translation installed locales from source
     */
    public void reload() {
        this.installed.clear();
        this.translations.clear();

        // Load custom translations first, then the base (built-in) translations after.
        loadFromFileSystem(this.translationsDirectory);
    }

    /**
     * Gets if the provided locale is registered in this translation bundle or not.
     *
     * @param locale to check
     * @return true if installed, otherwise false
     */
    public boolean contains(@NotNull Locale locale) {
        return this.translations.containsKey(locale);
    }

    /**
     * Gets installed locale in the registry.
     *
     * @return a set of installed locales
     */
    public @NotNull Set<Locale> getInstalled() {
        return this.installed;
    }

    /**
     * Gets the strings from the locale provided with the given path.
     *
     * @param path of the strings
     * @param locale of the path to get
     * @return a list of strings from the given locale
     */
    public @NotNull List<String> getStringList(@NotNull String path, @NotNull Locale locale) {
        @NotNull Locale parsedLocale = parseLocale(locale);
        try {
            List<String> translated = this.translations
                    .get(parsedLocale)
                    .getStringList(path, ImmutableList.of());

            /*
                When displaying a list to a scoreboard, we need to reverse the
                list by default, we don't want an upside down scoreboard.
            */
            Collections.reverse(translated);
            return translated;
        } catch (Exception e) {
            return ImmutableList.of(path);
        }
    }

    /**
     * Gets the string from the locale provided with the given path.
     *
     * @param path of the string
     * @param locale of the path to get
     * @return a string from the given locale
     */
    public @NotNull String getString(@NotNull String path, @NotNull Locale locale) {
        @NotNull Locale parsedLocale = parseLocale(locale);
        try {
            return this.translations
                    .get(parsedLocale)
                    .getString(path, path);
        } catch (Exception e) {
            return path;
        }
    }

    /**
     * Parses a {@link Locale} from a {@link String}.
     *
     * @param locale the string
     * @return a locale
     */
    public static @Nullable Locale parseLocale(@Nullable String locale) {

        // Avoid null pointer exception
        if (locale == null) {
            return null;
        }

        String[] segments = locale.split("_", 3); // language_country_variant
        int length = segments.length;
        if (length == 1) {
            return new Locale(locale); // language
        } else if (length == 2) {
            return new Locale(segments[0], segments[1]); // language + country
        } else if (length == 3) {
            return new Locale(segments[0], segments[1], segments[2]); // language + country + variant
        }
        return null;
    }

    private @NotNull Locale parseLocale(@NotNull Locale locale) {
        if (!contains(locale)) {
            // If the locale is not installed, check for the locale without his country code
            locale = new Locale(locale.getLanguage());

            if (!contains(locale)) {
                /*
                    If the given locale is not registered or don't exist,
                    shift back to the default system locale.
                */
                locale = DEFAULT_LOCALE;
            }
        }
        return locale;
    }

    /**
     * Loads custom translations (in any language) from the bot configuration folder.
     */
    private void loadFromFileSystem(Path directory) {
        List<Path> translationFiles;
        try (Stream<Path> stream = Files.list(directory)) {
            translationFiles = stream.filter(path -> path.getFileName().toString().endsWith(".yml")).collect(Collectors.toList());
        } catch (IOException e) {
            translationFiles = Collections.emptyList();
        }

        if (translationFiles.isEmpty()) {
            return;
        }

        Map<Locale, ConfigurationAdapter> loaded = new HashMap<>();
        for (Path translationFile : translationFiles) {
            try {
                Map.Entry<Locale, ConfigurationAdapter> result = loadTranslationFile(translationFile);
                loaded.put(result.getKey(), result.getValue());
            } catch (Exception e) {
                logger.warn("Error loading locale file: {}", translationFile.getFileName());
                e.printStackTrace();
            }

        }

        // try registering the locale without a country code - if we don't already have a registration for that
        loaded.forEach((locale, section) -> {
            Locale localeWithoutCountry = new Locale(locale.getLanguage());
            if (!locale.equals(localeWithoutCountry) && this.installed.add(localeWithoutCountry)) {
                try {
                    this.translations.put(localeWithoutCountry, section);
                } catch (IllegalArgumentException e) {
                    // ignore
                }
            }
        });
    }

    private Map.Entry<Locale, ConfigurationAdapter> loadTranslationFile(Path translationFile) {
        String fileName = translationFile.getFileName().toString();
        String localeString = fileName.substring(0, fileName.length() - ".yml".length());
        Locale locale = TranslationBundle.parseLocale(localeString);

        if (locale == null) {
            throw new IllegalStateException("Unknown locale '" + localeString + "' - unable to register.");
        }

        ConfigurationAdapter section = adapter.apply(translationFile);

        // Register translation by loading the yaml file if not already registered
        this.translations.put(locale, section);
        this.installed.add(locale);
        return Maps.immutableEntry(locale, section);
    }
}