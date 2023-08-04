package com.skypro.animalshelter.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity(name = "user")
public class User {

    @Id
    private long chatId;
    private String fullName;
    private String phoneNumber;
    private ShelterType shelterType;


}
