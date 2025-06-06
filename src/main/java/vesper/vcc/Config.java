package vesper.vcc;

import eu.midnightdust.lib.config.MidnightConfig;

public class Config extends MidnightConfig {
        public static final String EFFECTIVEWAKES = "Effective x Wakes";

        @Entry(category = EFFECTIVEWAKES) public static boolean EffectiveXWakes = true;
        @Entry(category = EFFECTIVEWAKES) public static boolean oarSplash = true;
        @Entry(category = EFFECTIVEWAKES) public static boolean WakeRenderMixin = true;
}
