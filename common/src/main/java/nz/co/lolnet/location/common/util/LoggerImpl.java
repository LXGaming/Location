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

package nz.co.lolnet.location.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import nz.co.lolnet.location.api.util.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class LoggerImpl implements Logger {
    
    private final Map<Level, Consumer<String>> consumers = Maps.newHashMap();
    
    @Override
    public void debug(String format, Object... arguments) {
        log(Level.DEBUG, format, arguments);
    }
    
    @Override
    public void info(String format, Object... arguments) {
        log(Level.INFO, format, arguments);
    }
    
    @Override
    public void warn(String format, Object... arguments) {
        log(Level.WARN, format, arguments);
    }
    
    @Override
    public void error(String format, Object... arguments) {
        log(Level.ERROR, format, arguments);
    }
    
    @Override
    public void log(Level level, String format, Object... arguments) {
        Optional<Consumer<String>> consumer = getConsumer(level);
        if (consumer.isPresent()) {
            consumer.get().accept(format(format, arguments));
        } else {
            System.out.println(format("[{}] [{}] [{}]: {}",
                    new SimpleDateFormat("HH:mm:ss").format(Instant.now().toEpochMilli()),
                    level.toString(),
                    Thread.currentThread().getName(),
                    format(format, arguments)));
        }
    }
    
    @Override
    public LoggerImpl add(Level level, Consumer<String> consumer) {
        getConsumers().put(level, consumer);
        return this;
    }
    
    private String format(String format, Object... arguments) {
        int index = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(format);
        List<Throwable> throwables = Lists.newArrayList();
        for (Object object : arguments) {
            index = stringBuilder.indexOf("{}", index);
            if (index < 0 || index > stringBuilder.length()) {
                if (object instanceof Throwable) {
                    throwables.add((Throwable) object);
                }
                
                continue;
            }
            
            int length = stringBuilder.length();
            stringBuilder.replace(index, index + 2, getString(object));
            index += stringBuilder.length() - length;
        }
        
        if (!throwables.isEmpty()) {
            stringBuilder.append(System.lineSeparator());
        }
        
        for (Throwable throwable : throwables) {
            stringBuilder.append(getStackTrace(throwable));
        }
        
        return stringBuilder.toString();
    }
    
    private String getString(Object object) {
        if (object != null) {
            return object.toString();
        }
        
        return "null";
    }
    
    private String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        throwable.printStackTrace(printWriter);
        return stringWriter.getBuffer().toString();
    }
    
    private Optional<Consumer<String>> getConsumer(Level level) {
        return Optional.ofNullable(getConsumers().get(level));
    }
    
    private Map<Logger.Level, Consumer<String>> getConsumers() {
        return consumers;
    }
}