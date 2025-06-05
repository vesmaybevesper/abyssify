package vesper.vcc;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;

public class VCCClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MidnightConfig.init("vcc", Config.class);
	}
}