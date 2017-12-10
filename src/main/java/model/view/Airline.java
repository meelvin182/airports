package model.view;

public enum Airline {
    Aeroflot("Аэрофлот"),
    Transaero("Трансаэро"),
    UralAirlines("Уральские авиалинии"),
    RusLine("РусЛайн"),
    Vostok("Восток"),
    Kosmos("Космос"),
    Seversal("Северсаль"),
    Yamal("Ямал");
    private final String name;
    Airline(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
