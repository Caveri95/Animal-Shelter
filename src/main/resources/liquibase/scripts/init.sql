-- liquibase formatted sql

-- changeset Caveri:1

create table "shelter_info"
(
    id                               bigint generated by default as identity PRIMARY KEY,
    about_shelter                    text not null,
    address_and_schedule             text not null,
    safety_contact_for_car_pass      text not null,
    safety_on_territory              text not null,
    first_meet                       text not null,
    documents                        text not null,
    transportation_advice            text not null,
    house_rules_for_small_animal     text not null,
    house_rules_for_adult_animal     text not null,
    rules_for_animal_with_disability text not null,
    cynologist_advice                text,
    cynologists                      text,
    refuse_reasons                   text
);

-- changeset Caveri:2

create table shelters_user
(
    id         bigint not null generated by default as identity primary key,
    name        varchar(255),
    surname       varchar(255),
    phone_number       varchar(255)
);


-- changeset Caveri:3

insert into public.shelter_info (about_shelter, address_and_schedule, safety_contact_for_car_pass, safety_on_territory,
                                 first_meet, documents, transportation_advice, house_rules_for_small_animal,
                                 house_rules_for_adult_animal, rules_for_animal_with_disability, cynologist_advice,
                                 cynologists, refuse_reasons)
values ('Здесь инфа о приюте для котов','СПБ Невский 38 89210000000', '890011111111',
        'Правила нахождения на территории',
        'При первой встрече необходимо',
        'Возьмите с собой следующие документы',
        'Учтите следующую инфрмацию при перевозке',
        'Правила обращения с животными кисами',
        'Правила обращения со взрослыми животными',
        'https://petscage.ru/blog/kak-oblegchit-zhizn-zhivotnomu-invalidu/',
        null, null, null);

-- changeset Caveri:4

insert into public.shelter_info (about_shelter, address_and_schedule, safety_contact_for_car_pass, safety_on_territory,
                                 first_meet, documents, transportation_advice, house_rules_for_small_animal,
                                 house_rules_for_adult_animal, rules_for_animal_with_disability, cynologist_advice,
                                 cynologists, refuse_reasons)
values ('Здесь инфа о приюте для собак',
'СПБ Лермонтов 25 телефон 8921654321',
'телефон охраны 222-22-22',
        'Правила нахождения на территории',
        'При первой встрече необходимо',
        'Возьмите с собой следующие документы',
        'Учтите следующую инфрмацию при перевозке',
        'Правила обращения с щенятами',
        'Правила обращения со взрослыми животными',
        'Если у животного есть отклонения',
        'Советы кинологов',
        'Вот тут список проверенных кинологов', 'Вы не можете забрать собаку, если у нее выражены следующие симптомы:...');