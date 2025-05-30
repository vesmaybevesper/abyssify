package vesper.abyssify.filter;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import java.util.logging.LogRecord;

/*
 *
 *
 * This code was derived from Log Begone by AzureDoom which is licensed under The GNU General Public License, Version 3
 *
 *
 */

public class Filter extends AbstractFilter implements java.util.logging.Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        return !FilterUtils.shouldFilter(record.getMessage());
    }

    @Override
    public Result filter(LogEvent event) {
        return FilterUtils.shouldFilter(
                "[" + event.getLoggerName() + "]: " + event.getMessage().getFormattedMessage()) ? Result.DENY : Result.NEUTRAL;
    }
}
