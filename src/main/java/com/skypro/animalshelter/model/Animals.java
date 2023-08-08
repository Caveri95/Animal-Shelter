package com.skypro.animalshelter.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Animals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String typeAnimal;
    private String breed;
    private Boolean inShelter;


    @Override
    public String toString() {
        return "Порода - " + breed;
    }
}
