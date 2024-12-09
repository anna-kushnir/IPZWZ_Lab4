# No-SQL DB (MongoDB) data format:
### Collection "Sellers":
```
{
"id": "ObjectId",
"name": "string",
"age": "int",
"gender": "string",
"region": "string"
}
```
### Щоб вперше запустити контейнери з БД та сервером, в терміналі необхідно прописати команди:
```
./gradlew build
docker-compose up --build
```
### Або якщо треба перезапустити під час або після тестування, тоді так:
```
./gradlew clean build
docker-compose down --volumes
docker-compose up --build
```
