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

package io.github.lxgaming.location.velocity.util;

import com.velocitypowered.proxy.connection.MinecraftConnection;
import com.velocitypowered.proxy.connection.registry.DimensionInfo;
import com.velocitypowered.proxy.network.Connections;
import com.velocitypowered.proxy.protocol.packet.Respawn;
import io.github.lxgaming.location.common.entity.UserImpl;
import io.github.lxgaming.location.common.network.netty.PacketHandler;
import io.github.lxgaming.location.common.util.Toolbox;
import io.github.lxgaming.location.velocity.network.netty.PacketHandlerImpl;
import io.netty.channel.Channel;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class VelocityToolbox {
    
    private static final MethodHandle dimensionInfoMethodHandle;
    
    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle dimensionInfoMethodHandleTemporary;
        
        try {
            Field field = Respawn.class.getDeclaredField("dimensionInfo");
            field.setAccessible(true);
            
            dimensionInfoMethodHandleTemporary = lookup.unreflectGetter(field);
        } catch (Throwable throwable) {
            dimensionInfoMethodHandleTemporary = null;
        }
        
        dimensionInfoMethodHandle = dimensionInfoMethodHandleTemporary;
    }
    
    public static boolean addChannel(UserImpl user, Object object) {
        Channel channel = Toolbox.getField(object, MinecraftConnection.class).map(MinecraftConnection::getChannel).orElse(null);
        if (channel != null && channel.pipeline().get(PacketHandler.NAME) == null) {
            channel.pipeline().addBefore(Connections.HANDLER, PacketHandler.NAME, new PacketHandlerImpl(user));
            return true;
        }
        
        return false;
    }
    
    public static boolean removeChannel(Object object) {
        Channel channel = Toolbox.getField(object, MinecraftConnection.class).map(MinecraftConnection::getChannel).orElse(null);
        if (channel != null && channel.pipeline().get(PacketHandler.NAME) != null) {
            channel.pipeline().remove(PacketHandler.NAME);
            return true;
        }
        
        return false;
    }
    
    public static DimensionInfo getDimensionInfo(Respawn respawn) {
        try {
            if (dimensionInfoMethodHandle == null) {
                return null;
            }
            
            Object object = dimensionInfoMethodHandle.invoke(respawn);
            if (object != null) {
                return (DimensionInfo) object;
            }
            
            return null;
        } catch (Throwable ex) {
            return null;
        }
    }
}