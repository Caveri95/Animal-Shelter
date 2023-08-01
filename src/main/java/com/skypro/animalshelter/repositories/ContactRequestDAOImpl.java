package com.skypro.animalshelter.repositories;

import com.example.animashelter.model.ContactRequest;
import com.example.animashelter.services.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ContactRequestDAOImpl implements ContactRequestDAO{
    @Override
    public void sendToDateBase(ContactRequest contactRequest) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(contactRequest);
            transaction.commit();
        }
    }

    @Override
    public ContactRequest findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(ContactRequest.class, id);
    }
}
