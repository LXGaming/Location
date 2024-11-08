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

package io.github.lxgaming.location.api;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Collection;

public interface Platform {

    @NotNull Collection<String> getUsernames();

    @NotNull Path getPath();

    @NotNull Type getType();

    enum Type {

        BUNGEECORD("BungeeCord"),
        VELOCITY("Velocity");

        private final String name;

        Type(@NotNull String name) {
            this.name = name;
        }

        public @NotNull String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}