## Сайт аренды жилья

В свободное время занимаюсь данным проектом в качестве хобби. Репозиторий содержит только часть кода в демонстрационных целях. 

Проект - это сайт для аренды жилья на курортах Казахстана. 

Существует сырая версия сайта, которая была написана мной в 2015 году на play framework 1.4 ([vdomikah.kz](http://vdomikah.kz)). На данный момент занимаюсь его переписыванием на spring-boot (новая версия сайте еще не опубликована в интернете).


---


### Используемые технологии:
* spring-boot
* spring-data
* spring-security
* kafka
* SOAP
* thymeleaf
* hibernate
* postgresql
* junit
* vue.js
* jquery
* gulp
* sass
* bootstrap

---


### Технические детали
#### Микросервисы
На текущий момент приложение состоит из двух сервисов, которые взаимодействуют через kafka 
* сервис обработки изображений 
* основной сервис с бизнес логикой и html).
#### SOLID
Соблюдаются правила SOLID. Код делится на "слои" - бизнес-логика, детали реализации. 

* [Бизнес-логика](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/core): сущности, варианты использования.

* Детали реализации: [взаимодействие с БД](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/dataproviders/database), [с вебом](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/entrypoints) и со сторонними сервисами, [фреймворк](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/configuration). 

Бизнес-логика не зависит от деталей реализации. Например, можно заменить PostgreSQL на MongoDB без необходимости редактирования кода бизнес-логики. 


![Image of arch](https://github.com/happy-robot/resort/blob/master/docs/images/clean-architecture-diagram-1.png)
![Image of arch2](https://github.com/happy-robot/resort/blob/master/docs/images/clean-architecture-diagram-2.png)
#### Акторы
Есть три актора - арендадатель, арендатор, модератор. Пишем для каждого актора отдельный код.
#### Покрытие тестами
Юнит тесты покрывают 72% бизнес логики арендадателя (jacoco) и 9% всего кода.
#### SOAP
При регистрации пользователя ему отправляется SMS с кодом подтверждения. Взаимодействие с сервисом отправки SMS происходит через SOAP.
#### Frontend
Для frontend-а ипользуется gulp (объединение и минификация css, js файлов, генерация спрайтов), sass, vue.js, jquery.

---


### Достижения при разработке проекта
#### Повысил свою техническую экспертизу и продолжаю это делать
При работе с сайтом ставлю себе задачи, с которыми не сталкиваюсь на основном месте работы.
#### Знаю, насколько важно понимать ценность продукта
Программный продукт должен представлять ценность для пользователей. Необходимо определять насколько проектируемый функционал будет нужен пользователю. Необходимо знать проблемы пользователей и находить решения данных проблем.
#### Повысил экспертизу в UX/UI
Для создания дизайна страниц сайта использую Figma. На текущий момент разработано 70% страниц. По запросу могу продемонстировать наработки.
#### Умею продвигать сайт в поисковых системах
Были настроены google analytics, google adwords. За первые 6 месяцев по некоторым запросам удалось продвинуть сайт на первую страницу в google и yandex.
#### Знай свою целевую аудиторию
Удалось непосредственно поработать с конечными пользователями - как с арендодателями так и с арендаторами, увидеть проблему глазами пользователей.

