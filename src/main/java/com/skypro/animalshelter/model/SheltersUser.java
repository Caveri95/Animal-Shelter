package com.skypro.animalshelter.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class SheltersUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private LocalDate dataAdopt;
    private long chatId;
    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    private ShelterUserType userType;

    public SheltersUser(long id, String name, String surname, String phoneNumber, LocalDate dataAdopt, long chatId, Animal animal, ShelterUserType userType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.dataAdopt = dataAdopt;
        this.chatId = chatId;
        this.animal = animal;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "SheltersUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
