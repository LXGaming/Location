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

package io.github.lxgaming.location.bungee.network.netty;

import io.github.lxgaming.location.common.entity.DimensionImpl;
import io.github.lxgaming.location.common.entity.UserImpl;
import io.github.lxgaming.location.common.network.PacketRegistry;
import io.github.lxgaming.location.common.network.netty.PacketHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.md_5.bungee.protocol.PacketWrapper;
import net.md_5.bungee.protocol.ProtocolConstants;
import net.md_5.bungee.protocol.packet.Login;
import net.md_5.bungee.protocol.packet.Respawn;
import se.llbit.nbt.Tag;

public class PacketHandlerImpl extends PacketHandler {
    
    public PacketHandlerImpl(UserImpl user) {
        super(user);
    }
    
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof Login) {
            Login packet = (Login) msg;
            if (packet.getDimension() instanceof Integer) {
                user.setDimension(new DimensionImpl((Integer) packet.getDimension()));
            }
            
            // 1.16 - 1.16.1
            if (packet.getDimension() instanceof String) {
                user.setDimension(new DimensionImpl((String) packet.getDimension()));
            }
            
            // 1.16.2
            if (packet.getDimension() instanceof Tag) {
                user.setDimension(new DimensionImpl(packet.getWorldName()));
            }
        }
        
        super.write(ctx, msg, promise);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof PacketWrapper) {
            PacketWrapper packet = (PacketWrapper) msg;
            
            // Ignore known packets.
            if (packet.packet != null) {
                super.channelRead(ctx, msg);
                return;
            }
            
            ByteBuf byteBuf = packet.buf;
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
    
    @Override
    public void handleServerRespawn(ByteBuf byteBuf) {
        Respawn packet = new Respawn();
        packet.read(byteBuf, ProtocolConstants.Direction.TO_CLIENT, getProtocolVersion());
        
        if (packet.getDimension() instanceof Integer) {
            user.setDimension(new DimensionImpl((Integer) packet.getDimension()));
        }
        
        // 1.16 - 1.16.1
        if (packet.getDimension() instanceof String) {
            user.setDimension(new DimensionImpl((String) packet.getDimension()));
        }
        
        // 1.16.2
        if (packet.getDimension() instanceof Tag) {
            user.setDimension(new DimensionImpl(packet.getWorldName()));
        }
    }
}