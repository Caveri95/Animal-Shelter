package com.skypro.animalshelter.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
//@Getter
//@Setter
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDataAdopt() {
        return dataAdopt;
    }

    public void setDataAdopt(LocalDate dataAdopt) {
        this.dataAdopt = dataAdopt;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public ShelterUserType getUserType() {
        return userType;
    }

    public void setUserType(ShelterUserType userType) {
        this.userType = userType;
    }
}
