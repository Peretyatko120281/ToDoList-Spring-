# ToDoList-Spring

Веб-приложение для управления списком задач (To-Do List) на **Spring Framework 7** с JPA/Hibernate, декларативными транзакциями и пулом соединений Apache Tomcat DBCP.

## 📋 О проекте

Классическое CRUD-приложение, демонстрирующее современный подход к построению слоя доступа к данным в Spring 7 (Jakarta EE):

- **DAO** построен на `@PersistenceContext` + `EntityManager`, без ручного управления транзакциями.
- **Сервисный слой** помечен `@Transactional`, границы транзакций задаются декларативно.
- **Пул соединений** — `BasicDataSource` из `tomcat-dbcp`, полностью совместимый с пространством имён `jakarta.*`.
- **Конфигурация** — Java-based (`@Configuration`), без XML.

### Функциональность

- Просмотр списка задач с фильтрацией по статусу (`ACTIVE` / `DONE`).
- Добавление новой задачи.
- Отметка задачи как выполненной.
- Удаление задачи.
- Счётчики активных и выполненных задач.

## 🛠 Технологический стек

| Компонент | Версия / Реализация |
| :--- | :--- |
| Язык | Java 21 |
| Фреймворк | Spring Framework 7.0.10 |
| Стандарт | Jakarta EE 11 (Servlet 6.1, Persistence 3.2) |
| ORM | Hibernate ORM 7.4.17.Final |
| Пул соединений | Apache Tomcat DBCP (`tomcat-dbcp` 10.1.57) |
| БД | PostgreSQL (драйвер 42.7.14) |
| Шаблоны | JSP + JSTL 3.0 |
| Сборка | Maven (WAR) |

## 📂 Структура проекта

```text
src/main/java/ru/alex/
├── config/
│   ├── ApplicationInitializer.java   # Замена web.xml: регистрация DispatcherServlet
│   ├── DataBaseConfig.java           # DataSource, EntityManagerFactory, TransactionManager
│   └── WebConfig.java                # Spring MVC, ViewResolver, ресурсы
├── controller/
│   └── CommonController.java         # Обработка HTTP-запросов
├── dao/
│   └── RecordDao.java                # Доступ к данным через EntityManager
├── entity/
│   ├── Record.java                   # JPA-сущность задачи
│   ├── RecordStatus.java             # enum ACTIVE / DONE
│   └── dto/
│       └── RecordsContainerDTO.java  # DTO для списка + счётчиков
└── service/
    └── RecordService.java            # Бизнес-логика + @Transactional

src/main/resources/
└── application.properties            # Параметры БД и пула
```

## 🚀 Запуск

### Требования

1. **JDK 21**.
2. **Maven** 3.9+.
3. **PostgreSQL** с любой базой (параметры подключения вы указываете сами — см. ниже).
4. Контейнер сервлетов **Tomcat 10.1+** (или совместимый, поддерживающий Jakarta EE).

### ⚠️ Настройка подключения к БД

В репозитории файл `src/main/resources/application.properties` содержит **заглушки**, а не реальные данные. Перед запуском **обязательно укажите свои параметры подключения**:

```properties
db.driver=org.postgresql.Driver
db.url=jdbc:postgresql://localhost:5432/your_database
db.username=your_username
db.password=your_password
```

Замените:

| Параметр | Что указать |
| :--- | :--- |
| `your_database` | Имя вашей базы данных PostgreSQL |
| `your_username` | Ваш логин PostgreSQL |
| `your_password` | Ваш пароль PostgreSQL |

Остальные параметры (пул соединений, диалект Hibernate) можно оставить как есть:

```properties
db.connection-pool.initial-size=2
db.connection-pool.min-idle=2
db.connection-pool.max-idle=8
db.connection-pool.max-total=8

hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.show_sql=true
hibernate.hbm2ddl.auto=update
```

> ⚠️ `hibernate.hbm2ddl.auto=update` удобно для разработки, но **не используйте его в продакшене** — схемой БД стоит управлять через миграции (Flyway / Liquibase).

> 💡 **Совет:** чтобы не коммитить свои реальные данные, можно создать рядом файл `application-local.properties`, добавить его в `.gitignore` и подключить вторым `@PropertySource` в `DataBaseConfig`. Тогда `application.properties` останется с заглушками, а локальные значения будут подхватываться только на вашей машине.

### Сборка и развёртывание

```bash
mvn clean package
```

Полученный `target/to-do-list-webapp_3.war` разверните в Tomcat 10.1+ (например, скопируйте в `$CATALINA_HOME/webapps/`).

Приложение будет доступно по адресу:

```
http://localhost:8080/to-do-list-webapp_3/
```

## 🔧 Особенности реализации

### Управление транзакциями

`@Transactional` вынесен на уровень `RecordService`. DAO не содержит аннотаций транзакций — это позволяет переиспользовать его в разных сервисах и не привязывать к конкретному сценарию. Метод чтения помечен `@Transactional(readOnly = true)`.

### Пул соединений

Используется `org.apache.tomcat.dbcp.dbcp2.BasicDataSource` из `tomcat-dbcp` — это переупакованный Apache Commons DBCP 2, совместимый с `jakarta.transaction`. Параметры пула задаются в `application.properties` и внедряются через `@Value`.

### Конфигурация без XML

`ApplicationInitializer` реализует `WebApplicationInitializer` и регистрирует `DispatcherServlet` программно — `web.xml` не требуется.

## 📝 Лицензия

Учебный проект. Свободен для использования в качестве основы.