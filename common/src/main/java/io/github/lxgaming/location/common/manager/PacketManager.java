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

package io.github.lxgaming.location.common.manager;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.lxgaming.location.common.entity.PacketImpl;
import io.github.lxgaming.location.common.entity.UserImpl;
import io.github.lxgaming.location.common.util.Toolbox;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.Map;

public final class PacketManager {
    
    private static final List<PacketImpl> CLIENT_PACKETS = Lists.newArrayList();
    private static final List<PacketImpl> SERVER_PACKETS = Lists.newArrayList();
    private static final Map<Integer, String> PROTOCOL_VERSIONS = Maps.newHashMap();
    
    public static void prepare() {
        // https://github.com/LXGaming/Powershell/tree/master/MinecraftMapping
        
        // Client
        // - PlayerLook
        CLIENT_PACKETS.add(new PacketImpl(0x13, 578, 471, PacketManager::handleClientPlayerLook));
        CLIENT_PACKETS.add(new PacketImpl(0x14, 470, 464, PacketManager::handleClientPlayerLook));
        CLIENT_PACKETS.add(new PacketImpl(0x12, 463, 389, PacketManager::handleClientPlayerLook));
        CLIENT_PACKETS.add(new PacketImpl(0x10, 388, 386, PacketManager::handleClientPlayerLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0E, 385, 343, PacketManager::handleClientPlayerLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0F, 342, 336, PacketManager::handleClientPlayerLook));
        CLIENT_PACKETS.add(new PacketImpl(0x10, 335, 332, PacketManager::handleClientPlayerLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0F, 331, 318, PacketManager::handleClientPlayerLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0E, 317, 77, PacketManager::handleClientPlayerLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0D, 76, 67, PacketManager::handleClientPlayerLook));
        CLIENT_PACKETS.add(new PacketImpl(0x05, 66, 47, PacketManager::handleClientPlayerLook));
        
        // - PlayerPosition
        CLIENT_PACKETS.add(new PacketImpl(0x11, 578, 471, PacketManager::handleClientPlayerPosition));
        CLIENT_PACKETS.add(new PacketImpl(0x12, 470, 464, PacketManager::handleClientPlayerPosition));
        CLIENT_PACKETS.add(new PacketImpl(0x10, 463, 389, PacketManager::handleClientPlayerPosition));
        CLIENT_PACKETS.add(new PacketImpl(0x0E, 388, 386, PacketManager::handleClientPlayerPosition));
        CLIENT_PACKETS.add(new PacketImpl(0x0C, 385, 343, PacketManager::handleClientPlayerPosition));
        CLIENT_PACKETS.add(new PacketImpl(0x0D, 342, 336, PacketManager::handleClientPlayerPosition));
        CLIENT_PACKETS.add(new PacketImpl(0x0E, 335, 332, PacketManager::handleClientPlayerPosition));
        CLIENT_PACKETS.add(new PacketImpl(0x0D, 331, 318, PacketManager::handleClientPlayerPosition));
        CLIENT_PACKETS.add(new PacketImpl(0x0C, 317, 77, PacketManager::handleClientPlayerPosition));
        CLIENT_PACKETS.add(new PacketImpl(0x0B, 76, 67, PacketManager::handleClientPlayerPosition));
        CLIENT_PACKETS.add(new PacketImpl(0x04, 66, 47, PacketManager::handleClientPlayerPosition));
        
        // - PlayerPositionAndLook
        CLIENT_PACKETS.add(new PacketImpl(0x12, 578, 471, PacketManager::handleClientPlayerPositionAndLook));
        CLIENT_PACKETS.add(new PacketImpl(0x13, 470, 464, PacketManager::handleClientPlayerPositionAndLook));
        CLIENT_PACKETS.add(new PacketImpl(0x11, 463, 389, PacketManager::handleClientPlayerPositionAndLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0F, 388, 386, PacketManager::handleClientPlayerPositionAndLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0D, 385, 343, PacketManager::handleClientPlayerPositionAndLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0E, 342, 336, PacketManager::handleClientPlayerPositionAndLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0F, 335, 332, PacketManager::handleClientPlayerPositionAndLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0E, 331, 318, PacketManager::handleClientPlayerPositionAndLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0D, 317, 77, PacketManager::handleClientPlayerPositionAndLook));
        CLIENT_PACKETS.add(new PacketImpl(0x0C, 76, 67, PacketManager::handleClientPlayerPositionAndLook));
        CLIENT_PACKETS.add(new PacketImpl(0x06, 66, 47, PacketManager::handleClientPlayerPositionAndLook));
        
        // Server
        // - JoinGame
        SERVER_PACKETS.add(new PacketImpl(0x26, 578, 550, PacketManager::handleServerJoinGame));
        SERVER_PACKETS.add(new PacketImpl(0x25, 498, 389, PacketManager::handleServerJoinGame));
        SERVER_PACKETS.add(new PacketImpl(0x24, 388, 345, PacketManager::handleServerJoinGame));
        SERVER_PACKETS.add(new PacketImpl(0x23, 344, 332, PacketManager::handleServerJoinGame));
        SERVER_PACKETS.add(new PacketImpl(0x24, 331, 318, PacketManager::handleServerJoinGame));
        SERVER_PACKETS.add(new PacketImpl(0x23, 317, 86, PacketManager::handleServerJoinGame));
        SERVER_PACKETS.add(new PacketImpl(0x24, 85, 67, PacketManager::handleServerJoinGame));
        SERVER_PACKETS.add(new PacketImpl(0x01, 66, 47, PacketManager::handleServerJoinGame));
        
        // - PlayerPositionAndLook
        SERVER_PACKETS.add(new PacketImpl(0x36, 578, 550, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x35, 498, 471, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x33, 470, 451, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x32, 450, 389, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x31, 388, 352, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x30, 351, 345, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x2F, 344, 336, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x2E, 335, 332, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x2F, 331, 318, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x2E, 317, 86, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x2F, 85, 80, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x2E, 79, 67, PacketManager::handleServerPlayerPositionAndLook));
        SERVER_PACKETS.add(new PacketImpl(0x08, 66, 47, PacketManager::handleServerPlayerPositionAndLook));
        
        // - Respawn
        SERVER_PACKETS.add(new PacketImpl(0x3B, 578, 550, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x3A, 498, 471, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x39, 470, 451, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x38, 450, 389, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x37, 388, 352, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x36, 351, 345, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x35, 344, 336, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x34, 335, 332, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x35, 331, 318, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x33, 317, 86, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x34, 85, 80, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x33, 79, 67, PacketManager::handleServerRespawn));
        SERVER_PACKETS.add(new PacketImpl(0x07, 66, 47, PacketManager::handleServerRespawn));
        
        PROTOCOL_VERSIONS.put(47, "1.8");
        PROTOCOL_VERSIONS.put(107, "1.9");
        PROTOCOL_VERSIONS.put(108, "1.9.1");
        PROTOCOL_VERSIONS.put(109, "1.9.2");
        PROTOCOL_VERSIONS.put(110, "1.9.4");
        PROTOCOL_VERSIONS.put(210, "1.10");
        PROTOCOL_VERSIONS.put(315, "1.11");
        PROTOCOL_VERSIONS.put(316, "1.11.2");
        PROTOCOL_VERSIONS.put(335, "1.12");
        PROTOCOL_VERSIONS.put(338, "1.12.1");
        PROTOCOL_VERSIONS.put(340, "1.12.2");
        PROTOCOL_VERSIONS.put(393, "1.13");
        PROTOCOL_VERSIONS.put(401, "1.13.1");
        PROTOCOL_VERSIONS.put(404, "1.13.2");
        PROTOCOL_VERSIONS.put(477, "1.14");
        PROTOCOL_VERSIONS.put(480, "1.14.1");
        PROTOCOL_VERSIONS.put(485, "1.14.2");
        PROTOCOL_VERSIONS.put(490, "1.14.3");
        PROTOCOL_VERSIONS.put(498, "1.14.4");
        PROTOCOL_VERSIONS.put(573, "1.15");
        PROTOCOL_VERSIONS.put(575, "1.15.1");
        PROTOCOL_VERSIONS.put(578, "1.15.2");
    }
    
    public static void processClientPacket(UserImpl user, ByteBuf byteBuf) {
        int packetId = Toolbox.readVarInt(byteBuf);
        for (PacketImpl packet : CLIENT_PACKETS) {
            if (packet.getId() != packetId) {
                continue;
            }
            
            if (packet.getMaxProtocol() >= user.getProtocolVersion() && packet.getMinProtocol() <= user.getProtocolVersion()) {
                packet.getConsumer().accept(user, byteBuf);
                return;
            }
        }
    }
    
    public static void processServerPacket(UserImpl user, ByteBuf byteBuf) {
        int packetId = Toolbox.readVarInt(byteBuf);
        for (PacketImpl packet : SERVER_PACKETS) {
            if (packet.getId() != packetId) {
                continue;
            }
            
            if (packet.getMaxProtocol() >= user.getProtocolVersion() && packet.getMinProtocol() <= user.getProtocolVersion()) {
                packet.getConsumer().accept(user, byteBuf);
                return;
            }
        }
    }
    
    private static void handleClientPlayerLook(UserImpl user, ByteBuf byteBuf) {
        float yaw = byteBuf.readFloat();
        float pitch = byteBuf.readFloat();
        
        user.setPitch(pitch);
        user.setYaw(yaw);
    }
    
    private static void handleClientPlayerPosition(UserImpl player, ByteBuf byteBuf) {
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        
        player.setX(x);
        player.setY(y);
        player.setZ(z);
    }
    
    private static void handleClientPlayerPositionAndLook(UserImpl player, ByteBuf byteBuf) {
        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        double z = byteBuf.readDouble();
        float yaw = byteBuf.readFloat();
        float pitch = byteBuf.readFloat();
        
        player.setX(x);
        player.setY(y);
        player.setZ(z);
        player.setPitch(pitch);
        player.setYaw(yaw);
    }
    
    private static void handleServerJoinGame(UserImpl player, ByteBuf byteBuf) {
        int entityId = byteBuf.readInt();
        short gamemode = byteBuf.readUnsignedByte();
        int dimension;
        
        // 1.9
        if (player.getProtocolVersion() > 107) {
            dimension = byteBuf.readInt();
        } else {
            dimension = byteBuf.readByte();
        }
        
        player.setDimension(dimension);
    }
    
    private static void handleServerPlayerPositionAndLook(UserImpl user, ByteBuf byteBuf) {
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
        user.setPitch(pitch);
        user.setYaw(yaw);
    }
    
    private static void handleServerRespawn(UserImpl user, ByteBuf byteBuf) {
        int dimension = byteBuf.readInt();
        user.setDimension(dimension);
    }
    
    public static String getProtocolVersion(int protocolVersion) {
        return PROTOCOL_VERSIONS.get(protocolVersion);
    }
}