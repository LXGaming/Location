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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.lxgaming.location.api.Location;
import io.netty.buffer.ByteBuf;

import java.lang.reflect.Field;
import java.util.Optional;

public class Toolbox {
    
    public static final String DECODER_HANDLER = Location.ID + "-decoder";
    public static final String ENCODER_HANDLER = Location.ID + "-encoder";
    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .create();
    
    public static boolean isUsername(String string) {
        return string.matches("^[a-zA-Z0-9_]{3,16}$");
    }
    
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
    
    public static String getClassSimpleName(Class<?> type) {
        if (type.getEnclosingClass() != null) {
            return getClassSimpleName(type.getEnclosingClass()) + "." + type.getSimpleName();
        }
        
        return type.getSimpleName();
    }
    
    public static <T> T newInstance(Class<? extends T> type) {
        try {
            return type.newInstance();
        } catch (Throwable ex) {
            return null;
        }
    }
}