package com.skypro.animalshelter.repositories;

import com.example.animashelter.model.ContactRequest;

public interface ContactRequestDAO {
    void sendToDateBase (ContactRequest contactRequest);
    ContactRequest findById (int id);
}
