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

package io.github.lxgaming.location.common.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.lxgaming.location.common.LocationImpl;
import io.github.lxgaming.location.common.util.Toolbox;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Configuration {
    
    private static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setPrettyPrinting()
            .create();
    
    private final Path path;
    private Config config;
    
    public Configuration(Path path) {
        this.path = path;
    }
    
    public boolean loadConfiguration() {
        Config config = loadFile(this.path.resolve("config.json"), Config.class);
        if (config != null) {
            this.config = config;
            return true;
        }
        
        return false;
    }
    
    public boolean saveConfiguration() {
        return saveFile(this.path.resolve("config.json"), config);
    }
    
    public static <T> T loadFile(Path path, Class<T> type) {
        if (Files.exists(path)) {
            return deserializeFile(path, type);
        }
        
        T object = Toolbox.newInstance(type);
        if (object != null && saveFile(path, object)) {
            return object;
        }
        
        return null;
    }
    
    public static boolean saveFile(Path path, Object object) {
        if (Files.exists(path) || createFile(path)) {
            return serializeFile(path, object);
        }
        
        return false;
    }
    
    public static <T> T deserializeFile(Path path, Class<T> type) {
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return GSON.fromJson(reader, type);
        } catch (Exception ex) {
            LocationImpl.getInstance().getLogger().error("Encountered an error while deserializing {}", path, ex);
            return null;
        }
    }
    
    public static boolean serializeFile(Path path, Object object) {
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            GSON.toJson(object, writer);
            return true;
        } catch (Exception ex) {
            LocationImpl.getInstance().getLogger().error("Encountered an error while serializing {}", path, ex);
            return false;
        }
    }
    
    private static boolean createFile(Path path) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            
            Files.createFile(path);
            return true;
        } catch (Exception ex) {
            LocationImpl.getInstance().getLogger().error("Encountered an error while creating {}", path, ex);
            return false;
        }
    }
    
    public Config getConfig() {
        return config;
    }
}