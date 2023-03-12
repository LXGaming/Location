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

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.lxgaming.location.api.Location;
import io.github.lxgaming.location.api.entity.Source;
import io.github.lxgaming.location.api.entity.User;
import io.github.lxgaming.location.common.entity.Locale;
import io.github.lxgaming.location.common.util.StringUtils;
import io.github.lxgaming.location.common.util.Toolbox;
import io.github.lxgaming.location.common.util.text.adapter.LocaleAdapter;

public class GetCommand extends Command {

    private static final String USERNAME_ARGUMENT = "username";

    @Override
    public boolean prepare() {
        addAlias("Get");
        permission("location.get.base");
        return true;
    }

    @Override
    public void register(LiteralArgumentBuilder<Source> argumentBuilder) {
        argumentBuilder
                .requires(source -> source.hasPermission(getPermission()))
                .executes(context -> {
                    return execute(context.getSource());
                })
                .then(argument(USERNAME_ARGUMENT, StringArgumentType.word())
                        .suggests((context, builder) -> {
                            Location.getPlatform().getUsernames().forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .executes(context -> {
                            return execute(context.getSource(), StringArgumentType.getString(context, USERNAME_ARGUMENT));
                        })
                );
    }

    private int execute(Source source) {
        LocaleAdapter.sendSystemMessage(source, Locale.COMMAND_INVALID_ARGUMENTS, "<Player>");
        return 0;
    }

    private int execute(Source source, String username) {
        if (!Toolbox.isUsername(username)) {
            LocaleAdapter.sendSystemMessage(source, Locale.USER_NAME_INVALID);
            return 0;
        }

        User user = Location.getInstance().getUser(username).orElse(null);
        if (user == null) {
            LocaleAdapter.sendSystemMessage(source, Locale.COMMAND_GET_USER_NOT_FOUND, username);
            return 0;
        }

        String version = StringUtils.defaultIfBlank(
                user.getProtocolVersion().getName(),
                String.valueOf(user.getProtocolVersion().getId())
        );

        String dimension;
        if (user.getDimension() != null) {
            dimension = StringUtils.defaultIfBlank(
                    user.getDimension().getName(),
                    String.valueOf(user.getDimension().getId())
            );
        } else {
            dimension = null;
        }

        LocaleAdapter.sendSystemMessage(source, Locale.COMMAND_GET,
                user.getUsername(), version,
                user.getX(), user.getY(), user.getZ(),
                user.getYaw(), user.getPitch(),
                dimension,
                user.getServer()
        );

        return 1;
    }
}