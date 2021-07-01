# REST API

Every request (except login and register) should have the "Token: \<TOKEN\>" header.

You receive the token header when you log in using your username and password.

The token expires after a given period of time.

Don't forget to specify the Content-Type (usually application/json) header when sending data.

## Register: 
```POST /auth/register```

### Parameters
```json
{
    "username": "iloveauth",
    "password": "abcd1234",
    "email": "iloveauthentication@example.com"
}
```
### Response: 200 OK if successful

<br>

## Login: 
```POST /auth/login```

### Parameters
```json
{
    "username": "iloveauth",
    "password": "abcd1234"
}
```
### Response: Token as plain text

<br>

## Overview
    

```GET /overview```
### Response
  ```json
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