# Бизнес-логика программных систем

- Сайт -> [Rottentomatoes](https://www.rottentomatoes.com/)
- Нотация -> [BPMN 2.0](http://www.bpmb.de/images/BPMN2_0_Poster_RU.pdf)

## Вариант №99825

Переработать программу, созданную в результате выполнения лабораторной работы #3, следующим образом:

1. Для управления бизнес-процессом использовать BPM-движок Camunda.
2. Заменить всю "статическую" бизнес-логику на "динамическую" на базе BPMS. Весь бизнес-процесс, реализованный в ходе выполнения предыдущих лабораторных работ (включая разграничение доступа по ролям, управление транзакциями, асинхронную обработку и периодические задачи), должен быть сохранён!
3. BPM-движок должен быть `запущен в режиме standalone-сервиса`.
4. Для описания бизнес-процесса необходимо использовать `онлайн-сервис BPMN.io`.
5. Пользовательский интерфейс приложения должен быть сгенерирован с помощью генератора форм Camunda.
6. Итоговая сборка должно быть развёрнута на сервере helios под управление сервера приложений `WildFly`.

### Правила выполнения работы

1. Описание бизнес-процесса необходимо реализовать на языке BPMN 2.0.
2. Необходимо интегрировать в состав процесса, управляемого BPMS, всё, что в принципе возможно в него интегрировать. Если какой-то из компонентов архитектуры приложения (например, асинхронный обмен сообщениями с помощью JMS) не поддерживается, необходимо использовать для интеграции с этой подсистемой соответствующие API и адаптеры.
3. Распределённую обработку задач и распределённые транзакции на BPM-движок переносить не требуется.

### Содержание отчёта

1. Текст задания.
2. Модель потока управления для автоматизируемого бизнес-процесса со всеми внесёнными изменениями.
3. Блок-схема архитектуры приложения с указанием "точек интеграции" BPM-фреймвока с остальными подсистемами.
4. Исходный код системы, код описания бизнес-процесса или ссылка на репозиторий.
5. Выводы по работе.

## Отчёт

### Модель потока управления для автоматизируемого бизнес-процесса со всеми внесёнными изменениями

Изначальная модель бизнес процесса
![Initial business process model](./img/bloss.png)

### Блок-схема архитектуры приложения с указанием "точек интеграции" BPM-фреймвока с остальными подсистемами

TODO

### Выводы по работе

#### Тучков Максим Русланович

TODO

#### Кондратьева Ксения Михайловна

TODO

## Вопросы к защите лабораторной работы

1. BPM-фреймворки. Особенности реализации бизнес-логики, преимущества и недостатки по сравнению с реализацией логики "вручную".
2. Платформа Camunda. Архитектура, состав, поддерживаемые языки, особенности разработки программ.
3. Механизмы редактирования бизнес-процессов в Camunda. Camunda Modeler. Использование "внешних" редакторов.
4. Роли и права доступа в Camunda.
5. Использование Camunda в качестве подсистемы "внутри" приложения на базе Java / Jakarta EE и Spring.
6. Интеграция Camunda с "внешними" сервисами (в т.ч. на базе Java / Jakarta EE и Spring). Основные API и адаптеры.
7. Транзакции в Camunda. Поддержка JTA.
8. Реализация GUI в Camunda. Управление формами.

## Выполнили

> Тучков Максим Русланович
>
> Кондратьева Ксения Михайловна
