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

package io.github.lxgaming.location.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import io.github.lxgaming.location.common.LocationImpl;
import io.github.lxgaming.location.common.command.AbstractCommand;
import io.github.lxgaming.location.velocity.util.VelocityToolbox;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

import java.util.List;

public class ReloadCommand extends AbstractCommand {
    
    public ReloadCommand() {
        addAlias("reload");
        setPermission("location.command.reload");
    }
    
    @Override
    public void execute(Object object, List<String> arguments) {
        CommandSource source = (CommandSource) object;
        if (LocationImpl.getInstance().reloadLocation()) {
            source.sendMessage(VelocityToolbox.getTextPrefix().append(TextComponent.of("Configuration reloaded", TextColor.GREEN)));
        } else {
            source.sendMessage(VelocityToolbox.getTextPrefix().append(TextComponent.of("An error occurred. Please check the console", TextColor.RED)));
        }
    }
}