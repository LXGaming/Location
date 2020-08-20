/*
 * Copyright 2019 Alex Thomson
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

package io.github.lxgaming.location.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ToolboxTest {
    
    @Test
    public void testIsUsername() {
        // Mojang - https://minecraft.gamepedia.com/Mojang_AB
        Assertions.assertTrue(Toolbox.isUsername("jeb_"));
        Assertions.assertTrue(Toolbox.isUsername("Dinnerbone"));
        Assertions.assertTrue(Toolbox.isUsername("Searge"));
        Assertions.assertTrue(Toolbox.isUsername("_tommo_"));
        Assertions.assertTrue(Toolbox.isUsername("LadyAgnes"));
        Assertions.assertTrue(Toolbox.isUsername("Grumm"));
        Assertions.assertTrue(Toolbox.isUsername("ProfMobius"));
        Assertions.assertTrue(Toolbox.isUsername("fry_"));
        Assertions.assertTrue(Toolbox.isUsername("slicedlime"));
        Assertions.assertTrue(Toolbox.isUsername("peterixxx"));
        
        // Invalid
        Assertions.assertFalse(Toolbox.isUsername("No"));
        Assertions.assertFalse(Toolbox.isUsername("ThisNameIsTooLong"));
        Assertions.assertFalse(Toolbox.isUsername("[]\\{}|"));
        Assertions.assertFalse(Toolbox.isUsername("::'"));
        Assertions.assertFalse(Toolbox.isUsername("<>?,./"));
    }
    
    @Test
    public void testNormalizeYaw() {
        Assertions.assertEquals(0, Toolbox.normalizeYaw(0));
        Assertions.assertEquals(180, Toolbox.normalizeYaw(180));
        Assertions.assertEquals(0, Toolbox.normalizeYaw(360));
        Assertions.assertEquals(90, Toolbox.normalizeYaw(450));
        Assertions.assertEquals(180, Toolbox.normalizeYaw(-180));
        Assertions.assertEquals(270, Toolbox.normalizeYaw(-450));
    }
}