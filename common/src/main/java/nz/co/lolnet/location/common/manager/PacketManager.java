/*
 * Copyright 2018 lolnet.co.nz
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

package nz.co.lolnet.location.common.manager;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import nz.co.lolnet.location.common.data.PacketImpl;
import nz.co.lolnet.location.common.data.UserImpl;
import nz.co.lolnet.location.common.util.Toolbox;

import java.util.List;

public final class PacketManager {
    
    private static final List<PacketImpl> CLIENT_PACKETS = Lists.newArrayList();
    private static final List<PacketImpl> SERVER_PACKETS = Lists.newArrayList();
    
    public static void registerPackets() {
        
        // ClientPlayerLook
        getClientPackets().add(PacketImpl.of(0x12, 404, 389, PacketManager::handleClientPlayerLook));
        getClientPackets().add(PacketImpl.of(0x10, 388, 386, PacketManager::handleClientPlayerLook));
        getClientPackets().add(PacketImpl.of(0x0E, 385, 343, PacketManager::handleClientPlayerLook));
        getClientPackets().add(PacketImpl.of(0x0F, 342, 336, PacketManager::handleClientPlayerLook));
        getClientPackets().add(PacketImpl.of(0x10, 335, 332, PacketManager::handleClientPlayerLook));
        getClientPackets().add(PacketImpl.of(0x0F, 331, 318, PacketManager::handleClientPlayerLook));
        getClientPackets().add(PacketImpl.of(0x0E, 317, 77, PacketManager::handleClientPlayerLook));
        getClientPackets().add(PacketImpl.of(0x0D, 76, 67, PacketManager::handleClientPlayerLook));
        getClientPackets().add(PacketImpl.of(0x05, 66, 47, PacketManager::handleClientPlayerLook));
        
        // ClientPlayerPosition
        getClientPackets().add(PacketImpl.of(0x10, 404, 389, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(PacketImpl.of(0x0E, 388, 386, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(PacketImpl.of(0x0C, 385, 343, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(PacketImpl.of(0x0D, 342, 336, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(PacketImpl.of(0x0E, 335, 332, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(PacketImpl.of(0x0D, 331, 318, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(PacketImpl.of(0x0C, 317, 77, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(PacketImpl.of(0x0B, 76, 67, PacketManager::handleClientPlayerPosition));
        getClientPackets().add(PacketImpl.of(0x04, 66, 47, PacketManager::handleClientPlayerPosition));
        
        // ClientPlayerPositionAndLook
        getClientPackets().add(PacketImpl.of(0x11, 404, 389, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(PacketImpl.of(0x0F, 388, 386, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(PacketImpl.of(0x0D, 385, 343, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(PacketImpl.of(0x0E, 342, 336, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(PacketImpl.of(0x0F, 335, 332, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(PacketImpl.of(0x0E, 331, 318, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(PacketImpl.of(0x0D, 317, 77, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(PacketImpl.of(0x0C, 76, 67, PacketManager::handleClientPlayerPositionAndLook));
        getClientPackets().add(PacketImpl.of(0x06, 66, 47, PacketManager::handleClientPlayerPositionAndLook));
        
        // ServerJoinGame
        getServerPackets().add(PacketImpl.of(0x25, 404, 389, PacketManager::handleServerJoinGame));
        getServerPackets().add(PacketImpl.of(0x24, 388, 345, PacketManager::handleServerJoinGame));
        getServerPackets().add(PacketImpl.of(0x23, 344, 332, PacketManager::handleServerJoinGame));
        getServerPackets().add(PacketImpl.of(0x24, 331, 318, PacketManager::handleServerJoinGame));
        getServerPackets().add(PacketImpl.of(0x23, 317, 86, PacketManager::handleServerJoinGame));
        getServerPackets().add(PacketImpl.of(0x24, 85, 67, PacketManager::handleServerJoinGame));
        getServerPackets().add(PacketImpl.of(0x01, 66, 47, PacketManager::handleServerJoinGame));
        
        // ServerPlayerPositionAndLook
        getServerPackets().add(PacketImpl.of(0x32, 404, 389, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(PacketImpl.of(0x31, 388, 352, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(PacketImpl.of(0x30, 351, 345, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(PacketImpl.of(0x2F, 344, 336, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(PacketImpl.of(0x2E, 335, 332, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(PacketImpl.of(0x2F, 331, 318, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(PacketImpl.of(0x2E, 317, 86, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(PacketImpl.of(0x2F, 85, 80, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(PacketImpl.of(0x2E, 79, 67, PacketManager::handleServerPlayerPositionAndLook));
        getServerPackets().add(PacketImpl.of(0x08, 66, 47, PacketManager::handleServerPlayerPositionAndLook));
        
        // ServerRespawn
        getServerPackets().add(PacketImpl.of(0x38, 404, 389, PacketManager::handleServerRespawn));
        getServerPackets().add(PacketImpl.of(0x37, 388, 352, PacketManager::handleServerRespawn));
        getServerPackets().add(PacketImpl.of(0x36, 351, 345, PacketManager::handleServerRespawn));
        getServerPackets().add(PacketImpl.of(0x35, 344, 336, PacketManager::handleServerRespawn));
        getServerPackets().add(PacketImpl.of(0x34, 335, 332, PacketManager::handleServerRespawn));
        getServerPackets().add(PacketImpl.of(0x35, 331, 318, PacketManager::handleServerRespawn));
        getServerPackets().add(PacketImpl.of(0x33, 317, 86, PacketManager::handleServerRespawn));
        getServerPackets().add(PacketImpl.of(0x34, 85, 80, PacketManager::handleServerRespawn));
        getServerPackets().add(PacketImpl.of(0x33, 79, 67, PacketManager::handleServerRespawn));
        getServerPackets().add(PacketImpl.of(0x07, 66, 47, PacketManager::handleServerRespawn));
    }
    
    public static void processClientPacket(UserImpl user, ByteBuf byteBuf) {
        int packetId = Toolbox.readVarInt(byteBuf);
        for (PacketImpl packet : getClientPackets()) {
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
        for (PacketImpl packet : getServerPackets()) {
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
    
    private static List<PacketImpl> getClientPackets() {
        return CLIENT_PACKETS;
    }
    
    private static List<PacketImpl> getServerPackets() {
        return SERVER_PACKETS;
    }
}