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

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import io.github.lxgaming.location.api.entity.Source;
import io.github.lxgaming.location.common.entity.Locale;
import io.github.lxgaming.location.common.manager.CommandManager;
import io.github.lxgaming.location.common.manager.LocaleManager;
import io.github.lxgaming.location.common.util.StringUtils;
import io.github.lxgaming.location.common.util.brigadier.adapter.CommandAdapter;
import io.github.lxgaming.location.common.util.text.adapter.LocaleAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

import java.util.Map;
import java.util.Set;

public class HelpCommand extends Command {

    @Override
    public boolean prepare() {
        addAlias("Help");
        addAlias("?");
        permission("location.help.base");
        return true;
    }

    @Override
    public void register(LiteralArgumentBuilder<Source> argumentBuilder) {
        argumentBuilder
                .requires(source -> source.hasPermission(getPermission()))
                .executes(context -> {
                    return execute(context.getSource());
                });
    }

    private int execute(Source source) {
        LocaleAdapter.sendSystemMessage(source, Locale.GENERAL_PREFIX);

        Set<Class<? extends Command>> commandClasses = Sets.newHashSet();
        commandClasses.add(getClass());

        Map<CommandNode<Source>, String> usages = CommandManager.DISPATCHER.getSmartUsage(CommandManager.DISPATCHER.getRoot(), source);
        for (Map.Entry<CommandNode<Source>, String> entry : usages.entrySet()) {
            if (!(entry.getKey().getCommand() instanceof CommandAdapter)) {
                continue;
            }

            Command command = ((CommandAdapter<Source>) entry.getKey().getCommand()).getCommand();
            if (commandClasses.contains(command.getClass()) || !(StringUtils.isNotBlank(command.getPermission()) && source.hasPermission(command.getPermission()))) {
                continue;
            }

            commandClasses.add(command.getClass());

            String usage = "/" + CommandManager.getPrefix() + " " + entry.getValue();
            Component description = LocaleManager.serialize(Locale.COMMAND_HELP_HOVER,
                    Iterables.getFirst(command.getAliases(), "Unknown"),
                    StringUtils.defaultIfEmpty(command.getDescription(), "No description provided"),
                    usage,
                    StringUtils.defaultIfEmpty(command.getPermission(), "None")
            );

            TextComponent component = Component.text()
                    .clickEvent(ClickEvent.suggestCommand(usage))
                    .hoverEvent(HoverEvent.showText(description))
                    .append(LocaleManager.serialize(Locale.COMMAND_HELP, usage))
                    .build();

            source.sendSystemMessage(component);
        }

        return 1;
    }
}