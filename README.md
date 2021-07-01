# RESTFul PasswordManager project

## Setup

Set a postgresql database up (you can use docker to make it easy.)

```docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=app -e POSTGRES_USER=app -d postgres```

## For Developers

* Login: ```POST /auth/login```
  * Header Param ```Content-Type: application/json```
  * Parameters
    ```
    {
    "username": "iloveauth@example.com",
    "password": "abcd1234"
    }
    ```
  * Response: Token as text
    

* Passwords Overview: ```GET /overview```
  * Head Parameter: ```Token: <INSERT TOKEN HERE>```
  * Response
  ```
    [{
      "id": "085cc0c5-f170-4693-9f5f-349bcd2c0424",
      "application": "google.com",
      "user": "s1mple133"
    },
    {
      "id": "df533ed7-bc47-478e-b997-1d5d4078032d",
      "application": "facebook.com",
      "user": "s1mple133"
    }]
  ```