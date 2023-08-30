package com.skypro.animalshelter.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class Animal {

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
