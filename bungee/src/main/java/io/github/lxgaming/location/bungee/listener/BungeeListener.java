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

package io.github.lxgaming.location.bungee.listener;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.bungee.util.BungeeToolbox;
import io.github.lxgaming.location.common.data.UserImpl;

public class BungeeListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPostLogin(PostLoginEvent event) {
        UserImpl user = UserImpl.of(event.getPlayer().getName(), event.getPlayer().getUniqueId(), event.getPlayer().getPendingConnection().getVersion());
        Location.getInstance().getUsers().add(user);
        
        if (BungeeToolbox.addChannel(user, event.getPlayer())) {
            Location.getInstance().getLogger().debug("Successfully added channel for {} ({})", event.getPlayer().getName(), event.getPlayer().getUniqueId());
        } else {
            Location.getInstance().getLogger().warn("Failed to add channel for {} ({})", event.getPlayer().getName(), event.getPlayer().getUniqueId());
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onServerConnected(ServerConnectedEvent event) {
        Location.getInstance().getUser(event.getPlayer().getUniqueId()).ifPresent(user -> user.setServer(event.getServer().getInfo().getName()));
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        Location.getInstance().getUsers().removeIf(user -> user.getUniqueId().equals(event.getPlayer().getUniqueId()));
        if (BungeeToolbox.removeChannel(event.getPlayer())) {
            Location.getInstance().getLogger().debug("Successfully removed channel for {} ({})", event.getPlayer().getName(), event.getPlayer().getUniqueId());
        } else {
            Location.getInstance().getLogger().warn("Failed to remove channel for {} ({})", event.getPlayer().getName(), event.getPlayer().getUniqueId());
        }
    }
}