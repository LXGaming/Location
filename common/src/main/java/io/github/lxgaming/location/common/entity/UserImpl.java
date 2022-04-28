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
import io.github.lxgaming.location.api.entity.ProtocolVersion;
import io.github.lxgaming.location.api.entity.User;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;
import java.util.UUID;

public class UserImpl implements User {
    
    private final UUID uniqueId;
    private final String username;
    private final ProtocolVersion protocolVersion;
    private final BehaviorSubject<Double> xSubject;
    private final BehaviorSubject<Double> ySubject;
    private final BehaviorSubject<Double> zSubject;
    private final BehaviorSubject<Float> pitchSubject;
    private final BehaviorSubject<Float> yawSubject;
    private final BehaviorSubject<Dimension> dimensionSubject;
    private final BehaviorSubject<String> serverSubject;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;
    private Dimension dimension;
    private String server;
    
    public UserImpl(UUID uniqueId, String username, ProtocolVersion protocolVersion) {
        this.uniqueId = uniqueId;
        this.username = username;
        this.protocolVersion = protocolVersion;
        this.xSubject = BehaviorSubject.create();
        this.ySubject = BehaviorSubject.create();
        this.zSubject = BehaviorSubject.create();
        this.pitchSubject = BehaviorSubject.create();
        this.yawSubject = BehaviorSubject.create();
        this.dimensionSubject = BehaviorSubject.create();
        this.serverSubject = BehaviorSubject.create();
    }
    
    @Override
    public @NonNull UUID getUniqueId() {
        return uniqueId;
    }
    
    @Override
    public @NonNull String getUsername() {
        return username;
    }
    
    @Override
    public @NonNull ProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }
    
    @Override
    public double getX() {
        return x;
    }
    
    @Override
    public @NonNull Observable<Double> observeX() {
        return xSubject;
    }
    
    public void setX(double x) {
        this.x = x;
        this.xSubject.onNext(x);
    }
    
    @Override
    public double getY() {
        return y;
    }
    
    @Override
    public @NonNull Observable<Double> observeY() {
        return ySubject;
    }
    
    public void setY(double y) {
        this.y = y;
        this.ySubject.onNext(y);
    }
    
    @Override
    public double getZ() {
        return z;
    }
    
    @Override
    public @NonNull Observable<Double> observeZ() {
        return zSubject;
    }
    
    public void setZ(double z) {
        this.z = z;
        this.zSubject.onNext(z);
    }
    
    @Override
    public float getPitch() {
        return pitch;
    }
    
    @Override
    public @NonNull Observable<Float> observePitch() {
        return pitchSubject;
    }
    
    public void setPitch(float pitch) {
        this.pitch = pitch;
        this.pitchSubject.onNext(pitch);
    }
    
    @Override
    public float getYaw() {
        return yaw;
    }
    
    @Override
    public @NonNull Observable<Float> observeYaw() {
        return yawSubject;
    }
    
    public void setYaw(float yaw) {
        this.yaw = yaw;
        this.yawSubject.onNext(yaw);
    }
    
    @Override
    public @Nullable Dimension getDimension() {
        return dimension;
    }
    
    @Override
    public @NonNull Observable<Dimension> observeDimension() {
        return dimensionSubject;
    }
    
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
        this.dimensionSubject.onNext(dimension);
    }
    
    @Override
    public @Nullable String getServer() {
        return server;
    }
    
    @Override
    public @NonNull Observable<String> observeServer() {
        return serverSubject;
    }
    
    public void setServer(String server) {
        this.server = server;
        this.serverSubject.onNext(server);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getUniqueId());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        UserImpl user = (UserImpl) obj;
        return Objects.equals(getUniqueId(), user.getUniqueId());
    }
    
    @Override
    public String toString() {
        return getUsername() + " (" + getUniqueId() + ")";
    }
}