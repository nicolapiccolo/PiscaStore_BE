# PiscaStore BackEnd

Back End con Spring Boot per il server della piattaforma.

# MicroService ports
- 8081 -> User Service
- 8082 -> Catalog Service

API dei vari Controller implementati:

| Metodo | URL          | Azione                             |
| ------ | ------------ | ---------------------------------- |
| POST   | /auth/signup | registrazione nuovo account        |
| POST   | /auth/signin | login con account esistente        |
| GET    | /category    | lista categorie oggetti            |
| GET    | /test/all    | contenuto pubblico                 |
| GET    | /test/user   | contenuto per utente (user e modo) |
| GET    | /test/admin  | contenuto per utente admin         |
| GET    | /test/mod    | contenuto per utente moderatore    |

- **REGISTRAZIONE**

  Esempio di body da inserire per il POST signup

  ```json
  {
      "username": "nico",
      "email": "nico@pisca.com",
      "password": "12345678",
      "role": [
          "admin",
          "user"
      ]
  }
  ```

- **LOGIN**

  Esempio di body da inserire per il POST signin

  ```json
  {
      "username": "nico",
      "password": "12345678"
  }
  ```

Una volta registratosi, nell'header della richiesta andrà inserito il token JWT generato durante la registrazione. Esempio:

```json
KEY: Authorization
VALUE: "Bearer -tokenCode-"
```

