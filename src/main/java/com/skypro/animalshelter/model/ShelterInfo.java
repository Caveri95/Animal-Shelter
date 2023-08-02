package com.skypro.animalshelter.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "shelter_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}

