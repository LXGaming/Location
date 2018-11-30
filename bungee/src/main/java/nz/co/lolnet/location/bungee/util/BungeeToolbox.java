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

package nz.co.lolnet.location.bungee.util;

import io.netty.channel.Channel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.netty.ChannelWrapper;
import net.md_5.bungee.netty.PipelineUtils;
import nz.co.lolnet.location.api.util.Reference;
import nz.co.lolnet.location.common.data.UserImpl;
import nz.co.lolnet.location.common.handler.DecodeHandler;
import nz.co.lolnet.location.common.handler.EncodeHandler;
import nz.co.lolnet.location.common.util.Toolbox;

public class BungeeToolbox {
    
    public static ComponentBuilder getTextPrefix() {
        ComponentBuilder componentBuilder = new ComponentBuilder("");
        componentBuilder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, getPluginInformation().create()));
        componentBuilder.append("[" + Reference.NAME + "]").bold(true).color(ChatColor.BLUE);
        componentBuilder.append(" ", ComponentBuilder.FormatRetention.NONE);
        return componentBuilder;
    }
    
    public static ComponentBuilder getPluginInformation() {
        ComponentBuilder componentBuilder = new ComponentBuilder("")
                .append(Reference.NAME).color(ChatColor.BLUE).bold(true).append("\n")
                .append("    Version: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY).append(Reference.VERSION).color(ChatColor.WHITE).append("\n")
                .append("    Authors: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY).append(Reference.AUTHORS).color(ChatColor.WHITE).append("\n")
                .append("    Source: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY).append(getURLClickEvent(Reference.SOURCE).create()).append("\n")
                .append("    Website: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY).append(getURLClickEvent(Reference.WEBSITE).create());
        return componentBuilder;
    }
    
    public static ComponentBuilder getURLClickEvent(String url) {
        ComponentBuilder componentBuilder = new ComponentBuilder("");
        componentBuilder.event(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        componentBuilder.append(url).color(ChatColor.BLUE);
        componentBuilder.append(" ", ComponentBuilder.FormatRetention.NONE);
        return componentBuilder;
    }
    
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
}