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

package io.github.lxgaming.location.velocity.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.common.data.UserImpl;
import io.github.lxgaming.location.velocity.util.VelocityToolbox;

public class VelocityListener {
    
    @Subscribe(order = PostOrder.EARLY)
    public void onPostLogin(PostLoginEvent event) {
        UserImpl user = UserImpl.of(event.getPlayer().getUsername(), event.getPlayer().getUniqueId(), event.getPlayer().getProtocolVersion().getProtocol());
        Location.getInstance().getUsers().add(user);
        if (VelocityToolbox.addChannel(user, event.getPlayer())) {
            Location.getInstance().getLogger().debug("Successfully added channel for {} ({})", event.getPlayer().getUsername(), event.getPlayer().getUniqueId());
        } else {
            Location.getInstance().getLogger().warn("Failed to add channel for {} ({})", event.getPlayer().getUsername(), event.getPlayer().getUniqueId());
        }
    }
    
    @Subscribe(order = PostOrder.EARLY)
    public void onServerConnected(ServerConnectedEvent event) {
        Location.getInstance().getUser(event.getPlayer().getUniqueId()).ifPresent(user -> user.setServer(event.getServer().getServerInfo().getName()));
    }
    
    @Subscribe(order = PostOrder.EARLY)
    public void onDisconnect(DisconnectEvent event) {
        Location.getInstance().getUsers().removeIf(user -> user.getUniqueId().equals(event.getPlayer().getUniqueId()));
    }
}