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

package io.github.lxgaming.location.velocity.entity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import io.github.lxgaming.location.api.entity.Source;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class VelocitySource implements Source {
    
    private final CommandSource commandSource;
    
    public VelocitySource(CommandSource commandSource) {
        this.commandSource = commandSource;
    }
    
    @Override
    public @NonNull UUID getUniqueId() {
        if (commandSource instanceof Player) {
            return ((Player) commandSource).getUniqueId();
        }
        
        return CONSOLE_UUID;
    }
    
    @Override
    public @NonNull String getName() {
        if (commandSource instanceof Player) {
            return ((Player) commandSource).getUsername();
        }
        
        return "Console";
    }
    
    @Override
    public boolean hasPermission(@NonNull String permission) {
        return commandSource.hasPermission(permission);
    }
    
    @Override
    public void sendActionBar(@NonNull Component component) {
        commandSource.sendActionBar(component);
    }
    
    @Override
    public void sendMessage(Component component, MessageType messageType) {
        commandSource.sendMessage(component, messageType);
    }
}