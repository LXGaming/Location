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

package io.github.lxgaming.location.bungee.util;

import io.github.lxgaming.location.bungee.network.netty.PacketHandlerImpl;
import io.github.lxgaming.location.common.entity.UserImpl;
import io.github.lxgaming.location.common.network.netty.PacketHandler;
import io.github.lxgaming.location.common.util.Toolbox;
import io.netty.channel.Channel;
import net.md_5.bungee.netty.ChannelWrapper;
import net.md_5.bungee.netty.PipelineUtils;

public class BungeeToolbox {
    
    public static boolean addChannel(UserImpl user, Object object) {
        Channel channel = Toolbox.getField(object, ChannelWrapper.class).map(ChannelWrapper::getHandle).orElse(null);
        if (channel != null && channel.pipeline().get(PacketHandler.NAME) == null) {
            channel.pipeline().addBefore(PipelineUtils.BOSS_HANDLER, PacketHandler.NAME, new PacketHandlerImpl(user));
            return true;
        }
        
        return false;
    }
    
    public static boolean removeChannel(Object object) {
        Channel channel = Toolbox.getField(object, ChannelWrapper.class).map(ChannelWrapper::getHandle).orElse(null);
        if (channel != null && channel.pipeline().get(PacketHandler.NAME) != null) {
            channel.pipeline().remove(PacketHandler.NAME);
            return true;
        }
        
        return false;
    }
}