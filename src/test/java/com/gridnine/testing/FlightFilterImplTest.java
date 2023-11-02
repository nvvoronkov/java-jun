package com.gridnine.testing;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FlightFilterImplTest {

    @Test
    public void testDepartureBeforeCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        List<Segment> segments = List.of(
                new Segment(now.minusHours(1), now.plusHours(2))
        );
        Flight flight = new Flight(segments);
        FlightFilterImpl filter = new FlightFilterImpl();
        assertThat(filter.isValidFlight(flight)).isFalse();
    }

    @Test
    public void testArrivalBeforeDeparture() {
        LocalDateTime now = LocalDateTime.now();
        List<Segment> segments = Arrays.asList(
                new Segment(now, now.plusHours(2)),
                new Segment(now.plusHours(3), now.plusHours(4))
        );
        Flight flight = new Flight(segments);
        FlightFilterImpl filter = new FlightFilterImpl();
        assertThat(filter.isValidFlight(flight)).isFalse();
    }

    @Test
    public void testGroundTimeExceedsTwoHours() {
        LocalDateTime now = LocalDateTime.now();
        List<Segment> segments = Arrays.asList(
                new Segment(now, now.plusHours(2)),
                new Segment(now.plusHours(3), now.plusHours(5))
        );
        Flight flight = new Flight(segments);
        FlightFilterImpl filter = new FlightFilterImpl();
        assertThat(filter.isValidFlight(flight)).isFalse();
    }
}
