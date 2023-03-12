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

package io.github.lxgaming.location.common.configuration.category;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GeneralCategory {

    public static final String DEFAULT_LOCALE = "en_us";

    @SerializedName("debug")
    private boolean debug = false;

    @SerializedName("locale")
    private String locale = DEFAULT_LOCALE;

    @SerializedName("localeOverrides")
    private Map<String, List<String>> localeOverrides = Maps.newHashMap();

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Map<String, List<String>> getLocaleOverrides() {
        return localeOverrides;
    }
}