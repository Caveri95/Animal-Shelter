package com.skypro.animalshelter.model;

public class CatShelter extends Shelter {

    private String foodForCat;

    public CatShelter(String name, String title, String timetable, Adress adress, String contactsOfSecurity, String safetyPrecautions, String howToMeetWithPet, String documentsForTaking, String petTransportRecommendations, String homePreparations, String extraPreparationsForDisabledPets, String possibleCausesOfRefusal, String foodForCat) {
        super(name, title, timetable, adress, contactsOfSecurity, safetyPrecautions, howToMeetWithPet, documentsForTaking, petTransportRecommendations, homePreparations, extraPreparationsForDisabledPets, possibleCausesOfRefusal);
        this.foodForCat = foodForCat;
    }

    public CatShelter(String name, String timetable, Adress adress) {
        super(name, timetable, adress);
        setFoodForCat("Тут должна быть информация о правильном питании для кошек");
    }

    public String getFoodForCat() {
        return foodForCat;
    }

    public void setFoodForCat(String foodForCat) {
        this.foodForCat = foodForCat;
    }
}
