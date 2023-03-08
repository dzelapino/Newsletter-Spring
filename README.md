# Serwis newsletter - SpringBoot + Swagger + H2

## Obsługa użytkowników

### Użytkownik:

Posiada dwa pola:
- id : jest ono automatycznie generowane przez bazę danych
- name : imie użytkownika
- surname : nazwisko użytkownika
- email : użytkownik podaje go w body zapytania tworzącego użytkownika, email musi być unikalny

### Crud Użytkownika

Kontroler użytkowników wystawia 5 endpointów:

- POST: /api/customers
- GET: /api/customers
- GET: /api/customers/findById/{id}
- PUT: /api/customers/{id}
- DELETE: /api/customers/{id}

## Wiadomości Email

Wiadomości są wysyłane z konta w serwisie Gmail \
Plik application.properties zawiera w sobie 2 zmienne środowiskowe: \
EMAILAPPUSERNAME : odpowiada adresowi email aplikacji z której wysyłamy wiadomości \
EMAILAPPPASSWORD : jest to 16 znakowy klucz generowany jako hasło do aplikacji przez platformę google

Jeżeli używamy edytora IntelliJ edytujemy konfigurację aplikacji i dodajemy tam powyższe zmienne środowiskowe.

Jeśli nie chcemy używać zmiennych środowiskowych wystarczy zamienić odniesienie do nich na odpowiadające im wartości.

### Wysyłanie wiadomości

Aby rozesłać wiadomości do zarejestrowanych użytkowników wysyłamy request POST na adres /api/emails/toSubscribers
```json
{
  "subject": "Message subject",
  "body": "Message body"
}
```

## Swagger

Endpointy wystawiające Swaggera obsługującego aplikację to:

- Api Docs:
http://localhost:8080/api/v2/api-docs

- Swagger Ui:
http://localhost:8080/api/swagger-ui/index.html

## Logi

Logi przychodzących requestów znajdują się w pliku requestLogs.log

Używany jest filtr wrapujący każdy request zawierający w adresie /api

## Testy

Podczas uruchamiania testów trzeba pamiętać że jeżeli w pliku application.properties znajdują się zmienne środowiskowe,
należy dodać je do konfiguracji testu.
