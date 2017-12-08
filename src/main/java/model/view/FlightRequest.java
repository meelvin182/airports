package model.view;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class FlightRequest {
    private String cityFrom;
    private String cityTo;
    private Timestamp date;
    private BigDecimal cost;
    public FlightRequest(String cityFrom, String cityTo, String date, double cost) {
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.date = Timestamp.valueOf(date);
        this.cost = new BigDecimal(cost);
    }
    public FlightRequest(String cityFrom, String cityTo, Timestamp date, BigDecimal cost) {
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.date = date;
        this.cost = cost;
    }
    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
