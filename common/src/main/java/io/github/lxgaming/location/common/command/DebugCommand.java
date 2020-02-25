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

package io.github.lxgaming.location.common.command;

import io.github.lxgaming.location.api.exception.CommandException;
import io.github.lxgaming.location.common.LocationImpl;
import io.github.lxgaming.location.common.configuration.Config;
import io.github.lxgaming.location.common.configuration.category.GeneralCategory;
import io.github.lxgaming.location.common.entity.Locale;
import io.github.lxgaming.location.common.util.StringUtils;
import io.github.lxgaming.location.common.util.text.adapter.LocaleAdapter;

import java.util.List;
import java.util.UUID;

public class DebugCommand extends Command {
    
    @Override
    public boolean prepare() {
        addAlias("Debug");
        description("For development purposes.");
        permission("location.debug.base");
        usage("[State]");
        return true;
    }
    
    @Override
    public void execute(UUID uniqueId, List<String> arguments) throws Exception {
        GeneralCategory generalCategory = LocationImpl.getInstance().getConfig().map(Config::getGeneralCategory).orElse(null);
        if (generalCategory == null) {
            LocaleAdapter.sendMessage(uniqueId, Locale.CONFIGURATION_ERROR);
            return;
        }
        
        Boolean state;
        if (!arguments.isEmpty()) {
            String argument = arguments.remove(0);
            if (StringUtils.isNotBlank(argument)) {
                state = StringUtils.toBooleanObject(argument);
                if (state == null) {
                    throw new CommandException(String.format("Failed to parse %s as a boolean", argument));
                }
            } else {
                state = null;
            }
        } else {
            state = null;
        }
        
        if (state != null) {
            generalCategory.setDebug(state);
        } else {
            generalCategory.setDebug(!generalCategory.isDebug());
        }
        
        if (generalCategory.isDebug()) {
            LocaleAdapter.sendMessage(uniqueId, Locale.COMMAND_DEBUG_ENABLE);
        } else {
            LocaleAdapter.sendMessage(uniqueId, Locale.COMMAND_DEBUG_DISABLE);
        }
    }
}