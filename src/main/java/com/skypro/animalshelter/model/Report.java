package com.skypro.animalshelter.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate localDate;

    private String reportTextUnderPhoto;

    @OneToOne
    @JoinColumn(name = "shelter_users_id")
    private ShelterUser shelterUser;

}
