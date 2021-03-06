# TopJava Release Notes

### Topjava 9
- добавил
  - выбор профиля базы через `ActiveProfilesResolver`/`AllActiveProfileResolver` на основе драйвера базы в classpath
  - видео <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFVmdpNDJSNXRTWUE">Cascade. Auto generate DDL.</a>
  - проверку на правильность id в Ajax/Rest контроллерах (<a href="http://stackoverflow.com/a/32728226/548473">treat IDs in REST body</a>)
  - тесты на валидацию входных значений контроллеров и <a href="http://hibernate.org/validator/documentation/getting-started/#unified-expression-language-el">зависимость на имплементацию</a>
  - <a href="http://getbootstrap.com/components/#glyphicons">Bootstrap Glyphicons</a>
- рефакторинг
  - переименовал `TimeUtil` в `DateTimeUtil`
  - переименовал `ExceptionUtil` в `ValidationUtil`
  - заменил валидацию <a href="http://stackoverflow.com/questions/17137307">`@NotEmpty` на `@NotBlank`</a>
  - заменил `CascadeType.REMOVE` на <a href="http://stackoverflow.com/questions/21149660">`@OnDelete`</a>
  - изменил `JdbcUserRepositoryImpl.getAll()`
  - обновил jQuery до 3.x, исключил из зависимостей webjars ненужные jQuery
  - cделал <a href="http://stackoverflow.com/questions/436411/where-should-i-put-script-tags-in-html-markup/24070373#24070373">загрузку скриптов асинхронной</a>
  - фильтр еды сделал в [Bootstrap Panels](http://getbootstrap.com/components/#panels)
  - вместо `Persistable` ввел интерфейс `HasId` и наследую от него как Entity, так и TO

### Topjava 8
- добавил:
  - [защиту от XSS (Cross-Site Scripting)](http://stackoverflow.com/a/40644276/548473)
  - интеграцию с <a href="https://dependencyci.com/">Dependency Ci</a> и <a href="https://travis-ci.org/">Travis Ci</a> 
  - локализацию календаря
  - сводку по результатам тестов
  - примеры запросов curl в `config/curl.md`
  - <a href="https://datatables.net/examples/styling/bootstrap.html">DataTables/Bootstrap 3 integration</a>
  - тесты на профиль деплоя Heroku (общее количество JUnit тестов стало 102)
- удалил зависимость `jul-to-slf4j`
- рефакторинг
  - переименовал все классы `UserMeal**` в `Meal**`, JSP
  - переименовал `LoggedUser` в `AuthorizedUser`
  - починил работа с PK Hibernate в случае ленивой загрузки (баг <a href="https://hibernate.atlassian.net/browse/HHH-3718">HHH-3718</a>)
  - поменял в `BaseEntity` `equals/hashCode/implements Persistable`
  - в `InMemoryMealRepositoryImpl` выделил метод `getAllStream` 
  - перенес проверки пердусловий `Assert` из `InMemory` репозиториев в сервисы
  - переименовал классы _Proxy*_ на более адекватные _Crud*_
  - поменял реализацию `JpaMealRepositoryImpl.get`, добавил в JPA модель `@BatchSize`
  - вместо `@RequestMapping` ввел Spring 4.3 аннотации `@Get/Post/...Mapping`
  - поменял авторизацию в тестах не-REST контроллеров
  - перенес вызовы `UserUtil.prepareToSave` из `AbstractUserController` в `UserServiceImpl`
  - зарефакторил обработку ошибок (`ExceptionInfoHandler`)

### Topjava 7
- добавил:
  - [JPA 2.1 EntityGraph](https://docs.oracle.com/javaee/7/tutorial/persistence-entitygraphs002.htm)
  - [Jackson @JsonView](https://habrahabr.ru/post/307392/)
  - валидацию объектов REST
  - [i18n в JavaScript](http://stackoverflow.com/a/6242840/548473)
  - проверку предусловий и видео <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFU005ZzBNZmZnTVU">Методы улучшения качества кода</a>
  - интеграцию с <a href="https://www.codacy.com/">проверкой кода в Codacy</a>
  - [сравнение вермени исполнения запросов при различных meals индексах](https://drive.google.com/open?id=0B9Ye2auQ_NsFX3RLcnJCWmQ2Y0U)
- tomcat7-maven-plugin плагин перключили на Tomcat 8 (cargo-maven2-plugin)
- рефакторинг 
  - обработка ошибок сделал с array
  - матчеров тестирования (сделал автоматические обертки и сравнение на основе передаваемого компаратора)
  - вынес форматирование даты в `functions.tld`

### Topjava 3-6
- добавил
  - [выпускной проект](https://drive.google.com/open?id=0B9Ye2auQ_NsFcG83dEVDVTVMamc)
  - в таблицу meals составной индекс 
  - константы `Profiles.ACTIVE_DB`, `Profiles.DB_IMPLEMENTATION`
  - проверки и тесты на `NotFound` для `UserMealService.getWithUser` и  `UserService.getWithMeals`
  - в MockMvc фильтр CharacterEncodingFilter
  - защиту от межсайтовой подделки запроса, видео <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNDlPZGdUNThzNUU">Межсайтовая подделка запроса (CSRF)</a>
  - ограничение на диапазон дат для фильтра еды
- рефакторинг
  - UserMealsUtil, ProfileRestController, компараторов в репозитоии 
  - `LoggedUser` отнаследовал от `org.springframework.security.core.userdetails.User`
  - переименовал `DbTest` в `AbstractServiceTest` и перенес сюда `@ActiveProfiles`
  - сделал выполнение скриптов в тестах через аннотацию `@Sql`
  - вместо использования id и селектора сделал обработчик `onclick`
  - изменил формат ввода даты в форме без 'T'
- убрал
  - `LoggerWrapper`
  - <a href="http://dandelion.github.io">Dandelion обертку к datatables</a>
- обновил 
  - Hibernate до 5.x и Hibernate Validator, добавились новые зависимости и `jackson-datatype-hibernate5`
  - datatables API (1.10)
  - Postgres драйвер. Новый драйвер поддерживает Java 8 Time API, разделил реализацию JdbcMealRepositoryImpl на Java8 (Postgresql) и Timestamp (HSQL)
