package com.skypro.animalshelter.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
@Table(name = "catsheltercontacts")
public class ContactRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Transient
    private Shelter shelter;

    @Column (name = "name")
    private String name;

    @Column (name = "phone")
    private String phone;

    @Column (name = "date")
    private String date;

    @Transient
    private Calendar calendar;

    public ContactRequest(int id, Shelter shelter, String name, String phone) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.id = id;
        this.shelter = shelter;
        this.name = name;
        this.phone = phone;
        this.calendar = Calendar.getInstance();
        this.date = format.format(this.calendar.getTime());
    }



    public ContactRequest() {

    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String shelterString = "";
        String calendarString = "";
        if (shelter != null) {shelterString = ", в приют: " + shelter.getName(); }
        if (calendar != null) {calendarString = ". Дата запроса: " + format.format(calendar.getTime());}
        return "Запрос id №" +
                + id +
        shelterString +
                ". Имя: " + name +
                ", телефон: " + phone +
        calendarString;
        // ДОДЕЛАТЬ! Дата должна парситься в календарь, а тип таблицы - в приют
    }

    public static void getExample () throws InterruptedException {
        Adress adress1 = new Adress("Москва", "Великолукская ул.", "3", "1A", "65");
        Adress adress2 = new Adress("Химки", "Ленинградское шоссе", "21", null, "2");
        CatShelter catShelter = new CatShelter("Кошачий приют №8", "Пн-Пт", adress1);
        DogShelter dogShelter = new DogShelter("Нос и хвост", "Пн, Ср, Чт, Пт, Сб", adress2);
        ContactRequest contactRequest = new ContactRequest(1, catShelter, "Степан", "89154921779");
        System.out.println(contactRequest);
        //Чтоб было разное время создания приютов
        Thread.sleep(1000);
        ContactRequest contactRequest2 = new ContactRequest(2, dogShelter, "Иван", "89174569081");
        System.out.println(contactRequest2);
    }
}
