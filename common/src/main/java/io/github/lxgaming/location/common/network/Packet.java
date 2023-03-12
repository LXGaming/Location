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

import io.github.lxgaming.location.common.network.netty.PacketHandler;
import io.netty.buffer.ByteBuf;

import java.util.function.BiConsumer;

public class Packet {

    private final int id;
    private final int maximumProtocol;
    private final int minimumProtocol;
    private final BiConsumer<PacketHandler, ByteBuf> consumer;

    public Packet(int id, int maximumProtocol, int minimumProtocol, BiConsumer<PacketHandler, ByteBuf> consumer) {
        this.id = id;
        this.maximumProtocol = maximumProtocol;
        this.minimumProtocol = minimumProtocol;
        this.consumer = consumer;
    }

    public int getId() {
        return id;
    }

    public int getMaximumProtocol() {
        return maximumProtocol;
    }

    public int getMinimumProtocol() {
        return minimumProtocol;
    }

    public BiConsumer<PacketHandler, ByteBuf> getConsumer() {
        return consumer;
    }
}