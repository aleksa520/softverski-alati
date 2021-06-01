# Companies Application - backend

## Clojure REST API

This is a clojure project, that uses Compojure, Swagger, Ring and Korma. Swagger is for "sweet API", and Korma is used for communicating with the MySQL database.

## Requirements

First thing you need is Java. You can check if you have java on your machine with 

    java -version

Install [Leiningen](https://leiningen.org/) on your machine.

Install [MySQL](https://dev.mysql.com/downloads/mysql/).
You can create database from script that is located in database.sql file in resources folder. Change database configuration in database.clj file in database folder.
Main thing to change is user and password.

## Start the server 

    lein ring server

It will open the Swagger "sweet API" for Clojure in browser.

![ss](https://user-images.githubusercontent.com/36844154/120293419-89223b80-c2c5-11eb-8456-45d842820352.PNG)


## Testing
Tests are located in core_test.clj file. They can be started with following command

    lein test
    
![testovi](https://user-images.githubusercontent.com/36844154/120312511-17ec8380-c2d9-11eb-8494-846acb550211.PNG)

## License

Copyright © 2021 Aleksa Pavlovic
