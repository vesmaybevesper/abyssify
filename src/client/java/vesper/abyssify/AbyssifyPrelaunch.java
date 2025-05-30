package vesper.abyssify;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import vesper.abyssify.filter.FilterUtils;

import java.util.ArrayList;
import java.util.Map;

public class AbyssifyPrelaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        System.setOut(new FilterUtils.SystemPrintFilter(System.out));
        java.util.logging.Logger.getLogger("").setFilter(FilterUtils.FILTER);

        ArrayList<LoggerConfig> found = new ArrayList<>();
        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
        Map<String, LoggerConfig> map = loggerContext.getConfiguration().getLoggers();

        for (LoggerConfig loggerConfig : map.values()) {
            if (!found.contains(loggerConfig)){
                loggerConfig.addFilter(FilterUtils.FILTER);
                found.add(loggerConfig);
            }
        }
    }
}
