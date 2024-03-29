# Test Microservices Project

## Описание

Этот проект представляет собой обучающий тестовый пет-проект, разработанный для демонстрации создания и использования микросервисной архитектуры на основе Spring Boot. Проект состоит из двух основных микросервисов: `productApi` и `consumerService`. 
Микросервис `productApi` отвечает за управление продуктами и категориями, в то время как `consumerService` предоставляет пользовательский интерфейс для взаимодействия с данными продуктов и категорий на Thymeleaf .

## Тестирование и запуск

Проект подготовлен для запуска через docker-compose.yml, "localhost" в коде заменены на названия микросервисов.
Функциональность основных функций можно увидеть на consumerService:8080/products.html, 
где реализован минимальный frontend, позволяющий работать с поиском, пагинацей, изменением, добавлением и удалением products и reviews для products.
Liquibase автоматически наполнит базу псевдо-рабочую базу стартовыми значениями, для тестов используется отдельная база данных с другими значениями через Testcontainers.


### Микросервисы

- **productApi**: Сервис для управления продуктами и категориями. Поддерживает операции добавления, просмотра, редактирования и удаления продуктов и категорий.
- **consumerService**: Потребительский сервис, предоставляющий веб-интерфейс для взаимодействия с `productApi`. Использует Thymeleaf для генерации динамических веб-страниц.

### Ключевые функции

- **Управление категориями**: Добавление, просмотр, редактирование и удаление категорий продуктов.
- **Управление продуктами**: Добавление, просмотр, редактирование и удаление продуктов.
- **Фильтрация продуктов по категориям**: Возможность фильтровать продукты по принадлежности к определенной категории.
- **Пагинация**: Поддержка пагинации для удобного просмотра списка продуктов.
- **Валидация данных**: Проверка корректности входящих данных при добавлении или редактировании продуктов и категорий.

### Основные методы

#### CategoryController

- `createCategory(@RequestBody @Validated CategoryInfoDTO)`: Создание новой категории.
- `getAllCategories()`: Получение списка всех категорий.
- `getCategoryById(@PathVariable Long id)`: Получение категории по идентификатору.
- `updateCategory(@PathVariable Long id, @RequestBody @Validated CategoryInfoDTO)`: Обновление информации о категории.
- `deleteCategory(@PathVariable Long id)`: Удаление категории.

#### ProductController

- `createProduct(@RequestBody @Validated ProductInfoReviewsDTO)`: Создание нового продукта.
- `getAllProducts(@RequestParam)`: Получение списка всех продуктов с поддержкой фильтрации по категории и пагинации.
- `getProductById(@PathVariable Long id)`: Получение продукта по идентификатору.
- `updateProduct(@PathVariable Long id, @RequestBody @Validated ProductInfoReviewsDTO)`: Обновление информации о продукте.
- `deleteProduct(@PathVariable Long id)`: Удаление продукта.

### Глобальная обработка исключений

Проект включает в себя `GlobalExceptionHandler` для обработки исключений и возвращения клиенту информативных сообщений об ошибках.

### Использование Liquibase

Для управления версиями базы данных и миграций используется Liquibase. Конфигурация миграций находится в `db.changelog/db.changelog-master.yaml`.

### Интеграционное тестирование

Для интеграционного тестирования используется Testcontainers с PostgreSQL контейнером. Класс `IntegrationTestBase` настраивает контейнер и предоставляет доступ к базе данных для тестов.
