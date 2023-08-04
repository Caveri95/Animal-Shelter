# Telegram бот для приюта животных. Командный учебный проект

## Animal-Shelter
 В данный момент проект в разработке, который был заказан заказчиком из города Астана. 
 Целью проекта является снятие нагрузки с сотрудников приюта, автоматизация процесса консультации новых клиентов приюта и 
 помощь клиентов, которые забрали животное к себе. 
## Описание телеграм - бота
Телеграм-бот для приюта животных, который отвечает на вопросы пользователей о том, что нужно знать и уметь, чтобы забрать животное из приюта. Телеграмм-бот в течение испытательного срока принимает от новых хозяев ежедневные отчеты о том, как питомцы привыкают к новой обстановке.

Реализована следующая функциональность:

- выбор приюта: для кошек или для собак
- отправка ботом ответов на вопросы пользователей о приюте, правилах усыновления питомцев и т.д. (в зависимости от выбранного приюта)
- кнопки для выбора команд/разделов

Что будет добавлено:

- возможность присылать отчеты о питомце и их проверка волонтером
- сохранение фото и данных в БД
- ежедневное автоматическое напоминание об отправке отчёта


## Используемые технологии

- Spring-boot;
- Telegram API (Pengrad 6.6.1);
- Maven;
- Lombok;
- PostgresSQL;
- Liquibase.
