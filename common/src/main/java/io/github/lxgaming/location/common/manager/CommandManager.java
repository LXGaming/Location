/*
 * Copyright 2018 Alex Thomson
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

import com.google.common.collect.Sets;
import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.common.command.AbstractCommand;
import io.github.lxgaming.location.common.util.Toolbox;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class CommandManager {
    
    private static final Set<AbstractCommand> COMMANDS = Sets.newLinkedHashSet();
    private static final Set<Class<? extends AbstractCommand>> COMMAND_CLASSES = Sets.newLinkedHashSet();
    
    public static boolean registerAlias(AbstractCommand command, String alias) {
        if (Toolbox.containsIgnoreCase(command.getAliases(), alias)) {
            Location.getInstance().getLogger().warn("{} has already been registered for {}", alias, command.getClass().getSimpleName());
            return false;
        }
        
        command.getAliases().add(alias);
        Location.getInstance().getLogger().debug("{} registered for {}", alias, command.getClass().getSimpleName());
        return true;
    }
    
    public static boolean registerCommand(Class<? extends AbstractCommand> commandClass) {
        if (getCommandClasses().contains(commandClass)) {
            Location.getInstance().getLogger().warn("{} has already been registered", commandClass.getSimpleName());
            return false;
        }
        
        getCommandClasses().add(commandClass);
        AbstractCommand command = Toolbox.newInstance(commandClass).orElse(null);
        if (command == null) {
            Location.getInstance().getLogger().error("{} failed to initialize", commandClass.getSimpleName());
            return false;
        }
        
        getCommands().add(command);
        Location.getInstance().getLogger().debug("{} registered", commandClass.getSimpleName());
        return true;
    }
    
    public static boolean registerCommand(AbstractCommand parentCommand, Class<? extends AbstractCommand> commandClass) {
        if (parentCommand.getClass() == commandClass) {
            Location.getInstance().getLogger().warn("{} attempted to register itself", parentCommand.getClass().getSimpleName());
            return false;
        }
        
        if (getCommandClasses().contains(commandClass)) {
            Location.getInstance().getLogger().warn("{} has already been registered", commandClass.getSimpleName());
            return false;
        }
        
        getCommandClasses().add(commandClass);
        AbstractCommand command = Toolbox.newInstance(commandClass).orElse(null);
        if (command == null) {
            Location.getInstance().getLogger().error("{} failed to initialize", commandClass.getSimpleName());
            return false;
        }
        
        parentCommand.getChildren().add(command);
        Location.getInstance().getLogger().debug("{} registered for {}", commandClass.getSimpleName(), parentCommand.getClass().getSimpleName());
        return true;
    }
    
    public static Optional<AbstractCommand> getChildCommand(List<String> arguments) {
        return getChildCommand(getCommands(), arguments);
    }
    
    public static Optional<AbstractCommand> getChildCommand(AbstractCommand parentCommand, List<String> arguments) {
        return getChildCommand(parentCommand.getChildren(), arguments);
    }
    
    private static Optional<AbstractCommand> getChildCommand(Set<AbstractCommand> commands, List<String> arguments) {
        if (commands.isEmpty() || arguments.isEmpty()) {
            return Optional.empty();
        }
        
        String argument = arguments.get(0);
        for (AbstractCommand command : commands) {
            if (Toolbox.containsIgnoreCase(command.getAliases(), argument)) {
                arguments.remove(0);
                return Optional.of(getChildCommand(command.getChildren(), arguments).orElse(command));
            }
        }
        
        return Optional.empty();
    }
    
    public static Set<AbstractCommand> getCommands() {
        return COMMANDS;
    }
    
    private static Set<Class<? extends AbstractCommand>> getCommandClasses() {
        return COMMAND_CLASSES;
    }
}