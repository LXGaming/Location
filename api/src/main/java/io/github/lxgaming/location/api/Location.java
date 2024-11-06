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

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import io.github.lxgaming.location.api.entity.User;
import io.github.lxgaming.location.api.util.BuildParameters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public abstract class Location {

    public static final String ID = "location";
    public static final String NAME = "Location";
    public static final String VERSION = BuildParameters.VERSION;
    public static final String DESCRIPTION = "Player Tracking";
    public static final String AUTHORS = "LX_Gaming";
    public static final String SOURCE = "https://github.com/LXGaming/Location";
    public static final String WEBSITE = "https://lxgaming.github.io/";

    private static Location instance;
    private static Platform platform;
    protected Set<User> users;

    public Location(Platform platform) {
        Location.instance = this;
        Location.platform = platform;
    }

    private static <T> T check(@Nullable T instance) {
        Preconditions.checkState(instance != null, "%s has not been initialized!", Location.NAME);
        return instance;
    }

    public static boolean isAvailable() {
        return instance != null;
    }

    public static @NotNull Location getInstance() {
        return check(instance);
    }

    public static @NotNull Platform getPlatform() {
        return check(platform);
    }

    public @NotNull Set<User> getUsers() {
        return ImmutableSet.copyOf(this.users);
    }

    public @NotNull Optional<User> getUser(@NotNull UUID uniqueId) {
        for (User user : this.users) {
            if (user.getUniqueId().equals(uniqueId)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    public @NotNull Optional<User> getUser(@NotNull String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}