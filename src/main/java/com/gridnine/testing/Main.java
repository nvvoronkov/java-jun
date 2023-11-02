package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("All flights: ");
        flights.forEach(System.out::println);

        FlightFilter flightFilter = new FlightFilterImpl();

        List<Flight> filteredFlights = flightFilter.filterFlights(flights);

        System.out.println("Filtered flights: ");
        filteredFlights.forEach(System.out::println);
    }
}