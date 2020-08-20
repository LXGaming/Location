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

package io.github.lxgaming.location.common.network.netty;

import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.common.entity.UserImpl;
import io.github.lxgaming.location.common.network.PacketRegistry;
import io.github.lxgaming.location.common.util.Toolbox;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public abstract class PacketHandler extends ChannelDuplexHandler {
    
    public static final String NAME = Location.ID + "-handler";
    
    protected final UserImpl user;
    
    protected PacketHandler(UserImpl user) {
        this.user = user;
    }
    
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            int readerIndex = byteBuf.readerIndex();
            
            try {
                PacketRegistry.SERVERBOUND.process(this, byteBuf);
            } catch (Exception ex) {
                // no-op
            }
            
            byteBuf.readerIndex(readerIndex);
        }
        
        super.write(ctx, msg, promise);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            int readerIndex = byteBuf.readerIndex();
            
            try {
                PacketRegistry.CLIENTBOUND.process(this, byteBuf);
            } catch (Exception ex) {
                // no-op
            }
            
            byteBuf.readerIndex(readerIndex);
        }
        
        super.channelRead(ctx, msg);
    }
    
    public int getProtocolVersion() {
        return user.getProtocolVersion().getId();
    }
    
    public void handleClientPlayerRotation(ByteBuf byteBuf) {
        float yaw = byteBuf.readFloat();
        float pitch = byteBuf.readFloat();
        
        user.setYaw(Toolbox.normalizeYaw(yaw));
        user.setPitch(pitch);
    }
    
    public void handleClientPlayerPosition(ByteBuf byteBuf) {
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        
        user.setX(x);
        user.setY(y);
        user.setZ(z);
    }
    
    public void handleClientPlayerPositionRotation(ByteBuf byteBuf) {
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        float yaw = byteBuf.readFloat();
        float pitch = byteBuf.readFloat();
        
        user.setX(x);
        user.setY(y);
        user.setZ(z);
        user.setYaw(Toolbox.normalizeYaw(yaw));
        user.setPitch(pitch);
    }
    
    public void handleServerJoinGame(ByteBuf byteBuf) {
        throw new UnsupportedOperationException();
    }
    
    public void handleServerPlayerPositionRotation(ByteBuf byteBuf) {
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        float yaw = byteBuf.readFloat();
        float pitch = byteBuf.readFloat();
        byte flags = byteBuf.readByte();
        
        // X (0)
        if ((flags & 1) == 1) {
            x += user.getX();
        }
        
        // Y (1)
        if ((flags & 2) == 2) {
            y += user.getY();
        }
        
        // Z (2)
        if ((flags & 4) == 4) {
            z += user.getZ();
        }
        
        // Y_ROT (3)
        if ((flags & 8) == 8) {
            yaw += user.getYaw();
        }
        
        // X_ROT (4)
        if ((flags & 16) == 16) {
            pitch += user.getPitch();
        }
        
        user.setX(x);
        user.setY(y);
        user.setZ(z);
        user.setYaw(Toolbox.normalizeYaw(yaw));
        user.setPitch(pitch);
    }
    
    public void handleServerRespawn(ByteBuf byteBuf) {
        throw new UnsupportedOperationException();
    }
}