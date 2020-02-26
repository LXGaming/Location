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

import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.common.entity.Locale;
import io.github.lxgaming.location.common.manager.CommandManager;
import io.github.lxgaming.location.common.manager.LocaleManager;
import io.github.lxgaming.location.common.util.StringUtils;
import io.github.lxgaming.location.common.util.text.adapter.LocaleAdapter;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;

import java.util.List;
import java.util.UUID;

public class HelpCommand extends Command {
    
    @Override
    public boolean prepare() {
        addAlias("Help");
        addAlias("?");
        permission("location.help.base");
        return true;
    }
    
    @Override
    public void execute(UUID uniqueId, List<String> arguments) throws Exception {
        LocaleAdapter.sendMessage(uniqueId, Locale.GENERAL_PREFIX);
        for (Command command : CommandManager.COMMANDS) {
            if (command == this || !(StringUtils.isNotBlank(command.getPermission()) && Location.getPlatform().hasPermission(uniqueId, command.getPermission()))) {
                continue;
            }
            
            String usage = "/" + CommandManager.getPrefix() + " " + String.join(" ", command.getPath()).toLowerCase();
            if (StringUtils.isNotBlank(command.getUsage())) {
                usage += " " + command.getUsage();
            }
            
            TextComponent description = LocaleManager.serialize(Locale.COMMAND_HELP_HOVER,
                    command.getPrimaryAlias().orElse("unknown"),
                    StringUtils.defaultIfEmpty(command.getDescription(), "No description provided"),
                    usage,
                    StringUtils.defaultIfEmpty(command.getPermission(), "None")
            );
            
            TextComponent component = TextComponent.builder()
                    .clickEvent(ClickEvent.of(ClickEvent.Action.SUGGEST_COMMAND, "/" + CommandManager.getPrefix() + " " + String.join(" ", command.getPath()).toLowerCase()))
                    .hoverEvent(HoverEvent.of(HoverEvent.Action.SHOW_TEXT, description))
                    .append(LocaleManager.serialize(Locale.COMMAND_HELP, usage))
                    .build();
            
            Location.getPlatform().sendMessage(uniqueId, component);
        }
    }
}