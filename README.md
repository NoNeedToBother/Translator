## Запуск:
1. Скопировать репозиторий (напрямую скачать или с помощью терминала:
`git clone https://github.com/NoNeedToBother/Translator`)
2. Перейти в папку проекта (через терминал после предыдущего шага: `cd Translator`)
3. Настроить соединение с базой данных: поменять значения в файле `resources/database.properties` и выполнить SQL скрипт
для создания таблицы с сущностями результатов перевода:
```sql
create table translate_results(ip varchar, original_text varchar, translated_text varchar);
```
4. Выполнить команду `./gradlew bootRun`
## Как работать:
Приложение представляет собой REST API с одним эндпоинтом: `localhost:8080/translate`

Чтобы получить перевод, нужно на этот эндпоинт отправить POST запрос, содержащий тело в JSON-формате
```json
{
    "languageFrom": "en",
    "languageTo": "ru",
    "text": "Hello world!"
}
```
Где languageFrom - язык оригинального текста, languageTo - язык желаемого первода.
Оба языка должны быть оформлены в стандарте ISO 639-1, т.е. в виде двузначных кодов на латинице.

Например, с помощью curl:
```bash
curl -X POST localhost:8080/translate -H 'Content-Type: application/json' -d '{"languageFrom": "en","languageTo": "ru","text": "Hello World!"}'
```
В ответе должен прийти JSON, в котором поле `databaseSaveSuccess` будет указывать, успешно ли сохранилась информация в базе данных,
и в зависимости от успешности перевода будет либо поле `result` с результатом перевода,
либо поле `error` с сообщением об ошибке.