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
            // Login (play)
            registerPacket(0x2B, 767, 766, PacketHandler::handleClientLogin);
            registerPacket(0x29, 765, 764, PacketHandler::handleClientLogin);
            registerPacket(0x28, 763, 762, PacketHandler::handleClientLogin);
            registerPacket(0x24, 761, 761, PacketHandler::handleClientLogin);
            registerPacket(0x25, 760, 760, PacketHandler::handleClientLogin);
            registerPacket(0x23, 759, 759, PacketHandler::handleClientLogin);
            registerPacket(0x26, 758, 755, PacketHandler::handleClientLogin);
            registerPacket(0x24, 754, 751, PacketHandler::handleClientLogin);
            registerPacket(0x25, 736, 735, PacketHandler::handleClientLogin);
            registerPacket(0x26, 578, 550, PacketHandler::handleClientLogin);
            registerPacket(0x25, 498, 389, PacketHandler::handleClientLogin);
            registerPacket(0x24, 388, 345, PacketHandler::handleClientLogin);
            registerPacket(0x23, 344, 332, PacketHandler::handleClientLogin);
            registerPacket(0x24, 331, 318, PacketHandler::handleClientLogin);
            registerPacket(0x23, 317, 86, PacketHandler::handleClientLogin);
            registerPacket(0x24, 85, 67, PacketHandler::handleClientLogin);
            registerPacket(0x01, 66, 47, PacketHandler::handleClientLogin);

            // Synchronize Player Position
            registerPacket(0x40, 767, 766, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x3E, 765, 764, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x3C, 763, 762, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x38, 761, 761, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x39, 760, 760, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x36, 759, 759, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x38, 758, 755, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x34, 754, 751, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x35, 736, 735, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x36, 578, 550, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x35, 498, 471, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x33, 470, 451, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x32, 450, 389, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x31, 388, 352, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x30, 351, 345, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x2F, 344, 336, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x2E, 335, 332, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x2F, 331, 318, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x2E, 317, 86, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x2F, 85, 80, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x2E, 79, 67, PacketHandler::handleClientPlayerPositionRotation);
            registerPacket(0x08, 66, 47, PacketHandler::handleClientPlayerPositionRotation);

            // Respawn
            registerPacket(0x47, 767, 766, PacketHandler::handleClientRespawn);
            registerPacket(0x45, 765, 765, PacketHandler::handleClientRespawn);
            registerPacket(0x43, 764, 764, PacketHandler::handleClientRespawn);
            registerPacket(0x41, 763, 762, PacketHandler::handleClientRespawn);
            registerPacket(0x3D, 761, 761, PacketHandler::handleClientRespawn);
            registerPacket(0x3E, 760, 760, PacketHandler::handleClientRespawn);
            registerPacket(0x3B, 759, 759, PacketHandler::handleClientRespawn);
            registerPacket(0x3D, 758, 755, PacketHandler::handleClientRespawn);
            registerPacket(0x39, 754, 751, PacketHandler::handleClientRespawn);
            registerPacket(0x3A, 736, 735, PacketHandler::handleClientRespawn);
            registerPacket(0x3B, 578, 550, PacketHandler::handleClientRespawn);
            registerPacket(0x3A, 498, 471, PacketHandler::handleClientRespawn);
            registerPacket(0x39, 470, 451, PacketHandler::handleClientRespawn);
            registerPacket(0x38, 450, 389, PacketHandler::handleClientRespawn);
            registerPacket(0x37, 388, 352, PacketHandler::handleClientRespawn);
            registerPacket(0x36, 351, 345, PacketHandler::handleClientRespawn);
            registerPacket(0x35, 344, 336, PacketHandler::handleClientRespawn);
            registerPacket(0x34, 335, 332, PacketHandler::handleClientRespawn);
            registerPacket(0x35, 331, 318, PacketHandler::handleClientRespawn);
            registerPacket(0x33, 317, 86, PacketHandler::handleClientRespawn);
            registerPacket(0x34, 85, 80, PacketHandler::handleClientRespawn);
            registerPacket(0x33, 79, 67, PacketHandler::handleClientRespawn);
            registerPacket(0x07, 66, 47, PacketHandler::handleClientRespawn);
        }
    },

    SERVERBOUND("Serverbound") {
        {
            // Set Player Position
            registerPacket(0x1A, 767, 766, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x17, 765, 765, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x16, 764, 764, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x14, 763, 762, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x13, 761, 761, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x14, 760, 760, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x13, 759, 759, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x11, 758, 755, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x12, 754, 735, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x11, 578, 471, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x12, 470, 464, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x10, 463, 389, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x0E, 388, 386, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x0C, 385, 343, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x0D, 342, 336, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x0E, 335, 332, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x0D, 331, 318, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x0C, 317, 77, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x0B, 76, 67, PacketHandler::handleServerPlayerPosition);
            registerPacket(0x04, 66, 47, PacketHandler::handleServerPlayerPosition);

            // Set Player Position and Rotation
            registerPacket(0x1B, 767, 766, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x18, 765, 765, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x17, 764, 764, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x15, 763, 762, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x14, 761, 761, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x15, 760, 760, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x14, 759, 759, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x12, 758, 755, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x13, 754, 735, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x12, 578, 471, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x13, 470, 464, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x11, 463, 389, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x0F, 388, 386, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x0D, 385, 343, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x0E, 342, 336, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x0F, 335, 332, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x0E, 331, 318, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x0D, 317, 77, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x0C, 76, 67, PacketHandler::handleServerPlayerPositionRotation);
            registerPacket(0x06, 66, 47, PacketHandler::handleServerPlayerPositionRotation);

            // Set Player Rotation
            registerPacket(0x1C, 767, 766, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x19, 765, 765, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x18, 764, 764, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x16, 763, 762, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x15, 761, 761, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x16, 760, 760, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x15, 759, 759, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x13, 758, 755, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x14, 754, 735, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x13, 578, 471, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x14, 470, 464, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x12, 463, 389, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x10, 388, 386, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x0E, 385, 343, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x0F, 342, 336, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x10, 335, 332, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x0F, 331, 318, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x0E, 317, 77, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x0D, 76, 67, PacketHandler::handleServerPlayerRotation);
            registerPacket(0x05, 66, 47, PacketHandler::handleServerPlayerRotation);
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