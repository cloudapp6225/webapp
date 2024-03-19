package com.cloudnativewebapp.webapp.GcpLogging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import com.google.cloud.logging.Severity;
import ch.qos.logback.classic.Level;

import java.util.Map;

import static ch.qos.logback.classic.Level.*;

public class GcpJsonLayout extends JsonLayout {
    private static final String SEVERITY_FIELD = "severity";
    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
        map.put(SEVERITY_FIELD, severityFor(event.getLevel()));
    }
    private static Severity severityFor(Level level) {
        return switch (level.toInt()) {
            // TRACE
            case TRACE_INT -> Severity.DEBUG;
            // DEBUG
            case DEBUG_INT -> Severity.DEBUG;
            // INFO
            case INFO_INT -> Severity.INFO;
            // WARNING
            case WARN_INT -> Severity.WARNING;
            // ERROR
            case ERROR_INT -> Severity.ERROR;
            default -> Severity.DEFAULT;
        };
    }

}
