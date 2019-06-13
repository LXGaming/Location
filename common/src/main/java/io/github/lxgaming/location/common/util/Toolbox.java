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

package io.github.lxgaming.location.common.util;

import io.netty.buffer.ByteBuf;
import io.github.lxgaming.location.api.util.Reference;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;

public class Toolbox {
    
    public static final String DECODER_HANDLER = Reference.ID + "-decoder";
    public static final String ENCODER_HANDLER = Reference.ID + "-encoder";
    
    public static int getVarIntSize(ByteBuf byteBuf, int input) {
        for (int index = 1; index < 5; ++index) {
            if ((input & -1 << index * 7) == 0) {
                return index;
            }
        }
        
        return 5;
    }
    
    public static int readVarInt(ByteBuf byteBuf) {
        return readVarInt(byteBuf, 5);
    }
    
    public static int readVarInt(ByteBuf byteBuf, int maxBytes) {
        int result = 0;
        int bytesRead = 0;
        
        while (byteBuf.readableBytes() != 0) {
            byte read = byteBuf.readByte();
            result |= (read & 0x7F) << (bytesRead++ * 7);
            if (bytesRead > maxBytes) {
                throw new RuntimeException("VarInt too big");
            }
            
            if ((read & 0x80) != 0x80) {
                break;
            }
        }
        
        return result;
    }
    
    public static boolean isBlank(CharSequence charSequence) {
        int stringLength;
        if (charSequence == null || (stringLength = charSequence.length()) == 0) {
            return true;
        }
        
        for (int index = 0; index < stringLength; index++) {
            if (!Character.isWhitespace(charSequence.charAt(index))) {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }
    
    public static boolean containsIgnoreCase(Collection<String> list, String targetString) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        
        for (String string : list) {
            if (string.equalsIgnoreCase(targetString)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static <T> Optional<T> getField(Object instance, Class<T> typeOfT) {
        try {
            for (Field field : instance.getClass().getDeclaredFields()) {
                if (typeOfT.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);
                    return Optional.ofNullable(typeOfT.cast(field.get(instance)));
                }
            }
            
            return Optional.empty();
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
    
    public static <T> Optional<T> newInstance(Class<? extends T> typeOfT) {
        try {
            return Optional.of(typeOfT.newInstance());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}