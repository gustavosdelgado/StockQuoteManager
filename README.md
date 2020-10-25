# About

Evaluation project for Inatel's application job.

# How to run

In order do run this project, you should have [Docker](https://docs.docker.com/get-docker/) and [Postman](https://www.postman.com/downloads/) (or similar) installed and execute the following commands:

    docker container run -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdb -p 3306:3306 -p 33060:33060 -d mysql:8

    docker container run -p 8080:8080 -d lucasvilela/stock-manager

    docker container run -p 8081:8081 -d benywolf42/stock-manager


# Resources available

**Method: GET**

Description: recover all quotes available from every stock.

    http://localhost:8081/stock

**Method: GET**

Parameters: id

Description: recover all quotes available from the stock specified by the *id*.

    http://localhost:8081/stock/{id}

**Method: POST**

Description: create quotes for the specified stock, if that stock is already registered in database.

    http://localhost:8081/stock

Request Body:
| Field | Description | Format | Mandatory | 
|- |- | - | - |
| id | Stock identifier | AN | YES |
| quotes[] | An array of keys and values regarding the stock quotes | Keys ("yyyy-MM-dd") and values (currency). Ex. `"2019-01-01" : "10.50"`. | YES

Request example: 

```json
{
    "id": "petr3",
    "quotes":
    {
        "2019-01-01" : "10",
        "2019-01-02" : "11",
        "2019-01-03" : "14"
    }
}
```
