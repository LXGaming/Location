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

package io.github.lxgaming.location.common.entity;

import com.google.common.collect.ImmutableList;
import io.github.lxgaming.location.api.entity.ProtocolVersion;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.Objects;

public class ProtocolVersionImpl implements ProtocolVersion {
    
    private static final List<ProtocolVersionImpl> PROTOCOL_VERSIONS = ImmutableList.of(
            new ProtocolVersionImpl(47, "1.8"),
            new ProtocolVersionImpl(107, "1.9"),
            new ProtocolVersionImpl(108, "1.9.1"),
            new ProtocolVersionImpl(109, "1.9.2"),
            new ProtocolVersionImpl(110, "1.9.4"),
            new ProtocolVersionImpl(210, "1.10"),
            new ProtocolVersionImpl(315, "1.11"),
            new ProtocolVersionImpl(316, "1.11.2"),
            new ProtocolVersionImpl(335, "1.12"),
            new ProtocolVersionImpl(338, "1.12.1"),
            new ProtocolVersionImpl(340, "1.12.2"),
            new ProtocolVersionImpl(393, "1.13"),
            new ProtocolVersionImpl(401, "1.13.1"),
            new ProtocolVersionImpl(404, "1.13.2"),
            new ProtocolVersionImpl(477, "1.14"),
            new ProtocolVersionImpl(480, "1.14.1"),
            new ProtocolVersionImpl(485, "1.14.2"),
            new ProtocolVersionImpl(490, "1.14.3"),
            new ProtocolVersionImpl(498, "1.14.4"),
            new ProtocolVersionImpl(573, "1.15"),
            new ProtocolVersionImpl(575, "1.15.1"),
            new ProtocolVersionImpl(578, "1.15.2"),
            new ProtocolVersionImpl(735, "1.16"),
            new ProtocolVersionImpl(736, "1.16.1"),
            new ProtocolVersionImpl(751, "1.16.2"),
            new ProtocolVersionImpl(753, "1.16.3"),
            new ProtocolVersionImpl(757, "1.18.1")
    );
    
    private final int id;
    private final String name;
    
    public ProtocolVersionImpl(int id) {
        this(id, null);
    }
    
    public ProtocolVersionImpl(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public static ProtocolVersion getProtocolVersion(int id) {
        return getProtocolVersion(id, null);
    }
    
    @NonNull
    public static ProtocolVersion getProtocolVersion(int id, @Nullable String name) {
        for (ProtocolVersion protocolVersion : PROTOCOL_VERSIONS) {
            if (protocolVersion.getId() == id) {
                return protocolVersion;
            }
        }
        
        return new ProtocolVersionImpl(id, name);
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public @Nullable String getName() {
        return name;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        ProtocolVersionImpl protocolVersion = (ProtocolVersionImpl) obj;
        return Objects.equals(getId(), protocolVersion.getId()) && Objects.equals(getName(), protocolVersion.getName());
    }
    
    @Override
    public String toString() {
        return getName() + " (" + getId() + ")";
    }
}