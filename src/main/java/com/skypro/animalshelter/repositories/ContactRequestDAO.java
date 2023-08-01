package com.skypro.animalshelter.repositories;

import com.skypro.animalshelter.model.ContactRequest;

public interface ContactRequestDAO {
    void sendToDateBase (ContactRequest contactRequest);
    ContactRequest findById (int id);
}
