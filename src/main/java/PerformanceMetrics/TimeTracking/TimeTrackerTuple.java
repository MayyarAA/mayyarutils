package PerformanceMetrics.TimeTracking;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class TimeTrackerTuple {
    final long startTime;
    final long endTime;
    final String key;
}
