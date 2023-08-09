package com.skypro.animalshelter.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String photo;

    private String reportTextUnderPhoto;

    @OneToOne
    @JoinColumn(name = "shelter_users_id")
    private ShelterUsers shelterUsers;

}
