package com.skypro.animalshelter.model;

public class Adress {
    private String town;
    private String street;
    private String house;
    private String building;
    private String flat;

    public Adress(String town, String street, String house, String building, String flat) {

        if (town == null || street == null || house == null || flat == null) {
            throw new NullPointerException();
        }
        this.town = town;
        this.street = street;
        this.house = house;
        if (building!=null) {
        if (building.trim().equals("0") || building.trim().isEmpty()) building = null;}
        this.building = building;
        this.flat = flat;
    }

    public void setTown(String town) {
        if (town != null) this.town = town;
    }

    public void setStreet(String street) {
        if (street != null) this.street = street;
    }

    public void setHouse(String house) {
        if (house != null) this.house = house;
    }

    public void setBuilding(String building) {
        //if (building!=null)
        this.building = building;
    }

    public void setFlat(String flat) {
        if (flat != null) this.flat = flat;
    }

    @Override
    public String toString() {
        if (this.building == null) {
            return "г. " + town +
                    ", " + street +
                    ", дом: " + house +
                    ", кв/офис: " + flat;
        } else {
            return "г." + town +
                    ", " + street +
                    ", дом: " + house +
                    " к." + building +
                    ", кв/офис: " + flat;
        }
    }
}
