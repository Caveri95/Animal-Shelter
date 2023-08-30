package com.skypro.animalshelter.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity(name = "shelter_info")
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode
public class ShelterInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "about_shelter")
    private String aboutShelter;

    @Column(name = "address_and_schedule")
    private String addressAndSchedule;

    @Column(name = "safety_contact_for_car_pass")
    private String safetyContactForCarPass;

    @Column(name = "safety_on_territory")
    private String safetyOnTerritory;

    @Column(name = "first_meet")
    private String firstMeetRecommendation;

    @Column(name = "documents")
    private String documents;

    @Column(name = "transportation_advice")
    private String transportationAdvice;

    @Column(name = "house_rules_for_small_animal")
    private String houseRulesForSmallAnimal;

    @Column(name = "house_rules_for_adult_animal")
    private String houseRulesForAdultAnimal;

    @Column(name = "rules_for_animal_with_disability")
    private String rulesForAnimalWithDisability;

    @Column(name = "cynologist_advice")
    private String cynologistAdvice;

    @Column(name = "cynologists")
    private String cynologists;

    @Column(name = "refuse_reasons")
    private String refuseReasons;

    public void setId(long id) {
        this.id = id;
    }

    public void setAboutShelter(String aboutShelter) {
        this.aboutShelter = aboutShelter;
    }

    public void setAddressAndSchedule(String addressAndSchedule) {
        this.addressAndSchedule = addressAndSchedule;
    }

    public void setSafetyContactForCarPass(String safetyContactForCarPass) {
        this.safetyContactForCarPass = safetyContactForCarPass;
    }

    public void setSafetyOnTerritory(String safetyOnTerritory) {
        this.safetyOnTerritory = safetyOnTerritory;
    }

    public void setFirstMeetRecommendation(String firstMeetRecommendation) {
        this.firstMeetRecommendation = firstMeetRecommendation;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public void setTransportationAdvice(String transportationAdvice) {
        this.transportationAdvice = transportationAdvice;
    }

    public void setHouseRulesForSmallAnimal(String houseRulesForSmallAnimal) {
        this.houseRulesForSmallAnimal = houseRulesForSmallAnimal;
    }

    public void setHouseRulesForAdultAnimal(String houseRulesForAdultAnimal) {
        this.houseRulesForAdultAnimal = houseRulesForAdultAnimal;
    }

    public void setRulesForAnimalWithDisability(String rulesForAnimalWithDisability) {
        this.rulesForAnimalWithDisability = rulesForAnimalWithDisability;
    }

    public void setCynologistAdvice(String cynologistAdvice) {
        this.cynologistAdvice = cynologistAdvice;
    }

    public void setCynologists(String cynologists) {
        this.cynologists = cynologists;
    }

    public void setRefuseReasons(String refuseReasons) {
        this.refuseReasons = refuseReasons;
    }
}

