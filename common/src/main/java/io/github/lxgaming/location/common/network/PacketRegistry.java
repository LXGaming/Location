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

package io.github.lxgaming.location.common.network;

import com.google.common.collect.Lists;
import io.github.lxgaming.location.common.network.netty.PacketHandler;
import io.github.lxgaming.location.common.util.ProtocolUtils;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.function.BiConsumer;

public enum PacketRegistry {
    
    CLIENTBOUND("Clientbound") {
        {
            // Player Position
            registerPacket(0x11, 757, 755, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x12, 754, 735, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x11, 578, 471, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x12, 470, 464, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x10, 463, 389, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x0E, 388, 386, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x0C, 385, 343, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x0D, 342, 336, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x0E, 335, 332, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x0D, 331, 318, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x0C, 317, 77, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x0B, 76, 67, PacketHandler::handleClientPlayerPosition);
            registerPacket(0x04, 66, 47, PacketHandler::handleClientPlayerPosition);
            
            // Player Rotation
            registerPacket(0x13, 757, 755, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x14, 754, 735, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x13, 578, 471, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x14, 470, 464, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x12, 463, 389, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x10, 388, 386, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x0E, 385, 343, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x0F, 342, 336, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x10, 335, 332, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x0F, 331, 318, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x0E, 317, 77, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x0D, 76, 67, PacketHandler::handleClientPlayerRotation);
            registerPacket(0x05, 66, 47, PacketHandler::handleClientPlayerRotation);
            
            // Player Position & Rotation
            registerPacket(0x12, 757, 755, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x13, 754, 735, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x12, 578, 471, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x13, 470, 464, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x11, 463, 389, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x0F, 388, 386, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x0D, 385, 343, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x0E, 342, 336, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x0F, 335, 332, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x0E, 331, 318, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x0D, 317, 77, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x0C, 76, 67, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x06, 66, 47, PacketHandler::handleClientPlayerPositionRotation);
        }
    },
    
    SERVERBOUND("Serverbound") {
        {
            // Join Game
            registerPacket(0x26, 757, 755, PacketHandler::handleServerJoinGame);
            registerPacket(0x24, 754, 751, PacketHandler::handleServerJoinGame);
            registerPacket(0x25, 736, 735, PacketHandler::handleServerJoinGame);
            registerPacket(0x26, 578, 550, PacketHandler::handleServerJoinGame);
            registerPacket(0x25, 498, 389, PacketHandler::handleServerJoinGame);
            registerPacket(0x24, 388, 345, PacketHandler::handleServerJoinGame);
            registerPacket(0x23, 344, 332, PacketHandler::handleServerJoinGame);
            registerPacket(0x24, 331, 318, PacketHandler::handleServerJoinGame);
            registerPacket(0x23, 317, 86, PacketHandler::handleServerJoinGame);
            registerPacket(0x24, 85, 67, PacketHandler::handleServerJoinGame);
            registerPacket(0x01, 66, 47, PacketHandler::handleServerJoinGame);
            
            // Player Position & Rotation
            registerPacket(0x38, 757, 755, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x34, 754, 751, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x35, 736, 735, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x36, 578, 550, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x35, 498, 471, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x33, 470, 451, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x32, 450, 389, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x31, 388, 352, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x30, 351, 345, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x2F, 344, 336, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x2E, 335, 332, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x2F, 331, 318, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x2E, 317, 86, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x2F, 85, 80, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x2E, 79, 67, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x08, 66, 47, PacketHandler::handleServerPlayerPositionRotation);
            
            // Respawn
            registerPacket(0x3D, 757, 755, PacketHandler::handleServerRespawn);
            registerPacket(0x39, 754, 751, PacketHandler::handleServerRespawn);
            registerPacket(0x3A, 736, 735, PacketHandler::handleServerRespawn);
            registerPacket(0x3B, 578, 550, PacketHandler::handleServerRespawn);
            registerPacket(0x3A, 498, 471, PacketHandler::handleServerRespawn);
            registerPacket(0x39, 470, 451, PacketHandler::handleServerRespawn);
            registerPacket(0x38, 450, 389, PacketHandler::handleServerRespawn);
            registerPacket(0x37, 388, 352, PacketHandler::handleServerRespawn);
            registerPacket(0x36, 351, 345, PacketHandler::handleServerRespawn);
            registerPacket(0x35, 344, 336, PacketHandler::handleServerRespawn);
            registerPacket(0x34, 335, 332, PacketHandler::handleServerRespawn);
            registerPacket(0x35, 331, 318, PacketHandler::handleServerRespawn);
            registerPacket(0x33, 317, 86, PacketHandler::handleServerRespawn);
            registerPacket(0x34, 85, 80, PacketHandler::handleServerRespawn);
            registerPacket(0x33, 79, 67, PacketHandler::handleServerRespawn);
            registerPacket(0x07, 66, 47, PacketHandler::handleServerRespawn);
        }
    };
    
    private final String name;
    private final List<Packet> packets;
    
    PacketRegistry(String name) {
        this.name = name;
        this.packets = Lists.newArrayList();
    }
    
    protected void registerPacket(int id, int maximumProtocol, int minimumProtocol, BiConsumer<PacketHandler, ByteBuf> consumer) {
        packets.add(new Packet(id, maximumProtocol, minimumProtocol, consumer));
    }
    
    public void process(PacketHandler packetHandler, ByteBuf byteBuf) {
        int packetId = ProtocolUtils.readVarIntSafely(byteBuf);
        if (packetId == Integer.MIN_VALUE) {
            return;
        }
        
        for (Packet packet : packets) {
            if (packet.getId() != packetId) {
                continue;
            }
            
            if (packet.getMaximumProtocol() >= packetHandler.getProtocolVersion() && packet.getMinimumProtocol() <= packetHandler.getProtocolVersion()) {
                packet.getConsumer().accept(packetHandler, byteBuf);
                return;
            }
        }
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}