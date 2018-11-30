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

package nz.co.lolnet.location.velocity.util;

import com.velocitypowered.proxy.connection.MinecraftConnection;
import com.velocitypowered.proxy.network.Connections;
import io.netty.channel.Channel;
import net.kyori.text.Components;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;
import nz.co.lolnet.location.api.util.Reference;
import nz.co.lolnet.location.common.data.UserImpl;
import nz.co.lolnet.location.common.handler.DecodeHandler;
import nz.co.lolnet.location.common.handler.EncodeHandler;
import nz.co.lolnet.location.common.util.Toolbox;

public class VelocityToolbox {
    
    public static TextComponent getTextPrefix() {
        TextComponent.Builder textBuilder = TextComponent.builder();
        textBuilder.hoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, getPluginInformation()));
        textBuilder.content("[" + Reference.NAME + "]").color(TextColor.BLUE).decoration(TextDecoration.BOLD, true);
        return TextComponent.of("").append(textBuilder.build()).append(TextComponent.of(" "));
    }
    
    public static TextComponent getPluginInformation() {
        TextComponent.Builder textBuilder = TextComponent.builder("");
        textBuilder.append(TextComponent.of(Reference.NAME, TextColor.BLUE).decoration(TextDecoration.BOLD, true)).append(Components.newline());
        textBuilder.append(TextComponent.of("    Version: ", TextColor.DARK_GRAY)).append(TextComponent.of(Reference.VERSION, TextColor.WHITE)).append(Components.newline());
        textBuilder.append(TextComponent.of("    Authors: ", TextColor.DARK_GRAY)).append(TextComponent.of(Reference.AUTHORS, TextColor.WHITE)).append(Components.newline());
        textBuilder.append(TextComponent.of("    Source: ", TextColor.DARK_GRAY)).append(getURLTextAction(Reference.SOURCE)).append(Components.newline());
        textBuilder.append(TextComponent.of("    Website: ", TextColor.DARK_GRAY)).append(getURLTextAction(Reference.WEBSITE));
        return textBuilder.build();
    }
    
    public static TextComponent getURLTextAction(String url) {
        TextComponent.Builder textBuilder = TextComponent.builder();
        textBuilder.clickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        textBuilder.content(url).color(TextColor.BLUE);
        return textBuilder.build();
    }
    
    public static boolean addChannel(UserImpl user, Object object) {
        Channel channel = Toolbox.getField(object, MinecraftConnection.class).map(MinecraftConnection::getChannel).orElse(null);
        if (channel != null && channel.pipeline().get(Toolbox.DECODER_HANDLER) == null && channel.pipeline().get(Toolbox.ENCODER_HANDLER) == null) {
            channel.pipeline().addBefore(Connections.MINECRAFT_DECODER, Toolbox.DECODER_HANDLER, new DecodeHandler(user));
            channel.pipeline().addBefore(Connections.MINECRAFT_ENCODER, Toolbox.ENCODER_HANDLER, new EncodeHandler(user));
            return true;
        }
        
        return false;
    }
    
    public static boolean removeChannel(Object object) {
        Channel channel = Toolbox.getField(object, MinecraftConnection.class).map(MinecraftConnection::getChannel).orElse(null);
        if (channel != null && channel.pipeline().get(Toolbox.DECODER_HANDLER) != null && channel.pipeline().get(Toolbox.ENCODER_HANDLER) != null) {
            channel.pipeline().remove(Toolbox.DECODER_HANDLER);
            channel.pipeline().remove(Toolbox.ENCODER_HANDLER);
            return true;
        }
        
        return false;
    }
}