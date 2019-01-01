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

package nz.co.lolnet.location.bungee.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import nz.co.lolnet.location.api.Location;
import nz.co.lolnet.location.api.data.User;
import nz.co.lolnet.location.bungee.BungeePlugin;
import nz.co.lolnet.location.bungee.util.BungeeToolbox;
import nz.co.lolnet.location.common.command.AbstractCommand;
import nz.co.lolnet.location.common.manager.PacketManager;

import java.util.List;

public class GetCommand extends AbstractCommand {
    
    public GetCommand() {
        addAlias("get");
        setPermission("location.command.get");
        setUsage("<Player>");
    }
    
    @Override
    public void execute(Object object, List<String> arguments) {
        CommandSender sender = (CommandSender) object;
        if (arguments.isEmpty()) {
            sender.sendMessage(BungeeToolbox.getTextPrefix().append("Invalid arguments: " + getUsage()).color(ChatColor.RED).create());
            return;
        }
        
        String username = arguments.remove(0);
        ProxiedPlayer player = BungeePlugin.getInstance().getProxy().getPlayer(username);
        if (player == null || !player.isConnected()) {
            sender.sendMessage(BungeeToolbox.getTextPrefix().append(username + " is not online").color(ChatColor.RED).create());
            return;
        }
        
        User user = Location.getInstance().getUser(player.getUniqueId()).orElse(null);
        if (user == null) {
            sender.sendMessage(BungeeToolbox.getTextPrefix().append(player.getName() + " not found").color(ChatColor.RED).create());
            return;
        }
        
        String protocolVersion = PacketManager.getProtocolVersion(user.getProtocolVersion()).orElse(String.valueOf(user.getProtocolVersion()));
        
        ComponentBuilder componentBuilder = new ComponentBuilder("");
        componentBuilder.append(user.getUsername()).color(ChatColor.BLUE).bold(true)
                .append(" (" + protocolVersion + ")", ComponentBuilder.FormatRetention.NONE).color(ChatColor.WHITE).append("\n");
        
        componentBuilder.append("Position: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY)
                .append(user.getX() + ", " + user.getY() + ", " + user.getZ()).color(ChatColor.WHITE).append("\n");
        
        componentBuilder.append("Rotation: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY)
                .append(user.getYaw() + ", " + user.getPitch()).color(ChatColor.WHITE).append("\n");
        
        componentBuilder.append("Server: ", ComponentBuilder.FormatRetention.NONE).color(ChatColor.DARK_GRAY)
                .append(user.getServer() + " (" + user.getDimension() + ")").color(ChatColor.WHITE);
        
        sender.sendMessage(componentBuilder.create());
    }
}