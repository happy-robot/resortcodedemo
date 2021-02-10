#### My linkedin https://www.linkedin.com/in/amir-kapsatarov-614571b4/
---

- [На русском](#in-russian)
- [In English](#apartment-rental-site)

## Apartment rental site

I am working on the side project on my own time. The repository contains only part of the code for demonstrating.

The project is a site for housing rental ads on Kazakhstan resorts.


There is alse rough version of the site which was created in 2015 on play framework 1.4 ([vdomikah.kz](http://vdomikah.kz)). Nowadays I am rewriting it on the spring-boot (new version of the site is not published on the Internet).


---


### Technologies:
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

### Technical details
#### Microservices
The app consists of two services communicating by the kafka.
* image processing service
* business logic and html service
#### SOLID
SOLID rules are respected. The code consists of two layers - business logic and implementation details.
* [business logic](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/core): domain, use cases.
* implementation details: [communication with DB](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/dataproviders/database), [with web](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/entrypoints) and with third party services, the [framework](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/configuration).

Business logic does not depend on implementation details. For example, it is possible to replace PostgreSQL with MongoDB without changing business logic code.

Actors: there are three actors - landlord, tenant, modetator. Do not mix different actors code.

![Image of arch](https://github.com/happy-robot/resortcodedemo/blob/master/docs/images/clean-architecture-diagram-1.png)
![Image of arch2](https://github.com/happy-robot/resortcodedemo/blob/master/docs/images/clean-architecture-diagram-2.png)

#### Code coverage
Unit tests cover 72% landlord business logic (jacoco) and 9% of the total code.

#### SOAP
The user is send SMS with confirmation code while sign up. The app communicates with third party SMS service via SOAP.

#### Frontend
I am using gulp (css and js files concatenating and minificating, sprite generating), sass, vue.js, jquery.

## Accomplishments during working on the project
#### I have enhanced my hard skills and continue to do so.
On the project I give myself tasks which I never had to deal with on my main place of work.
#### It is important to understand the value of a product.
A software product have to be of value for users. It is necessary to define users problems and to find solutions to them.
#### I have enhanced my UX/UI skills
I use Figma for pages designing. Upon request I can demonstrate designed pages.
#### I am able to make SEO optimizations and promote websites in google
Google analytics and google adwords have been configured for the website. Website appeared in the first google's page during 6 month.
#### It is important to know your target audience
I was able to communicate with my users - landlords and tenants and to see the problems from their perspective.


## In Russian
---
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
* основной сервис с бизнес логикой и html.
#### SOLID
Соблюдаются правила SOLID. Код делится на "слои" - бизнес-логика, детали реализации. 

* [Бизнес-логика](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/core): сущности, варианты использования.

* Детали реализации: [взаимодействие с БД](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/dataproviders/database), [с вебом](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/entrypoints) и со сторонними сервисами, [фреймворк](https://github.com/happy-robot/resortcodedemo/tree/master/src/main/java/kz/kaps/resort/configuration). 

Бизнес-логика не зависит от деталей реализации. Например, можно заменить PostgreSQL на MongoDB без необходимости редактирования кода бизнес-логики.

Акторы: Есть три актора - арендадатель, арендатор, модератор. Не смешиваем код, используемый разными акторами.


![Image of arch](https://github.com/happy-robot/resortcodedemo/blob/master/docs/images/clean-architecture-diagram-1.png)
![Image of arch2](https://github.com/happy-robot/resortcodedemo/blob/master/docs/images/clean-architecture-diagram-2.png)

#### Покрытие тестами
Юнит тесты покрывают 72% бизнес логики арендадателя (jacoco) и 9% всего кода.
#### SOAP
При регистрации пользователя ему отправляется SMS с кодом подтверждения. Взаимодействие с сервисом отправки SMS происходит через SOAP.
#### Frontend
Для frontend-а ипользуется gulp (объединение и минификация css, js файлов, генерация спрайтов), sass, vue.js, jquery.

---


## Достижения при разработке проекта
#### Повысил свою техническую экспертизу и продолжаю это делать
При работе с сайтом ставлю себе задачи, с которыми не сталкиваюсь на основном месте работы.
#### Знаю насколько важно понимать ценность продукта
Программный продукт должен представлять ценность для пользователей. Необходимо определять насколько проектируемый функционал будет нужен пользователю. Необходимо знать проблемы пользователей и находить решения данных проблем.
#### Повысил экспертизу в UX/UI
Для создания дизайна страниц сайта использую Figma. На текущий момент разработано 70% страниц. По запросу могу продемонстировать проделанную работу.
#### SEO оптимизация и продвижение сайта
Были настроены google analytics, google adwords. За первые 6 месяцев по некоторым запросам удалось продвинуть сайт на первую страницу в google и yandex.
#### Знай свою целевую аудиторию
Удалось непосредственно поработать с конечными пользователями - как с арендодателями так и с арендаторами и увидеть проблему глазами пользователей.

Мой линкедин https://www.linkedin.com/in/amir-kapsatarov-614571b4/

