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

package io.github.lxgaming.location.common.util;

import io.netty.buffer.ByteBuf;

/**
 * Copyright 2018 Velocity team.
 * <br>
 * Licensed under the MIT license.
 *
 * @see <a href="https://github.com/VelocityPowered/Velocity/blob/1dd900194d181fc771157d9b58015449d572158e/proxy/src/main/java/com/velocitypowered/proxy/protocol/ProtocolUtils.java">ProtocolUtils</a>
 */
public class ProtocolUtils {
    
    /**
     * Reads a Minecraft-style VarInt from the specified {@code buf}.
     *
     * @param buf the buffer to read from
     * @return the decoded VarInt, or {@code Integer.MIN_VALUE} if the varint is invalid
     */
    public static int readVarIntSafely(ByteBuf buf) {
        int i = 0;
        int maxRead = Math.min(5, buf.readableBytes());
        for (int j = 0; j < maxRead; j++) {
            int k = buf.readByte();
            i |= (k & 0x7F) << j * 7;
            if ((k & 0x80) != 128) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }
}