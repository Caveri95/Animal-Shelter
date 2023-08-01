package com.skypro.animalshelter;

import com.skypro.animalshelter.repositories.ContactRequestDAOImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnimalShelterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalShelterApplication.class, args);
        ContactRequestDAOImpl contactRequestDAOimpl = new ContactRequestDAOImpl();
        System.out.println(contactRequestDAOimpl.findById(1));
    }


}
