
To get this authentication working

1. under entity package.. In each entity please change catalog name to your database name.
2. and in application properties.. change database configurations accordingly
    particularly :  spring.datasource.url=jdbc:mysql://127.0.0.1:3306/pbrain?autoReconnect=true
                    spring.datasource.password=root

3. Server runs on port 9999 as per configuration in application.properties -> server.port=9999

4. Note: You need to add a user to be able to login..
    adduser.sql file in sql package has insert statement to add one user ->username :  "admin", password : "admin123@".

Rest services
---------------
login:
    /rest/auth/login -> POST -> with headers
        X-Auth-Username : admin
        X-Auth-Password : admin123@

    Response would be like :
        {
          "result": {
            "id": 1,
            "name": "admin",
            "password": "",
            "email": "admin@razorthink.com",
            "token": "6cb1ce0c-89e8-40b2-9574-72dd76b2efb9"
          },
          "status": "SUCCESS",
          "exception": null,
          "httpStatus": "OK",
          "httpStatusCode": 200
        }

accessing any resource:
       have header
        X-Auth-Token : 6cb1ce0c-89e8-40b2-9574-72dd76b2efb9  (the token returned by login service)

logout:
        have header
            X-Auth-Token : ....
            and hit /rest/auth/logout -> GET (will return some 401 .. but token will be expired)
