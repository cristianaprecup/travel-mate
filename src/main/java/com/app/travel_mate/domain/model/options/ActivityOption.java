package com.app.travel_mate.domain.model.options;

public class ActivityOption {
    private final int id;
    private final String name;
    private final String category;
    private final String location;
    private final String openTime;
    private final String closeTime;
    private final int typicalDurationMinutes;
    private final int priceAmount;

    public ActivityOption(int id, String name, String category, String location, String openTime, String closeTime, int typicalDurationMinutes, int priceAmount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.location = location;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.typicalDurationMinutes = typicalDurationMinutes;
        this.priceAmount = priceAmount;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public String getOpenTime() { return openTime; }
    public String getCloseTime() { return closeTime; }
    public int getTypicalDurationMinutes() { return typicalDurationMinutes; }
    public int getPriceAmount() { return priceAmount; }

    @Override public String toString() { return "ActivityOption{id=" + id + ", name='" + name + "'}"; }
    @Override public boolean equals(Object o) {
        return (this == o) || (o instanceof ActivityOption a && this.id == a.id);
    }
}
