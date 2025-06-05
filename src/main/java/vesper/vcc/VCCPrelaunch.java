package vesper.vcc;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class VCCPrelaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        /*Path config = Load.PLATFORM.getConfigLocation();
        try {
            FilterUtils.loadFilters(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.setOut(new FilterUtils.SystemPrintFilter(System.out));
        Logger.getLogger("").setFilter(record -> !FilterUtils.shouldFilter(record.getMessage()));
        ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger()).addFilter(
                new AbstractFilter() {
                    @Override
                    public Result filter(LogEvent event) {
                        return FilterUtils.shouldFilter(event.getMessage().getFormattedMessage()) ? Result.DENY : Result.NEUTRAL;
                    }
                }
        );*/
    }
}
