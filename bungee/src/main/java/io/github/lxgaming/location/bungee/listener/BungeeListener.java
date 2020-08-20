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

import io.github.lxgaming.location.bungee.util.BungeeToolbox;
import io.github.lxgaming.location.common.LocationImpl;
import io.github.lxgaming.location.common.entity.ProtocolVersionImpl;
import io.github.lxgaming.location.common.entity.UserImpl;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class BungeeListener implements Listener {
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPostLogin(PostLoginEvent event) {
        UserImpl user = new UserImpl(
                event.getPlayer().getUniqueId(),
                event.getPlayer().getName(),
                ProtocolVersionImpl.getProtocolVersion(event.getPlayer().getPendingConnection().getVersion()));
        LocationImpl.getInstance().addUser(user);
        
        if (BungeeToolbox.addChannel(user, event.getPlayer())) {
            LocationImpl.getInstance().getLogger().debug("Successfully added channel for {} ({})", event.getPlayer().getName(), event.getPlayer().getUniqueId());
        } else {
            LocationImpl.getInstance().getLogger().warn("Failed to add channel for {} ({})", event.getPlayer().getName(), event.getPlayer().getUniqueId());
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onServerConnected(ServerConnectedEvent event) {
        LocationImpl.getInstance().getUser(event.getPlayer().getUniqueId())
                .filter(user -> user instanceof UserImpl)
                .map(user -> (UserImpl) user)
                .ifPresent(user -> user.setServer(event.getServer().getInfo().getName()));
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        LocationImpl.getInstance().getUser(event.getPlayer().getUniqueId()).ifPresent(LocationImpl.getInstance()::removeUser);
        
        if (BungeeToolbox.removeChannel(event.getPlayer())) {
            LocationImpl.getInstance().getLogger().debug("Successfully removed channel for {} ({})", event.getPlayer().getName(), event.getPlayer().getUniqueId());
        } else {
            LocationImpl.getInstance().getLogger().warn("Failed to remove channel for {} ({})", event.getPlayer().getName(), event.getPlayer().getUniqueId());
        }
    }
}