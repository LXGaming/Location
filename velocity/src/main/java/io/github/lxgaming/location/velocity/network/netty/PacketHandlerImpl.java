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

package io.github.lxgaming.location.velocity.network.netty;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.connection.registry.DimensionInfo;
import com.velocitypowered.proxy.protocol.ProtocolUtils;
import com.velocitypowered.proxy.protocol.packet.JoinGamePacket;
import com.velocitypowered.proxy.protocol.packet.RespawnPacket;
import io.github.lxgaming.location.common.entity.DimensionImpl;
import io.github.lxgaming.location.common.entity.UserImpl;
import io.github.lxgaming.location.common.network.netty.PacketHandler;
import io.github.lxgaming.location.velocity.util.VelocityToolbox;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class PacketHandlerImpl extends PacketHandler {

    public PacketHandlerImpl(UserImpl user) {
        super(user);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof JoinGamePacket packet) {
            String dimensionName;
            if (packet.getDimensionInfo() != null) {
                dimensionName = packet.getDimensionInfo().getRegistryIdentifier();
            } else {
                dimensionName = null;
            }

            user.setDimension(new DimensionImpl(packet.getDimension(), dimensionName));
        }

        super.write(ctx, msg, promise);
    }

    @Override
    public void handleServerRespawn(ByteBuf byteBuf) {
        RespawnPacket packet = new RespawnPacket();
        packet.decode(byteBuf, ProtocolUtils.Direction.CLIENTBOUND, ProtocolVersion.getProtocolVersion(getProtocolVersion()));

        DimensionInfo dimensionInfo = VelocityToolbox.getDimensionInfo(packet);
        String dimensionName;
        if (dimensionInfo != null) {
            dimensionName = dimensionInfo.getRegistryIdentifier();
        } else {
            dimensionName = null;
        }

        user.setDimension(new DimensionImpl(packet.getDimension(), dimensionName));
    }
}
