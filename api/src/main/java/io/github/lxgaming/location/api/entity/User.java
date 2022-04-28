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

import io.reactivex.rxjava3.core.Observable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public interface User {
    
    @NonNull UUID getUniqueId();
    
    @NonNull String getUsername();
    
    @NonNull ProtocolVersion getProtocolVersion();
    
    double getX();
    
    @NonNull Observable<Double> observeX();
    
    double getY();
    
    @NonNull Observable<Double> observeY();
    
    double getZ();
    
    @NonNull Observable<Double> observeZ();
    
    float getPitch();
    
    @NonNull Observable<Float> observePitch();
    
    float getYaw();
    
    @NonNull Observable<Float> observeYaw();
    
    @Nullable Dimension getDimension();
    
    @NonNull Observable<Dimension> observeDimension();
    
    @Nullable String getServer();
    
    @NonNull Observable<String> observeServer();
}