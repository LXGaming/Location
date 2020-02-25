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
import io.github.lxgaming.location.common.LocationImpl;
import io.github.lxgaming.location.common.entity.UserImpl;
import io.github.lxgaming.location.velocity.util.VelocityToolbox;

public class VelocityListener {
    
    @Subscribe(order = PostOrder.EARLY)
    public void onPostLogin(PostLoginEvent event) {
        UserImpl user = new UserImpl(event.getPlayer().getUsername(), event.getPlayer().getUniqueId(), event.getPlayer().getProtocolVersion().getProtocol());
        LocationImpl.getInstance().addUser(user);
        
        if (VelocityToolbox.addChannel(user, event.getPlayer())) {
            LocationImpl.getInstance().getLogger().debug("Successfully added channel for {} ({})", event.getPlayer().getUsername(), event.getPlayer().getUniqueId());
        } else {
            LocationImpl.getInstance().getLogger().warn("Failed to add channel for {} ({})", event.getPlayer().getUsername(), event.getPlayer().getUniqueId());
        }
    }
    
    @Subscribe(order = PostOrder.EARLY)
    public void onServerConnected(ServerConnectedEvent event) {
        LocationImpl.getInstance().getUser(event.getPlayer().getUniqueId())
                .filter(user -> user instanceof UserImpl)
                .map(user -> (UserImpl) user)
                .ifPresent(user -> user.setServer(event.getServer().getServerInfo().getName()));
    }
    
    @Subscribe(order = PostOrder.EARLY)
    public void onDisconnect(DisconnectEvent event) {
        LocationImpl.getInstance().getUser(event.getPlayer().getUniqueId()).ifPresent(LocationImpl.getInstance()::removeUser);
    }
}