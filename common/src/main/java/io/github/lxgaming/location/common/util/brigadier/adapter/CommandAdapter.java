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

package io.github.lxgaming.location.common.util.brigadier.adapter;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.lxgaming.location.common.command.Command;

public class CommandAdapter<S> implements com.mojang.brigadier.Command<S> {

    private final Command command;
    private final com.mojang.brigadier.Command<S> brigadierCommand;

    public CommandAdapter(Command command, com.mojang.brigadier.Command<S> brigadierCommand) {
        this.command = command;
        this.brigadierCommand = brigadierCommand;
    }

    @Override
    public int run(CommandContext<S> context) throws CommandSyntaxException {
        return brigadierCommand.run(context);
    }

    public Command getCommand() {
        return command;
    }
}