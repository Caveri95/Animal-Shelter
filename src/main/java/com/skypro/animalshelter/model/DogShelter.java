package com.skypro.animalshelter.model;

public class DogShelter extends Shelter {
    private String DogHandlerRecommendations;
    private String ListOfCynologists;

    public DogShelter(String name, String title, String timetable, Adress adress, String contactsOfSecurity, String safetyPrecautions, String howToMeetWithPet, String documentsForTaking, String petTransportRecommendations, String homePreparations, String extraPreparationsForDisabledPets, String possibleCausesOfRefusal, String dogHandlerRecommendations, String listOfCynologists) {
        super(name, title, timetable, adress, contactsOfSecurity, safetyPrecautions, howToMeetWithPet, documentsForTaking, petTransportRecommendations, homePreparations, extraPreparationsForDisabledPets, possibleCausesOfRefusal);
        DogHandlerRecommendations = dogHandlerRecommendations;
        ListOfCynologists = listOfCynologists;
    }

    public DogShelter(String name, String timetable, Adress adress) {
        super(name, timetable, adress);
        setDogHandlerRecommendations("Тут должны быть рекомендации профессионального кинолога");
        setListOfCynologists("Тут должен быть список рекомендуемых кинологов");
    }

    public String getDogHandlerRecommendations() {
        return DogHandlerRecommendations;
    }

    public void setDogHandlerRecommendations(String dogHandlerRecommendations) {
        DogHandlerRecommendations = dogHandlerRecommendations;
    }

    public String getListOfCynologists() {
        return ListOfCynologists;
    }

    public void setListOfCynologists(String listOfCynologists) {
        ListOfCynologists = listOfCynologists;
    }
}
