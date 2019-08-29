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

package io.github.lxgaming.location.bungee;

import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.api.Platform;
import io.github.lxgaming.location.api.util.Logger;
import io.github.lxgaming.location.api.util.Reference;
import io.github.lxgaming.location.bungee.command.GetCommand;
import io.github.lxgaming.location.bungee.command.LocationCommand;
import io.github.lxgaming.location.bungee.command.ReloadCommand;
import io.github.lxgaming.location.bungee.listener.BungeeListener;
import io.github.lxgaming.location.common.LocationImpl;
import io.github.lxgaming.location.common.configuration.Config;
import io.github.lxgaming.location.common.manager.CommandManager;
import net.md_5.bungee.api.plugin.Plugin;

import java.nio.file.Path;

public class BungeePlugin extends Plugin implements Platform {
    
    private static BungeePlugin instance;
    
    @Override
    public void onEnable() {
        instance = this;
        LocationImpl location = new LocationImpl(this);
        location.getLogger()
                .add(Logger.Level.INFO, getLogger()::info)
                .add(Logger.Level.WARN, getLogger()::warning)
                .add(Logger.Level.ERROR, getLogger()::severe)
                .add(Logger.Level.DEBUG, message -> {
                    if (LocationImpl.getInstance().getConfig().map(Config::isDebug).orElse(false)) {
                        getLogger().info(message);
                    }
                });
        
        location.loadLocation();
        
        CommandManager.registerCommand(GetCommand.class);
        CommandManager.registerCommand(ReloadCommand.class);
        getProxy().getPluginManager().registerCommand(getInstance(), new LocationCommand());
        getProxy().getPluginManager().registerListener(getInstance(), new BungeeListener());
    }
    
    @Override
    public void onDisable() {
        Location.getInstance().getLogger().info("{} v{} unloaded", Reference.NAME, Reference.VERSION);
    }
    
    @Override
    public Path getPath() {
        return getDataFolder().toPath();
    }
    
    public static BungeePlugin getInstance() {
        return instance;
    }
}