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

package io.github.lxgaming.location.api;

import io.github.lxgaming.location.api.data.User;
import io.github.lxgaming.location.api.util.Logger;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public abstract class Location {
    
    private static Location instance;
    protected Platform platform;
    protected Logger logger;
    protected Set<User> users;
    
    public Location() {
        instance = this;
    }
    
    public static Location getInstance() {
        return instance;
    }
    
    public Platform getPlatform() {
        return platform;
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public Set<User> getUsers() {
        return users;
    }
    
    public Optional<User> getUser(UUID uniqueId) {
        for (User user : getUsers()) {
            if (user.getUniqueId().equals(uniqueId)) {
                return Optional.of(user);
            }
        }
        
        return Optional.empty();
    }
}