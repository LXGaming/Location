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
import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.api.Platform;
import io.github.lxgaming.location.api.util.Logger;
import io.github.lxgaming.location.common.LocationImpl;
import io.github.lxgaming.location.common.configuration.Config;
import io.github.lxgaming.location.common.manager.CommandManager;
import io.github.lxgaming.location.velocity.command.GetCommand;
import io.github.lxgaming.location.velocity.command.LocationCommand;
import io.github.lxgaming.location.velocity.command.ReloadCommand;
import io.github.lxgaming.location.velocity.listener.VelocityListener;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

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
        location.getLogger()
                .add(Logger.Level.INFO, LoggerFactory.getLogger(Location.NAME)::info)
                .add(Logger.Level.WARN, LoggerFactory.getLogger(Location.NAME)::warn)
                .add(Logger.Level.ERROR, LoggerFactory.getLogger(Location.NAME)::error)
                .add(Logger.Level.DEBUG, message -> {
                    if (LocationImpl.getInstance().getConfig().map(Config::isDebug).orElse(false)) {
                        LoggerFactory.getLogger(Location.NAME).info(message);
                    }
                });
        
        location.loadLocation();
        
        CommandManager.registerCommand(GetCommand.class);
        CommandManager.registerCommand(ReloadCommand.class);
        getProxy().getCommandManager().register(new LocationCommand(), "location");
        getProxy().getEventManager().register(getInstance(), new VelocityListener());
    }
    
    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        Location.getInstance().getLogger().info("{} v{} unloaded", Location.NAME, Location.VERSION);
    }
    
    @Override
    public Path getPath() {
        return path;
    }
    
    public static VelocityPlugin getInstance() {
        return instance;
    }
    
    public ProxyServer getProxy() {
        return proxy;
    }
}