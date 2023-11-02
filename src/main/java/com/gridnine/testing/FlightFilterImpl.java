package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightFilterImpl implements FlightFilter {
    @Override
    public List<Flight> filterFlights(final List<Flight> flights) {
        List<Flight> filteredFlights = new ArrayList<>();

        for (Flight flight : flights) {
            if (isValidFlight(flight)) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }

    /**
     * Rule 1: departure to the current point in time
     * <p>
     * Rule 2: there are segments with an arrival date earlier than the departure date
     * <p>
     * Rule 3: Total time spent on the ground exceeds two hours
     *
     * @param flight
     */
    boolean isValidFlight(Flight flight) {
        List<Segment> segments = flight.getSegments();

        if (isDepartureNotBeforeCurrentTime(segments)) return false;

        if (isArrivalNotBeforeDeparture(segments)) return false;

        if (!isFlightMoreThan2HoursOnGround(segments)) return false;

        return true;
    }

    private static boolean isFlightMoreThan2HoursOnGround(List<Segment> segments) {
        Duration totalGroundTime = Duration.ZERO;

        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime arrival1 = segments.get(i).getArrivalDate();
            LocalDateTime departure2 = segments.get(i + 1).getDepartureDate();
            Duration groundTime = Duration.between(arrival1, departure2);
            totalGroundTime = totalGroundTime.plus(groundTime);
            if (totalGroundTime.toHours() > 2) {
                System.out.println("Flights with more than 2 hours ground time: " + segments);
                return false;
            }
        }
        return true;
    }

    private static boolean isArrivalNotBeforeDeparture(List<Segment> segments) {
        for (Segment segment : segments) {
            if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                System.out.println("Flights with invalid segments (arrival before departure): " + segments);
                return true;
            }
        }
        return false;
    }

    private static boolean isDepartureNotBeforeCurrentTime(List<Segment> segments) {
        for (Segment segment : segments) {
            if (segment.getDepartureDate().isBefore(LocalDateTime.now())) {
                System.out.println("Flights with departure before current time: " + segments);
                return true;
            }
        }
        return false;
    }
}
