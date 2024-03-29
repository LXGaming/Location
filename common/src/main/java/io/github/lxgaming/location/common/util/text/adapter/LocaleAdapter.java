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

package io.github.lxgaming.location.common.util.text.adapter;

import io.github.lxgaming.location.api.entity.Source;
import io.github.lxgaming.location.common.entity.Locale;
import io.github.lxgaming.location.common.manager.LocaleManager;

public class LocaleAdapter {

    public static void sendActionBar(Source source, Locale locale, Object... arguments) {
        source.sendActionBar(LocaleManager.serialize(locale, arguments));
    }

    public static void sendMessage(Source source, Locale locale, Object... arguments) {
        source.sendMessage(LocaleManager.serialize(locale, arguments));
    }
}