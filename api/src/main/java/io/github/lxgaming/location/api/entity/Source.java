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

package io.github.lxgaming.location.api.entity;

import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public interface Source {

    UUID CONSOLE_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @NonNull UUID getUniqueId();

    @NonNull String getName();

    boolean hasPermission(@NonNull String permission);

    void sendActionBar(@NonNull Component component);

    void sendMessage(@NonNull Component component);
}