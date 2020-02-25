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

import io.github.lxgaming.location.common.LocationImpl;
import io.github.lxgaming.location.common.entity.Locale;
import io.github.lxgaming.location.common.manager.LocaleManager;
import io.github.lxgaming.location.common.util.text.adapter.LocaleAdapter;

import java.util.List;
import java.util.UUID;

public class ReloadCommand extends Command {
    
    @Override
    public boolean prepare() {
        addAlias("Reload");
        permission("location.reload.base");
        return true;
    }
    
    @Override
    public void execute(UUID uniqueId, List<String> arguments) throws Exception {
        if (LocationImpl.getInstance().reload()) {
            LocaleManager.prepare();
            LocaleAdapter.sendMessage(uniqueId, Locale.COMMAND_RELOAD_SUCCESS);
            return;
        }
        
        LocaleAdapter.sendMessage(uniqueId, Locale.COMMAND_RELOAD_FAILURE);
    }
}
