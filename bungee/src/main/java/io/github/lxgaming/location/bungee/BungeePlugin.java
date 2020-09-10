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

import com.google.common.collect.Lists;
import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.api.Platform;
import io.github.lxgaming.location.bungee.command.LocationCommand;
import io.github.lxgaming.location.bungee.listener.BungeeListener;
import io.github.lxgaming.location.common.LocationImpl;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class BungeePlugin extends Plugin implements Platform {
    
    private static BungeePlugin instance;
    
    @Override
    public void onEnable() {
        instance = this;
        
        if (getProxy().getName().equalsIgnoreCase("BungeeCord")) {
            getLogger().severe("\n\n"
                    + "  BungeeCord is not supported - https://github.com/SpigotMC/BungeeCord/pull/1877\n"
                    + "\n"
                    + "  Use Waterfall - https://github.com/PaperMC/Waterfall\n"
            );
            return;
        }
        
        LocationImpl location = new LocationImpl(this);
        location.load();
        
        getProxy().getPluginManager().registerCommand(getInstance(), new LocationCommand());
        getProxy().getPluginManager().registerListener(getInstance(), new BungeeListener());
    }
    
    @Override
    public void onDisable() {
        if (!Location.isAvailable()) {
            return;
        }
        
        LocationImpl.getInstance().getLogger().info("{} v{} unloaded", Location.NAME, Location.VERSION);
    }
    
    @Override
    public @NonNull Collection<String> getUsernames() {
        List<String> usernames = Lists.newArrayList();
        for (ProxiedPlayer player : getProxy().getPlayers()) {
            usernames.add(player.getName());
        }
        
        return usernames;
    }
    
    @Override
    public @NonNull Type getType() {
        return Type.BUNGEECORD;
    }
    
    @Override
    public @NonNull Path getPath() {
        return getDataFolder().toPath();
    }
    
    public static BungeePlugin getInstance() {
        return instance;
    }
}