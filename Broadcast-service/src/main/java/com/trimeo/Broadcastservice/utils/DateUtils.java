package com.trimeo.Broadcastservice.utils;

import com.trimeo.Broadcastservice.configs.TimeConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class DateUtils {

    @NonNull
    private final TimeConfig timeConfig;

    public  String getZonedTimeNow() {
        Instant instant = Instant.now();
        ZoneId zoneId = ZoneId.of(timeConfig.getTimezone());
        return ZonedDateTime.ofInstant(instant, zoneId)
                .format(DateTimeFormatter.ofPattern(timeConfig.getFormat()));
    }
}
