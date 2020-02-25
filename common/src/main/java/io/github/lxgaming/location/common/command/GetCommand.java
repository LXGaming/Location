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

package io.github.lxgaming.location.common.command;

import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.api.entity.User;
import io.github.lxgaming.location.common.entity.Locale;
import io.github.lxgaming.location.common.manager.PacketManager;
import io.github.lxgaming.location.common.util.StringUtils;
import io.github.lxgaming.location.common.util.Toolbox;
import io.github.lxgaming.location.common.util.text.adapter.LocaleAdapter;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.format.TextDecoration;

import java.util.List;
import java.util.UUID;

public class GetCommand extends Command {
    
    @Override
    public boolean prepare() {
        addAlias("Get");
        permission("location.get.base");
        usage("<Player>");
        return true;
    }
    
    @Override
    public void execute(UUID uniqueId, List<String> arguments) throws Exception {
        if (arguments.isEmpty()) {
            LocaleAdapter.sendMessage(uniqueId, Locale.COMMAND_INVALID_ARGUMENTS, getUsage());
            return;
        }
        
        String username = arguments.remove(0);
        if (!Toolbox.isUsername(username)) {
            LocaleAdapter.sendMessage(uniqueId, Locale.USER_NAME_INVALID);
            return;
        }
        
        User user = Location.getInstance().getUser(username).orElse(null);
        if (user == null) {
            LocaleAdapter.sendMessage(uniqueId, Locale.COMMAND_GET_USER_NOT_FOUND, username);
            return;
        }
        
        String protocolVersion = StringUtils.defaultIfBlank(
                PacketManager.getProtocolVersion(user.getProtocolVersion()),
                String.valueOf(user.getProtocolVersion())
        );
        
        TextComponent.Builder textBuilder = TextComponent.builder("");
        textBuilder.append(TextComponent.of(user.getUsername(), TextColor.BLUE).decoration(TextDecoration.BOLD, true))
                .append(TextComponent.of(" (" + protocolVersion + ")")).append(TextComponent.newline());
        
        textBuilder.append(TextComponent.of("Position: ", TextColor.DARK_GRAY))
                .append(TextComponent.of(user.getX() + ", " + user.getY() + ", " + user.getZ(), TextColor.WHITE)).append(TextComponent.newline());
        
        textBuilder.append(TextComponent.of("Rotation: ", TextColor.DARK_GRAY))
                .append(TextComponent.of(user.getYaw() + ", " + user.getPitch(), TextColor.WHITE)).append(TextComponent.newline());
        
        textBuilder.append(TextComponent.of("Server: ", TextColor.DARK_GRAY))
                .append(TextComponent.of(user.getServer() + " (" + user.getDimension() + ")", TextColor.WHITE));
        
        Location.getPlatform().sendMessage(uniqueId, textBuilder.build());
    }
}
