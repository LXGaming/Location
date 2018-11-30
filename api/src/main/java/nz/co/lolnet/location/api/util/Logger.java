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

package nz.co.lolnet.location.api.util;

import java.util.function.Consumer;

public interface Logger {
    
    void debug(String format, Object... arguments);
    
    void info(String format, Object... arguments);
    
    void warn(String format, Object... arguments);
    
    void error(String format, Object... arguments);
    
    void log(Level level, String format, Object... arguments);
    
    Logger add(Level level, Consumer<String> consumer);
    
    enum Level {
        
        DEBUG("Debug"),
        
        INFO("Info"),
        
        WARN("Warn"),
        
        ERROR("Error");
        
        private final String friendlyName;
        
        Level(String friendlyName) {
            this.friendlyName = friendlyName;
        }
        
        public String getFriendlyName() {
            return friendlyName;
        }
    }
}