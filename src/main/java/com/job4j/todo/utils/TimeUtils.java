package com.job4j.todo.utils;

import com.job4j.todo.model.Task;
import com.job4j.todo.model.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@UtilityClass
public class TimeUtils {

    public LocalDateTime toUTCZeroTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneOffset.systemDefault());
        return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    public List<TimeZone> getTimeZones() {
        var timeZones = new ArrayList<TimeZone>();
        for (String zone : TimeZone.getAvailableIDs()) {
            timeZones.add(TimeZone.getTimeZone(zone));
        }
        return timeZones;
    }

    public void addUserTimeZone(User user, Task task) {
        var defaultZone = TimeZone.getDefault().toZoneId();
        var userZone = user.getTimeZone() == null ? defaultZone : ZoneId.of(user.getTimeZone());
        var time = task.getCreated()
                .atZone(defaultZone)
                .withZoneSameInstant(userZone)
                .toLocalDateTime();
        task.setCreated(time);
    }
}
