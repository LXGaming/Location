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
import io.github.lxgaming.location.common.util.StringUtils;
import io.github.lxgaming.location.common.util.text.adapter.LocaleAdapter;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.format.TextColor;

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
            
            TextComponent.Builder textBuilder = TextComponent.builder("");
            textBuilder.clickEvent(ClickEvent.of(ClickEvent.Action.SUGGEST_COMMAND, "/" + CommandManager.getPrefix() + " " + String.join(" ", command.getPath()).toLowerCase()));
            textBuilder.hoverEvent(HoverEvent.of(HoverEvent.Action.SHOW_TEXT, buildDescription(command)));
            textBuilder.append(TextComponent.of("> ", TextColor.BLUE));
            textBuilder.append(TextComponent.of("/" + CommandManager.getPrefix() + " " + String.join(" ", command.getPath()).toLowerCase(), TextColor.GREEN));
            if (StringUtils.isNotBlank(command.getUsage())) {
                textBuilder.append(TextComponent.of(" " + command.getUsage(), TextColor.GREEN));
            }
            
            Location.getPlatform().sendMessage(uniqueId, textBuilder.build());
        }
    }
    
    private TextComponent buildDescription(Command command) {
        TextComponent.Builder textBuilder = TextComponent.builder("");
        textBuilder.append(TextComponent.of("Command: ", TextColor.AQUA));
        textBuilder.append(TextComponent.of(command.getPrimaryAlias().orElse("unknown"), TextColor.DARK_GREEN));
        textBuilder.append(TextComponent.newline());
        textBuilder.append(TextComponent.of("Description: ", TextColor.AQUA));
        textBuilder.append(TextComponent.of(StringUtils.defaultIfEmpty(command.getDescription(), "No description provided"), TextColor.DARK_GREEN));
        textBuilder.append(TextComponent.newline());
        textBuilder.append(TextComponent.of("Usage: ", TextColor.AQUA));
        textBuilder.append(TextComponent.of("/" + CommandManager.getPrefix() + " " + String.join(" ", command.getPath()).toLowerCase(), TextColor.DARK_GREEN));
        if (StringUtils.isNotBlank(command.getUsage())) {
            textBuilder.append(TextComponent.of(" " + command.getUsage(), TextColor.DARK_GREEN));
        }
        
        textBuilder.append(TextComponent.newline());
        textBuilder.append(TextComponent.of("Permission: ", TextColor.AQUA));
        textBuilder.append(TextComponent.of(StringUtils.defaultIfEmpty(command.getPermission(), "None"), TextColor.DARK_GREEN));
        textBuilder.append(TextComponent.newline());
        textBuilder.append(TextComponent.newline());
        textBuilder.append(TextComponent.of("Click to auto-complete.", TextColor.GRAY));
        return textBuilder.build();
    }
}