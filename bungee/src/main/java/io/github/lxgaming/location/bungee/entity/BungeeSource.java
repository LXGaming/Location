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

package io.github.lxgaming.location.bungee.entity;

import io.github.lxgaming.location.api.entity.Source;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class BungeeSource implements Source {
    
    private final CommandSender commandSender;
    
    public BungeeSource(CommandSender commandSender) {
        this.commandSender = commandSender;
    }
    
    @Override
    public @NonNull UUID getUniqueId() {
        if (commandSender instanceof ProxiedPlayer) {
            return ((ProxiedPlayer) commandSender).getUniqueId();
        }
        
        return CONSOLE_UUID;
    }
    
    @Override
    public @NonNull String getName() {
        return commandSender.getName();
    }
    
    @Override
    public boolean hasPermission(@NonNull String permission) {
        return commandSender.hasPermission(permission);
    }
    
    @Override
    public void sendActionBar(@NonNull Component component) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(BungeeComponentSerializer.get().serialize(component));
            return;
        }
        
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        player.sendMessage(ChatMessageType.ACTION_BAR, BungeeComponentSerializer.get().serialize(component));
    }
    
    @Override
    public void sendMessage(Component component, MessageType messageType) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(BungeeComponentSerializer.get().serialize(component));
            return;
        }
        
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (messageType == MessageType.CHAT) {
            player.sendMessage(ChatMessageType.CHAT, BungeeComponentSerializer.get().serialize(component));
        } else if (messageType == MessageType.SYSTEM) {
            player.sendMessage(ChatMessageType.SYSTEM, BungeeComponentSerializer.get().serialize(component));
        } else {
            throw new UnsupportedOperationException(String.format("%s is not supported", messageType));
        }
    }
}