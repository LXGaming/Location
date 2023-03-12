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

import io.github.lxgaming.location.api.entity.Dimension;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

public class DimensionImpl implements Dimension {

    private final int id;
    private final String name;

    public DimensionImpl(int id) {
        this(id, null);
    }

    public DimensionImpl(String name) {
        this(0, name);
    }

    public DimensionImpl(int id, String name) {
        this.id = id;
        this.name = name;
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

        DimensionImpl dimension = (DimensionImpl) obj;
        return Objects.equals(getId(), dimension.getId()) && Objects.equals(getName(), dimension.getName());
    }

    @Override
    public String toString() {
        return getName() + " (" + getId() + ")";
    }
}