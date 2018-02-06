package com.airport.util;

public class QueryHolder {
    public static String GET_AIRPORTS_BY_NAME = "SELECT airport FROM AirportEntity airport WHERE airport.name = :name";
    public static String GET_AIRPORTS_BY_CITY_NAME = "SELECT airport FROM AirportEntity airport WHERE airport.city.name = :name";
    public static String GET_CITY_BY_NAME = "SELECT city FROM CityEntity city WHERE city.name = :name";
    public static String GET_FLIGHT_ENTITY_FROM_DATE = "select flight from FlightEntity flight \" +\n" +
            "                            \"where flight.departureTime >= :date and \" +\n" +
            "                            \"flight.departureTime <= :dateFor";
    public static String REMOVE_FLIGHT_ENTITY_FROM_DATE = "delete from FlightEntity flight \" +\n" +
            "                            \"where flight.departureTime >= :date and \" +\n" +
            "                            \"flight.departureTime <= :dateFor";
    public static String GET_FLIGHT_ENTITY_WITH_FILTER = "select flight from FlightEntity flight " +
            "where flight.airportFromId in " +
            "(select airport.id from AirportEntity airport " +
            "where airport.cityId = " +
            "(select city.id from CityEntity city " +
            "where city.name = :cityFrom)) " +
            "and flight.airportToId in " +
            "(select airport.id from AirportEntity airport " +
            "where airport.cityId = " +
            "(select city.id from CityEntity city " +
            "where city.name = :cityTo)) " +
            "and flight.departureTime >= :date " +
            "and flight.departureTime <= :dateFor " +
            "and flight.cost <= :hCost";
    public static String GET_WITH_COMPLEX_FILTER = "select flight from FlightEntity flight " +
            "where flight.airportFromId in " +
            "(select airport.id from AirportEntity airport " +
            "where airport.cityId = " +
            "(select city.id from CityEntity city " +
            "where city.name = :cityFrom)) " +
            "and flight.departureTime >= :date " +
            "and flight.departureTime <= :dateFor " +
            "and flight.cost <= :hCost";

}
