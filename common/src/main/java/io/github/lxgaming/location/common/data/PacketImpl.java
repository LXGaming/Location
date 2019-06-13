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

package io.github.lxgaming.location.common.data;

import io.netty.buffer.ByteBuf;
import io.github.lxgaming.location.api.data.Packet;

import java.util.function.BiConsumer;

public class PacketImpl implements Packet {
    
    private final int id;
    private final int maxProtocol;
    private final int minProtocol;
    private final BiConsumer<UserImpl, ByteBuf> consumer;
    
    private PacketImpl(int id, int maxProtocol, int minProtocol, BiConsumer<UserImpl, ByteBuf> consumer) {
        this.id = id;
        this.maxProtocol = maxProtocol;
        this.minProtocol = minProtocol;
        this.consumer = consumer;
    }
    
    public static PacketImpl of(int id, int maxProtocol, int minProtocol, BiConsumer<UserImpl, ByteBuf> consumer) {
        return new PacketImpl(id, maxProtocol, minProtocol, consumer);
    }
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public int getMaxProtocol() {
        return maxProtocol;
    }
    
    @Override
    public int getMinProtocol() {
        return minProtocol;
    }
    
    @Override
    public BiConsumer<UserImpl, ByteBuf> getConsumer() {
        return consumer;
    }
}