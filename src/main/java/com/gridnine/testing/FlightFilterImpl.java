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
    private boolean isValidFlight(Flight flight) {
        List<Segment> segments = flight.getSegments();

        for (Segment segment : segments) {
            if (segment.getDepartureDate().isBefore(LocalDateTime.now()) ||
                segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                return false;
            }
        }

        Duration totalGroundTime = Duration.ZERO;

        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime arrival1 = segments.get(i).getArrivalDate();
            LocalDateTime departure2 = segments.get(i + 1).getDepartureDate();
            Duration groundTime = Duration.between(arrival1, departure2);
            totalGroundTime = totalGroundTime.plus(groundTime);
            if (totalGroundTime.toHours() > 2) {
                return false;
            }
        }
        return true;
    }
}
