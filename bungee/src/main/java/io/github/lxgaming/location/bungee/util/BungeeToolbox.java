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

import io.github.lxgaming.location.api.Platform;
import io.github.lxgaming.location.common.entity.UserImpl;
import io.github.lxgaming.location.common.handler.DecodeHandler;
import io.github.lxgaming.location.common.handler.EncodeHandler;
import io.github.lxgaming.location.common.util.Toolbox;
import io.netty.channel.Channel;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.netty.ChannelWrapper;
import net.md_5.bungee.netty.PipelineUtils;

import java.util.UUID;

public class BungeeToolbox {
    
    public static boolean addChannel(UserImpl user, Object object) {
        Channel channel = Toolbox.getField(object, ChannelWrapper.class).map(ChannelWrapper::getHandle).orElse(null);
        if (channel != null && channel.pipeline().get(Toolbox.DECODER_HANDLER) == null && channel.pipeline().get(Toolbox.ENCODER_HANDLER) == null) {
            channel.pipeline().addBefore(PipelineUtils.PACKET_DECODER, Toolbox.DECODER_HANDLER, new DecodeHandler(user));
            channel.pipeline().addBefore(PipelineUtils.PACKET_ENCODER, Toolbox.ENCODER_HANDLER, new EncodeHandler(user));
            return true;
        }
        
        return false;
    }
    
    public static boolean removeChannel(Object object) {
        Channel channel = Toolbox.getField(object, ChannelWrapper.class).map(ChannelWrapper::getHandle).orElse(null);
        if (channel != null && channel.pipeline().get(Toolbox.DECODER_HANDLER) != null && channel.pipeline().get(Toolbox.ENCODER_HANDLER) != null) {
            channel.pipeline().remove(Toolbox.DECODER_HANDLER);
            channel.pipeline().remove(Toolbox.ENCODER_HANDLER);
            return true;
        }
        
        return false;
    }
    
    public static UUID getUniqueId(CommandSender sender) {
        if (sender instanceof ProxiedPlayer) {
            return ((ProxiedPlayer) sender).getUniqueId();
        } else {
            return Platform.CONSOLE_UUID;
        }
    }
}