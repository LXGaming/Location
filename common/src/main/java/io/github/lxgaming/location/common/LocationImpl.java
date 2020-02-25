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

package io.github.lxgaming.location.common;

import com.google.common.collect.Sets;
import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.api.Platform;
import io.github.lxgaming.location.api.entity.User;
import io.github.lxgaming.location.common.configuration.Config;
import io.github.lxgaming.location.common.configuration.Configuration;
import io.github.lxgaming.location.common.configuration.category.GeneralCategory;
import io.github.lxgaming.location.common.manager.CommandManager;
import io.github.lxgaming.location.common.manager.LocaleManager;
import io.github.lxgaming.location.common.manager.PacketManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LocationImpl extends Location {
    
    private final Logger logger;
    private final Configuration configuration;
    
    public LocationImpl(Platform platform) {
        super(platform);
        this.users = Sets.newConcurrentHashSet();
        this.logger = LoggerFactory.getLogger(Location.NAME);
        this.configuration = new Configuration(platform.getPath());
    }
    
    public void load() {
        getLogger().info("Initializing...");
        if (!reload()) {
            getLogger().error("Failed to load");
            return;
        }
        
        PacketManager.prepare();
        LocaleManager.prepare();
        CommandManager.prepare();
        
        getLogger().info("{} v{} has loaded", Location.NAME, Location.VERSION);
    }
    
    public boolean reload() {
        getConfiguration().loadConfiguration();
        if (!getConfig().isPresent()) {
            return false;
        }
        
        getConfiguration().saveConfiguration();
        reloadLogger();
        
        return true;
    }
    
    public void reloadLogger() {
        if (getConfig().map(Config::getGeneralCategory).map(GeneralCategory::isDebug).orElse(false)) {
            getLogger().debug("Debug mode enabled");
        } else {
            getLogger().info("Debug mode disabled");
        }
    }
    
    public boolean addUser(User user) {
        return this.users.add(user);
    }
    
    public boolean removeUser(User user) {
        return this.users.remove(user);
    }
    
    public static LocationImpl getInstance() {
        return (LocationImpl) Location.getInstance();
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
    
    public Optional<? extends Config> getConfig() {
        return Optional.ofNullable(getConfiguration().getConfig());
    }
}