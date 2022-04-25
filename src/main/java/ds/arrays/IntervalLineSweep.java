package ds.arrays;

import java.util.*;
import java.util.stream.Collectors;

public class IntervalLineSweep {

    /**
     * union and intersection problems
     * overlap and capacity
     * use minHeap
     */

    public static void main(String[] args) {
        IntervalLineSweep intervalLineSweep = new IntervalLineSweep();
        int[][] schedule = {{10, 13}, {7, 11}, {15, 17}};
        System.out.println("can attend all meetings   " + intervalLineSweep.doMeetingsOverlap(schedule));
        System.out.println("max simultaneous meetings   " + intervalLineSweep.maxSimultaneousMeetings(schedule));
        System.out.println("min meetings rooms   " + intervalLineSweep.minMeetingRooms(schedule));
        int[][] trips = {{9,3,4},{9,1,7},{4,2,4},{7,4,5}};
        System.out.println("can do car pooling   " + intervalLineSweep.isCarPoolingPossible(trips, 23));
        int[][] intervals = {{3, 5}, {12, 15}};
        int[] interval = {6, 6};
        System.out.println("insert interval   " + intervalLineSweep.insertInterval(intervals, interval));

/*        ArrayList<ArrayList<Integer>> intervals = new ArrayList<>();
        intervals.add(new ArrayList<>(Arrays.asList(1, 3)));
        intervals.add(new ArrayList<>(Arrays.asList(5, 7)));
        intervals.add(new ArrayList<>(Arrays.asList(2, 4)));
        intervals.add(new ArrayList<>(Arrays.asList(6, 8)));
        intervalLineSweep.getMergedIntervals(intervals);*/
    }

    // is there any overlap?
    public boolean doMeetingsOverlap(int[][] schedule) {
        if (schedule == null || schedule.length < 2) {
            return true;
        }

        List<List<Integer>> meetings = Arrays.stream(schedule)
                .map(a -> Arrays.stream(a).boxed().collect(Collectors.toList()))
                .collect(Collectors.toList());

        meetings.sort(Comparator.comparing(list -> list.get(0)));

        List<Integer> prev = null;
        for (List<Integer> curr: meetings) {
            if (prev != null) {
                if (prev.get(1) > curr.get(0)) {
                    // overlap
                    return false;
                }
            }
            prev = curr;
        }

        return true;
    }

    public int[][] mergeIntervals(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return intervals;
        }

        Integer[][] spans = Arrays.stream(intervals)
                .map(a -> Arrays.stream(a).boxed().toArray(Integer[]::new))
                .toArray(Integer[][]::new);
        Arrays.sort(spans, Comparator.comparing(a -> a[0]));

        List<Integer[]> mergedSpans = new ArrayList<>();
        Integer[] lastSpan = spans[0];
        mergedSpans.add(lastSpan);
        for (int i=1; i<spans.length; i++) {
            Integer[] span = spans[i];
            if (span[0] <= lastSpan[1]) {
                // overlap - merge - update last interval
                lastSpan[1] = Math.max(span[1], lastSpan[1]);
            } else {
                // no overlap - insert new interval
                mergedSpans.add(span);
                lastSpan = span;
            }
        }

        int[][] mergedIntervals = mergedSpans.stream()
                .map(a -> Arrays.stream(a).mapToInt(Integer::intValue).toArray())
                .toArray(int[][]::new);
        return mergedIntervals;
    }

    public int[][] insertInterval(int[][] intervals, int[] interval) {
        if (intervals == null || intervals.length < 1) {
            intervals = new int[1][2];
            intervals[0] = interval;
            return intervals;
        }

        Integer[][] spans = Arrays.stream(intervals)
                .map(a -> Arrays.stream(a).boxed().toArray(Integer[]::new))
                .toArray(Integer[][]::new);
        Arrays.sort(spans, Comparator.comparing(a -> a[0]));

        List<Integer[]> mergedSpans = new ArrayList<>();
        Integer[] mergedSpan = null;
        for (int i=0; i<spans.length; i++) {
            Integer[] span = spans[i];
            if (mergedSpan == null) {
                // far left - no overlap - add both
                if (interval[1] < span[0] ) {
                    mergedSpans.add(Arrays.stream(interval).boxed().toArray(Integer[]::new));
                    mergedSpans.add(span);
                    mergedSpan = span;
                    continue;
                }

                // far right - no overlap
                if (span[1] < interval[0] ) {
                    mergedSpans.add(span);
                    continue;
                }

                // overlap
                if (span[0] <= interval[1] || interval[0] <= span[1]) {
                    span[0] = Math.min(span[0], interval[0]);
                    span[1] = Math.max(span[1], interval[1]);
                    mergedSpan = span;
                    mergedSpans.add(mergedSpan);
                    continue;
                }

            }

            // overlap
            if (mergedSpan != null && span[0] <= mergedSpan[1]) {
                mergedSpan[1] = Math.max(span[1], mergedSpan[1]);
                continue;
            }

            // no overlap
            mergedSpans.add(span);
        }

        // edge case - interval is at far right
        if (mergedSpan == null) {
            mergedSpan = Arrays.stream(interval).boxed().toArray(Integer[]::new);
            mergedSpans.add(mergedSpan);
        }

        int[][] mergedIntervals = mergedSpans.stream()
                .map(a -> Arrays.stream(a).mapToInt(Integer::intValue).toArray())
                .toArray(int[][]::new);
        return mergedIntervals;
    }

    /**
     * vertical - calculation of concurrent events => minHeap
     */

    public int maxSimultaneousMeetings(int[][] schedule) {
        if (schedule == null || schedule.length < 1) {
            return 0;
        }

        if (schedule.length < 2) {
            return 1;
        }

        List<List<Integer>> meetings = Arrays.stream(schedule)
                .map(a -> Arrays.stream(a).boxed().collect(Collectors.toList()))
                .collect(Collectors.toList());
        // sort by start time
        meetings.sort(Comparator.comparing(list -> list.get(0)));

        // sort by end time
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>(Comparator.comparing(list -> list.get(1)));

        int maxSimultaneousMeetings = 0;
        for (List<Integer> curr: meetings) {
            // clean up
            while (!pq.isEmpty() && pq.peek().get(1) <= curr.get(0)) {
                pq.poll();
            }

            // add this meeting
            pq.offer(curr);

            // calculate meetings
            int simultaneousMeetings = pq.size();
            maxSimultaneousMeetings = Math.max(maxSimultaneousMeetings, simultaneousMeetings);
        }

        return maxSimultaneousMeetings;
    }

    // min meeting rooms = max simultaneous meetings
    public int minMeetingRooms(int[][] schedule) {
        if (schedule == null || schedule.length < 1) {
            return 0;
        }

        int n = schedule.length;;

        if (n < 2) {
            return 1;
        }

        Integer[][] meetings = Arrays.stream(schedule)
                .map(a -> Arrays.stream(a).boxed().toArray(Integer[]::new))
                .toArray(Integer[][]::new);
        // sort by start time
        Arrays.sort(meetings, Comparator.comparing(a -> a[0]));

        // sort by end time
        PriorityQueue<Integer[]> pq = new PriorityQueue<>(Comparator.comparing(a -> a[1]));

        int maxSimultaneousMeetings = 0;
        int nextMeetingStart = -1;
        for (int i=0; i<n; i++) {
            if (i == n-1) {
                nextMeetingStart = Integer.MAX_VALUE;
            } else {
                nextMeetingStart = meetings[i+1][0];
            }

            // start the meeting
            pq.offer(meetings[i]);

            // process the meeting
            int simultaneousMeetings = pq.size();
            maxSimultaneousMeetings = Math.max(simultaneousMeetings, maxSimultaneousMeetings);

            // end the meeting
            // clean up - if there is any meeting that ends before (or at) the start of the next meeting then end that meeting
            // including (but not limited to) the current meeting
            while (!pq.isEmpty() && pq.peek()[1] <= nextMeetingStart) {
                pq.poll();
            }
        }

        return maxSimultaneousMeetings;
    }

    public boolean isCarPoolingPossible(int[][] trips, int capacity) {
        if (trips == null || trips.length < 1) {
            return true;
        }

        Integer[][] poolTrips = Arrays.stream(trips)
                .map(a -> Arrays.stream(a).boxed().toArray(Integer[]::new))
                .toArray(Integer[][]::new);
        // sort by start time
        Arrays.sort(poolTrips, Comparator.comparing(a -> a[1]));

        // sort by end time
        PriorityQueue<Integer[]> pq = new PriorityQueue<>(Comparator.comparing(a -> a[2]));

        int occupancy = 0;
        for (int i=0; i< poolTrips.length; i++) {
            Integer[] trip = poolTrips[i];
            // clean up old trips
            while (!pq.isEmpty() && pq.peek()[2] <= trip[1]) {
                occupancy -= pq.peek()[0];
                pq.poll();
            }

            // start this trip
            occupancy += trip[0];
            if (occupancy <= capacity) {
                pq.offer(trip);
            } else {
                return false;
            }
        }

        return true;
    }

    // TODO
    public int[] employeeFreeTime() {
        return null;
    }


    ArrayList<ArrayList<Integer>> getMergedIntervals(ArrayList<ArrayList<Integer>> intervals) {

        if (intervals == null || intervals.size() < 2) {
            return intervals;
        }

        ArrayList<ArrayList<Integer>> mergedIntervals = new ArrayList();

        // sort intervals based on start time
        intervals.sort(Comparator.comparing((list) -> list.get(0)));

        ArrayList<Integer> mergedInterval = new ArrayList<>();
        for (ArrayList<Integer> interval: intervals) {
            if (mergedInterval.size() == 0) {
                mergedInterval.add(interval.get(0));
                mergedInterval.add(interval.get(1));
                mergedIntervals.add(mergedInterval);
                continue;
            }

            if (mergedInterval.get(1) >= interval.get(0)) {
                mergedInterval.set(1, Math.max(interval.get(1), mergedInterval.get(1)));
            } else {
                mergedInterval = new ArrayList<>();
                mergedInterval.add(interval.get(0));
                mergedInterval.add(interval.get(1));
                mergedIntervals.add(mergedInterval);
            }
        }

        return mergedIntervals;
    }

}
