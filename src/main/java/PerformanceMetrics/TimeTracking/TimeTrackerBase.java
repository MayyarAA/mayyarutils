package PerformanceMetrics.TimeTracking;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.UUID;

@Data
@Builder
public class TimeTrackerBase implements TimeTracker {
    final HashMap<String, TimeTrackerTuple> timeUnitHashMap;

    public long startTimer() {
        return System.nanoTime();
    }

    public String startTimeSave() {
        final String key = UUID.randomUUID().toString();
        timeUnitHashMap.put(key, TimeTrackerTuple
                .builder()
                .key(key)
                .startTime(startTimer())
                .build());
        return key;
    }

    public long calcRunTime(final long startTime, final long endTime) {
        return endTime - startTime;
    }

    public long calcRunTime(final String className) {
        if (!timeUnitHashMap.containsKey(className)) {
            throw new IllegalStateException(
                    String.format("The className:%s is not presnet in timeUnitDataStore", className));
        }
        final TimeTrackerTuple timeTrackerTuple = timeUnitHashMap.get(className);
        return calcRunTime(timeTrackerTuple.getStartTime(), timeTrackerTuple.getEndTime());
    }

    public void addTimer(final String className) {
        if (timeUnitHashMap.containsKey(className)) {
            final TimeTrackerTuple timeTrackerTupleOrginal = timeUnitHashMap.get(className);
            final TimeTrackerTuple timeTrackerTuple = TimeTrackerTuple
                    .builder()
                    .key(timeTrackerTupleOrginal.getKey())
                    .startTime(timeTrackerTupleOrginal.getStartTime())
                    .endTime(startTimer())
                    .build();
            timeUnitHashMap.put(className, timeTrackerTuple);
        }
        timeUnitHashMap.put(className, TimeTrackerTuple
                .builder()
                .startTime(startTimer())
                .key(className)
                .build());
    }
}
