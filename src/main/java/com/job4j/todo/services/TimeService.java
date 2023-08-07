package com.job4j.todo.services;

import com.job4j.todo.model.Task;
import com.job4j.todo.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class TimeService {


    public static LocalDateTime toUTCZeroTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        /* minusHours(3) - приводим UTC-3 к UTC-0 */
        return currentDateTime.atZone(ZoneOffset.UTC).minusHours(3).toLocalDateTime();
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
