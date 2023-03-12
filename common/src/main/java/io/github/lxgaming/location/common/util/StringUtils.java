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

package io.github.lxgaming.location.common.util;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.Collection;
import java.util.Set;

public class StringUtils {

    private static final Set<String> FALSE = ImmutableSet.of("f", "n", "no", "off", "false");
    private static final Set<String> TRUE = ImmutableSet.of("t", "y", "on", "yes", "true");

    public static String capitalize(String string) {
        int length;
        if (string == null || (length = string.length()) == 0) {
            return string;
        }

        int firstCodePoint = string.codePointAt(0);
        int newCodePoint = Character.toTitleCase(firstCodePoint);
        if (firstCodePoint == newCodePoint) {
            return string;
        }

        int[] newCodePoints = new int[length];
        int outOffset = 0;
        newCodePoints[outOffset++] = newCodePoint;
        for (int inOffset = Character.charCount(firstCodePoint); inOffset < length; ) {
            int codePoint = string.codePointAt(inOffset);
            newCodePoints[outOffset++] = codePoint;
            inOffset += Character.charCount(codePoint);
        }

        return new String(newCodePoints, 0, outOffset);
    }

    public static boolean containsIgnoreCase(Collection<String> collection, String targetString) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }

        for (String string : collection) {
            if (string.equalsIgnoreCase(targetString)) {
                return true;
            }
        }

        return false;
    }

    public static int countMatches(String string, String searchString) {
        if (string == null || string.length() == 0 || searchString == null || searchString.length() == 0) {
            return 0;
        }

        int count = 0;
        int index = 0;
        while ((index = string.indexOf(searchString, index)) != -1) {
            count++;
            index += searchString.length();
        }

        return count;
    }

    public static <T extends CharSequence> T defaultIfBlank(T string, T defaultString) {
        if (isBlank(string)) {
            return defaultString;
        }

        return string;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T string, T defaultString) {
        if (isEmpty(string)) {
            return defaultString;
        }

        return string;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isBlank(CharSequence charSequence) {
        int length;
        if (charSequence == null || (length = charSequence.length()) == 0) {
            return true;
        }

        for (int index = 0; index < length; index++) {
            if (!Character.isWhitespace(charSequence.charAt(index))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }

    public static boolean isNumeric(CharSequence charSequence) {
        int length;
        if (charSequence == null || (length = charSequence.length()) == 0) {
            return false;
        }

        for (int index = 0; index < length; index++) {
            if (!Character.isDigit(charSequence.charAt(index))) {
                return false;
            }
        }

        return true;
    }

    public static String truncate(String string, int length, String suffix) {
        if (string.length() >= length) {
            return string.substring(0, length) + suffix;
        }

        return string;
    }

    public static Boolean toBooleanObject(String string) {
        if (string == null) {
            return null;
        }

        if (containsIgnoreCase(FALSE, string)) {
            return Boolean.FALSE;
        }

        if (containsIgnoreCase(TRUE, string)) {
            return Boolean.TRUE;
        }

        return null;
    }

    public static String toString(Object object) {
        if (object != null) {
            return object.toString();
        }

        return "null";
    }

    public static String toString(JsonElement jsonElement) throws UnsupportedOperationException {
        if (jsonElement instanceof JsonPrimitive) {
            return jsonElement.getAsString();
        }

        if (jsonElement instanceof JsonArray) {
            return toString((JsonArray) jsonElement);
        }

        throw new UnsupportedOperationException(String.format("%s is not supported", jsonElement.getClass().getSimpleName()));
    }

    public static String toString(JsonArray jsonArray) throws UnsupportedOperationException {
        StringBuilder stringBuilder = new StringBuilder();
        for (JsonElement jsonElement : jsonArray) {
            if (!(jsonElement instanceof JsonPrimitive)) {
                throw new UnsupportedOperationException(String.format("%s is not supported inside a JsonArray", jsonElement.getClass().getSimpleName()));
            }

            if (stringBuilder.length() != 0) {
                stringBuilder.append("\n");
            }

            stringBuilder.append(jsonElement.getAsString());
        }

        return stringBuilder.toString();
    }
}