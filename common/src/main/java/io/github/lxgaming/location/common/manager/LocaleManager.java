/*
 * Copyright 2020 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.location.common.manager;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.common.LocationImpl;
import io.github.lxgaming.location.common.configuration.Config;
import io.github.lxgaming.location.common.configuration.category.GeneralCategory;
import io.github.lxgaming.location.common.entity.Locale;
import io.github.lxgaming.location.common.util.StringUtils;
import io.github.lxgaming.location.common.util.Toolbox;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

public final class LocaleManager {

    public static final char CHARACTER = '&';
    public static final char HEX_CHARACTER = '#';
    public static final String PLACEHOLDER_START = "{";
    public static final String PLACEHOLDER_END = "}";
    private static final Map<String, String> LOCALE = Maps.newHashMap();
    private static LegacyComponentSerializer legacyComponentSerializer;

    public static void prepare() {
        if (loadLocale(GeneralCategory.DEFAULT_LOCALE)) {
            LocationImpl.getInstance().getLogger().info("Loaded default locale");
        } else {
            LocationImpl.getInstance().getLogger().warn("Failed to load default locale");
        }

        GeneralCategory generalCategory = LocationImpl.getInstance().getConfig().map(Config::getGeneralCategory).orElseThrow(NullPointerException::new);
        if (!generalCategory.getLocale().equals(GeneralCategory.DEFAULT_LOCALE)) {
            if (loadLocale(generalCategory.getLocale())) {
                LocationImpl.getInstance().getLogger().info("Loaded {} locale", generalCategory.getLocale());
            } else {
                LocationImpl.getInstance().getLogger().warn("Failed to load {} locale", generalCategory.getLocale());
            }
        }

        for (Map.Entry<String, List<String>> entry : generalCategory.getLocaleOverrides().entrySet()) {
            LOCALE.put(entry.getKey(), String.join("\n", entry.getValue()));
        }

        legacyComponentSerializer = LegacyComponentSerializer.builder()
                .hexColors()
                .hexCharacter(HEX_CHARACTER)
                .extractUrls()
                .character(CHARACTER)
                .build();
    }

    public static Component serialize(Locale locale, Object... arguments) {
        return serialize(locale.getKey(), arguments);
    }

    public static Component serialize(String key, Object... arguments) {
        String translation = getTranslation(key);
        if (translation == null) {
            return Component.text("Failed to translate message", NamedTextColor.RED);
        }

        int matches = StringUtils.countMatches(translation, PLACEHOLDER_START + PLACEHOLDER_END);
        if (matches != arguments.length) {
            LocationImpl.getInstance().getLogger().warn("Incorrect Arguments for {}. Expected {}, got {}", key, matches, arguments.length);
        }

        String format = format(translation, arguments);
        if (StringUtils.isEmpty(format)) {
            return Component.text("Failed to format message", NamedTextColor.RED);
        }

        return legacyComponentSerializer.deserialize(format);
    }

    private static String format(String format, Object... arguments) {
        StringBuilder stringBuilder = new StringBuilder(format);
        int startIndex = 0;
        int endIndex = 0;
        int argumentIndex = 0;
        while ((startIndex = stringBuilder.indexOf(PLACEHOLDER_START, startIndex)) != -1 && ((endIndex = stringBuilder.indexOf(PLACEHOLDER_END, endIndex)) != -1)) {
            int length = stringBuilder.length();

            if ((endIndex - startIndex) == PLACEHOLDER_START.length()) {
                if (argumentIndex >= arguments.length) {
                    endIndex += PLACEHOLDER_END.length();
                    startIndex = endIndex;
                    continue;
                }

                stringBuilder.replace(startIndex, endIndex + PLACEHOLDER_END.length(), StringUtils.toString(arguments[argumentIndex]));
                argumentIndex += 1;
            } else {
                String key = stringBuilder.substring(startIndex + PLACEHOLDER_START.length(), endIndex);
                String value = getTranslation(key);
                if (value != null) {
                    stringBuilder.replace(startIndex, endIndex + PLACEHOLDER_END.length(), value);
                } else {
                    stringBuilder.delete(startIndex + PLACEHOLDER_START.length(), endIndex);
                }
            }

            int difference = stringBuilder.length() - length;
            startIndex += difference;
            endIndex += difference;
        }

        return stringBuilder.toString();
    }

    private static boolean loadLocale(String name) {
        Map<String, String> locale = deserializeLocaleFile(String.format("/assets/%s/locale/%s.json", Location.ID, name));
        if (locale != null) {
            LOCALE.putAll(locale);
            return true;
        }

        return false;
    }

    private static Map<String, String> deserializeLocaleFile(String name) {
        InputStream inputStream = LocaleManager.class.getResourceAsStream(name);
        if (inputStream == null) {
            LocationImpl.getInstance().getLogger().warn("Resource {} doesn't exist", name);
            return null;
        }

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            JsonObject jsonObject = Toolbox.GSON.fromJson(reader, JsonObject.class);
            if (jsonObject == null) {
                throw new JsonParseException(String.format("Failed to parse locale %s", name));
            }

            Map<String, String> translations = Maps.newHashMap();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                translations.put(entry.getKey(), StringUtils.toString(entry.getValue()));
            }

            return translations;
        } catch (Exception ex) {
            LocationImpl.getInstance().getLogger().error("Encountered an error while deserializing {}", name, ex);
            return null;
        }
    }

    private static String getTranslation(String key) {
        String translation = LOCALE.get(key);
        if (translation == null) {
            LocationImpl.getInstance().getLogger().warn("Missing translation for {}", key);
            return null;
        }

        return translation;
    }
}