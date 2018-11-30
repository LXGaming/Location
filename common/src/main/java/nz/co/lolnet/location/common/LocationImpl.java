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

package nz.co.lolnet.location.common;

import com.google.common.collect.Sets;
import nz.co.lolnet.location.api.Location;
import nz.co.lolnet.location.api.Platform;
import nz.co.lolnet.location.api.util.Reference;
import nz.co.lolnet.location.common.configuration.Config;
import nz.co.lolnet.location.common.configuration.Configuration;
import nz.co.lolnet.location.common.manager.PacketManager;
import nz.co.lolnet.location.common.util.LoggerImpl;

import java.util.Collections;
import java.util.Optional;

public class LocationImpl extends Location {
    
    private final Configuration configuration;
    
    public LocationImpl(Platform platform) {
        super();
        this.platform = platform;
        this.logger = new LoggerImpl();
        this.users = Collections.synchronizedSet(Sets.newHashSet());
        this.configuration = new Configuration();
    }
    
    public void loadLocation() {
        getLogger().info("Initializing...");
        reloadLocation();
        PacketManager.registerPackets();
        getLogger().info("{} v{} has loaded", Reference.NAME, Reference.VERSION);
    }
    
    public boolean reloadLocation() {
        getConfiguration().loadConfiguration();
        if (!getConfig().isPresent()) {
            return false;
        }
        
        getConfiguration().saveConfiguration();
        if (getConfig().map(Config::isDebug).orElse(false)) {
            getLogger().debug("Debug mode enabled");
        } else {
            getLogger().info("Debug mode disabled");
        }
        
        return true;
    }
    
    public static LocationImpl getInstance() {
        return (LocationImpl) Location.getInstance();
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
    
    public Optional<? extends Config> getConfig() {
        if (getConfiguration() != null) {
            return Optional.ofNullable(getConfiguration().getConfig());
        }
        
        return Optional.empty();
    }
}