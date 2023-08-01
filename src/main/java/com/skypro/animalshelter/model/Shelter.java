package com.skypro.animalshelter.model;

public abstract class Shelter {

    private String name;
    private String title;
    private String timetable;
    private Adress adress;
    //img: driving directions;
    private String contactsOfSecurity;
    private String safetyPrecautions;

    private String howToMeetWithPet;

    private String documentsForTaking;

    private String petTransportRecommendations;

    private String homePreparations;

    private String extraPreparationsForDisabledPets;

    private String possibleCausesOfRefusal;

    public Shelter(String name, String timetable, Adress adress) {
        if (name == null || timetable == null || adress == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.timetable = timetable;
        this.adress = adress;
        setTitle("Тут должна быть основная информация о приюте");
        setContactsOfSecurity("Тут должны быть контакты охраны для оформления пропуска");
        setDocumentsForTaking("Тут должен быть список документов для оформления питомца из приюта");
        setSafetyPrecautions("Тут должны быть правила техники безопасности на территории приюта");
        setHowToMeetWithPet("Тут должны быть рекомендации как правильно знакомиться с питомцем");
        setHomePreparations("Тут должны быть рекомендации, как подготовить жилье для содержания питомца");
        setExtraPreparationsForDisabledPets("Тут должны быть дополнительные рекомендации по подготовке жилья для питомца с ограниченными возможностями");
        setPetTransportRecommendations("Тут должны быть рекомендации по правильной транспортировке питомца");
        setPossibleCausesOfRefusal("Тут должен быть список возможных причин отказа во взятии питомца из приюта");
    }

    public Shelter(String name, String title, String timetable, Adress adress, String contactsOfSecurity, String safetyPrecautions, String howToMeetWithPet, String documentsForTaking, String petTransportRecommendations, String homePreparations, String extraPreparationsForDisabledPets, String possibleCausesOfRefusal) {
        if (name == null || timetable == null || adress == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.title = title;
        this.timetable = timetable;
        this.adress = adress;
        this.contactsOfSecurity = contactsOfSecurity;
        this.safetyPrecautions = safetyPrecautions;
        this.howToMeetWithPet = howToMeetWithPet;
        this.documentsForTaking = documentsForTaking;
        this.petTransportRecommendations = petTransportRecommendations;
        this.homePreparations = homePreparations;
        this.extraPreparationsForDisabledPets = extraPreparationsForDisabledPets;
        this.possibleCausesOfRefusal = possibleCausesOfRefusal;
    }

    @Override
    public String toString() {
        return "приют: "+name +
                "\nрасписание: " + timetable +
                "\nадрес: " + adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public String getContactsOfSecurity() {
        return contactsOfSecurity;
    }

    public void setContactsOfSecurity(String contactsOfSecurity) {
        this.contactsOfSecurity = contactsOfSecurity;
    }

    public String getSafetyPrecautions() {
        return safetyPrecautions;
    }

    public void setSafetyPrecautions(String safetyPrecautions) {
        this.safetyPrecautions = safetyPrecautions;
    }

    public String getHowToMeetWithPet() {
        return howToMeetWithPet;
    }

    public void setHowToMeetWithPet(String howToMeetWithPet) {
        this.howToMeetWithPet = howToMeetWithPet;
    }

    public String getDocumentsForTaking() {
        return documentsForTaking;
    }

    public void setDocumentsForTaking(String documentsForTaking) {
        this.documentsForTaking = documentsForTaking;
    }

    public String getPetTransportRecommendations() {
        return petTransportRecommendations;
    }

    public void setPetTransportRecommendations(String petTransportRecommendations) {
        this.petTransportRecommendations = petTransportRecommendations;
    }

    public String getHomePreparations() {
        return homePreparations;
    }

    public void setHomePreparations(String homePreparations) {
        this.homePreparations = homePreparations;
    }

    public String getExtraPreparationsForDisabledPets() {
        return extraPreparationsForDisabledPets;
    }

    public void setExtraPreparationsForDisabledPets(String extraPreparationsForDisabledPets) {
        this.extraPreparationsForDisabledPets = extraPreparationsForDisabledPets;
    }

    public String getPossibleCausesOfRefusal() {
        return possibleCausesOfRefusal;
    }

    public void setPossibleCausesOfRefusal(String possibleCausesOfRefusal) {
        this.possibleCausesOfRefusal = possibleCausesOfRefusal;
    }

    public static void getExample () {
        Adress adress1 = new Adress("Москва", "Великолукская ул.", "3", "1A", "65");
        Adress adress2 = new Adress("Химки", "Ленинградское шоссе", "21", null, "2");
        CatShelter catShelter = new CatShelter("Кошачий приют №8", "Пн-Пт", adress1);
        DogShelter dogShelter = new DogShelter("Нос и хвост", "Пн, Ср, Чт, Пт, Сб", adress2);
        System.out.println(dogShelter);
        System.out.println(catShelter);
    }
}
