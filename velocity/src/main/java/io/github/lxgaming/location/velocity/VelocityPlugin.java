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

package io.github.lxgaming.location.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.util.MessagePosition;
import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.api.Platform;
import io.github.lxgaming.location.common.LocationImpl;
import io.github.lxgaming.location.velocity.command.LocationCommand;
import io.github.lxgaming.location.velocity.listener.VelocityListener;
import net.kyori.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.file.Path;
import java.util.UUID;

@Plugin(
        id = Location.ID,
        name = Location.NAME,
        version = Location.VERSION,
        description = Location.DESCRIPTION,
        url = Location.WEBSITE,
        authors = {Location.AUTHORS}
)
public class VelocityPlugin implements Platform {
    
    private static VelocityPlugin instance;
    
    @Inject
    private ProxyServer proxy;
    
    @Inject
    @DataDirectory
    private Path path;
    
    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        instance = this;
        
        LocationImpl location = new LocationImpl(this);
        location.load();
        
        getProxy().getCommandManager().register(new LocationCommand(), "location");
        getProxy().getEventManager().register(getInstance(), new VelocityListener());
    }
    
    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        LocationImpl.getInstance().getLogger().info("{} v{} unloaded", Location.NAME, Location.VERSION);
    }
    
    @Override
    public boolean hasPermission(@NonNull UUID uniqueId, @NonNull String permission) {
        if (uniqueId.equals(Platform.CONSOLE_UUID)) {
            return true;
        }
        
        return getProxy().getPlayer(uniqueId).map(player -> player.hasPermission(permission)).orElse(false);
    }
    
    @Override
    public void sendMessage(@NonNull UUID uniqueId, @NonNull Component component) {
        sendMessage(uniqueId, MessagePosition.SYSTEM, component);
    }
    
    @Override
    public void sendChatMessage(@NonNull UUID uniqueId, @NonNull Component component) {
        sendMessage(uniqueId, MessagePosition.CHAT, component);
    }
    
    @Override
    public void sendStatusMessage(@NonNull UUID uniqueId, @NonNull Component component) {
        sendMessage(uniqueId, MessagePosition.ACTION_BAR, component);
    }
    
    private void sendMessage(@NonNull UUID uniqueId, @NonNull MessagePosition chatType, @NonNull Component component) {
        if (uniqueId.equals(Platform.CONSOLE_UUID)) {
            getProxy().getConsoleCommandSource().sendMessage(component);
            return;
        }
        
        getProxy().getPlayer(uniqueId).ifPresent(player -> player.sendMessage(component, MessagePosition.SYSTEM));
    }
    
    @Override
    public @NonNull Path getPath() {
        return path;
    }
    
    @Override
    public @NonNull Type getType() {
        return Type.VELOCITY;
    }
    
    public static VelocityPlugin getInstance() {
        return instance;
    }
    
    public ProxyServer getProxy() {
        return proxy;
    }
}