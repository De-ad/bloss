# Бизнес-логика программных систем

- Сайт -> [Rottentomatoes](https://www.rottentomatoes.com/)
- Нотация -> [BPMN 2.0](http://www.bpmb.de/images/BPMN2_0_Poster_RU.pdf)

## Вариант №8125

Доработать приложение из лабораторной работы #2, реализовав в нём асинхронное выполнение задач с распределением бизнес-логики между несколькими вычислительными узлами и выполнением периодических операций с использованием планировщика задач.

### Новая функциональность

Стриминговые платформы:

- При обновлении фильма создать таску на распуливание по всем внешним серверам и воркер, который эту очередь разбирает и отправляет на прочие сервисы

### Требования к реализации асинхронной обработки:

1. Перед выполнением работы неободимо согласовать с преподавателем набор прецедентов, в реализации которых целесообразно использование асинхронного распределённого выполнения задач. Если таких прецедентов использования в имеющейся бизнес-процесса нет, нужно согласовать реализацию новых прецедентов, доработав таким образом модель бизнес-процесса из лабораторной работы #1.
2. Асинхронное выполнение задач должно использовать модель доставки `"очередь сообщений"`.
3. В качестве провайдера сервиса асинхронного обмена сообщениями необходимо использовать очередь сообщений на базе `RabbitMQ`.
4. Для отправки сообщений необходимо использовать `JMS API`.
5. Для получения сообщений необходимо использовать `JMS API`.

### Требования к реализации распределённой обработки:

1. Обработка сообщений должна осуществляться на двух независимых друг от друга узлах сервера приложений.
2. Если логика сценария распределённой обработки предполагает транзакционность выполняемых операций, они должны быть включены в состав распределённой транзакции.

### Требования к реализации запуска периодических задач по расписанию:

1. Согласовать с преподавателем прецедент или прецеденты, в рамках которых выглядит целесообразным использовать планировщик задач. Если такие прецеденты отсутствуют -- согласовать с преподавателем новые и добавить их в модель автоматизируемого бизнес-процесса.
2. Реализовать утверждённые прецеденты с использованием планировщика задач `Quartz`.

### Правила выполнения работы:

1. Все изменения, внесённые в реализуемый бизнес-процесс, должны быть учтены в описывающей его модели, REST API и наборе скриптов для тестирования публичных интерфейсов модуля.
2. Доработанное приложение необходимо либо развернуть на сервере helios, либо продемонстрировать его работоспособность на собственной инфраструктуре обучающегося.

### Содержание отчёта:

1. Текст задания.
2. Модель потока управления для автоматизируемого бизнес-процесса со всеми внесёнными изменениями.
3. UML-диаграммы классов и пакетов разработанного приложения.
4. Спецификация REST API для всех публичных интерфейсов разработанного приложения.
5. Исходный код системы или ссылка на репозиторий с исходным кодом.
6. Выводы по работе.

## Отчёт

### Модель потока управления для автоматизируемого бизнес-процесса

Модель

!["BP model"](./img/bloss.drawio-lab2.svg)

### Спецификация пользовательских привилегий и ролей, реализованных в приложении

#### NO_ROLE

- Чтение проверенных данных (GET)
- Регистрация/Логин

#### ROLE_USER

- Всё, что доступно NO_ROLE
- Манипуляция (создание/удаление/изменение) своими отзывами

#### ROLE_ADMIN

- Всё, что доступно ROLE_USER
- Манипуляция чужими данными (в том числе проверка сырых отзывов)

#### ROLE_SUPER_ADMIN

- Всё, что доступно ROLE_ADMIN
- Получение информации о всех ROLE_USER системы
- Выдача роли ROLE_ADMIN

### Спецификация REST API для всех публичных интерфейсов разработанного приложения.

- `/admin`
  - `/{username}` - присвоить пользователю роль администратора
    - ROLE_SUPER_ADMIN
    - POST
      - *username* - логин пользователя, которому присвается ROLE_ADMIN
    - RET: user
  - `/users` - получить список всех пользователей
    - ROLE_SUPER_ADMIN
    - GET
    - RET: user[]
- `/enter`
  - `/register` - зарегестироваться в системе
    - NO_ROLE
    - POST
      - *username* - логин пользователя
      - *password* - пароль пользователя
      - *name* - имя пользователя
      - *surname* - фамилия пользователя
    - RET: {id, username, token}
  - `/login` - залогиниться в системе
    - NO_ROLE
    - POST
      - *username* - логин пользователя
      - *password* - пароль пользователя
    - RET: {id, username, token}
- `/films`
  - ` ` - получить список всех фильмов
    - NO_ROLE
    - GET
    - RET: film[]
  - `/{id}` - получить конкретный фильм
    - NO_ROLE
    - GET
      - *id* - идентификатор фильма
    - RET: film
  - ` ` - создать фильм
    - ROLE_ADMIN
    - POST
      - *name* - имя фильма
      - *description* - описание фильма
    - RET: film
  - ` ` - изменить фильм
    - ROLE_ADMIN
    - PUT
      - *id* - идентификатор фильма
      - *name* - новое имя фильма
      - *description* - новое описание фильма
    - RET: film
  - `/{id}` - удалить выбранный фильм
    - ROLE_ADMIN
    - DELETE
      - *id* - идентификатор удаляемого фильма
    - RET: null (204)
- `/reviews`
  - ` ` - получить список всех принятых отзывов
    - NO_ROLE
    - GET
    - RET: review[]
  - `/{id}` - получить выбранный отзыв, если он принят
    - NO_ROLE
    - GET
      - *id* - идентификатор отзыва
    - RET: review
  - ` ` - создать новый отзыв
    - ROLE_USER
    - POST
      - *filmId* - идентификатор фильма, к которому прилагается отзыв
      - *text* - текст отзыва
      - *score* - рейтинг отзыва (int)
    - RET: review
  - ` ` - изменить существующий отзыв
    - ROLE_USER
    - PUT
      - *id* - идентификатор отзыва 
      - *filmId* - идентификатор фильма, к которому прилагается отзыв
      - *text* - новый текст отзыва
      - *score* - новый рейтинг отзыва (int)
    - RET: review
  - `/{id}` - удалить выбранный отзыв
    - ROLE_USER
    - DELETE
      - *id* - идентификатор отзыва
    - RET: null (204)
  - `/raw` - просмотреть все сырые отзывы (on-review, rejected) 
    - ROLE_ADMIN
    - GET
    - RET: review[]
  - `/{id}/approve` - принять отзыв
    - ROLE_ADMIN
    - POST
      - *id* - идентификатор отзыва
    - RET: review
  - `/{id}/reject` - отклонить отзыв
    - ROLE_ADMIN
    - POST
      - *id* - идентификатор отзыва
    - RET: review

### Выводы по работе.

#### Тучков Максим Русланович

TODO

#### Кондратьева Ксения Михайловна

TODO

## Вопросы к защите лабораторной работы

1. Асинхронное выполнение задач. Преимущества и недостатки, подходы к реализации.
2. Спецификация Java Message Service.
3. Ресурсы и сообщения JMS. Модели взаимодействия "очередь" и "подписка". Распределённая обработка сообщений.
4. Протоколы взаимодействия с очередями сообщений: MQTT, AMQP, STOMP, XMPP. Отправка сообщений с использованием HTTP + WebSockets.
5. Apache ActiveMQ. Архитектура, способы взаимодействия, поддерживаемые протоколы, особенности реализации JMS. Протокол OpenWire и его реализации для различных платформ.
6. RabbitMQ. Архитектура, способы взаимодействия, поддерживаемые протоколы, особенности реализации JMS.
7. Apache Kafka. Особенности обработки сообщений, сходства и отличия с очередями сообщений. Архитектура, особенности построения масштабируемых решений, интеграция с Service Discovery.
8. Периодические задачи, планировщики выполнения задач.
9. Cron. Архитектура, интеграция в ОС, способы конфигурации, синтаксис Cron Expression.
10. Quartz. Архитектура, интеграция с приложением, способы конфигурации.
11. Выполнение периодических задач в Java / Jakarta EE и Spring. Java / Jakarta EE Timer Services и Spring @Scheduled.

## Выполнили

> Тучков Максим Русланович
> 
> Кондратьева Ксения Михайловна
