/*
 * Copyright 2018 lolnet.co.nz
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

package nz.co.lolnet.location.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import nz.co.lolnet.location.api.Location;
import nz.co.lolnet.location.api.Platform;
import nz.co.lolnet.location.api.util.Logger;
import nz.co.lolnet.location.api.util.Reference;
import nz.co.lolnet.location.common.LocationImpl;
import nz.co.lolnet.location.common.configuration.Config;
import nz.co.lolnet.location.common.manager.CommandManager;
import nz.co.lolnet.location.velocity.command.GetCommand;
import nz.co.lolnet.location.velocity.command.LocationCommand;
import nz.co.lolnet.location.velocity.command.ReloadCommand;
import nz.co.lolnet.location.velocity.listener.VelocityListener;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@Plugin(
        id = Reference.ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        description = Reference.DESCRIPTION,
        url = Reference.WEBSITE,
        authors = {Reference.AUTHORS}
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
                .add(Logger.Level.INFO, LoggerFactory.getLogger(Reference.NAME)::info)
                .add(Logger.Level.WARN, LoggerFactory.getLogger(Reference.NAME)::warn)
                .add(Logger.Level.ERROR, LoggerFactory.getLogger(Reference.NAME)::error)
                .add(Logger.Level.DEBUG, message -> {
                    if (LocationImpl.getInstance().getConfig().map(Config::isDebug).orElse(false)) {
                        LoggerFactory.getLogger(Reference.NAME).info(message);
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
        Location.getInstance().getLogger().info("{} v{} unloaded", Reference.NAME, Reference.VERSION);
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