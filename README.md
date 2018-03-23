# inventory management system

The Application is build with Spring Boot with JWT token based authentication and runtime Derby DB.


The application works for two roles. 
1) sys-admin : Users with this role are allowed to perform all GET and POST operations on Stock Resource
sys-admin token :- eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik9EY3hPVEJDT1VWR09UVXhPREpETmpreU9VRkdRelZHUlVVNU0wRkZPRU15T0RFMk1qZ3hPQSJ9.eyJ1c2VybmFtZSI6IkludmVudG9yeSBBZG1pbiIsInVzZXJpZCI6InVzcl8xIiwicm9sZSI6InN5cy1hZG1pbiJ9.UNh3jrl1BSNlYcUpsGu3CRuWLlYFdXWnu9FyyzROEfuA-EQUBtVQvmNbDxgRL4wWeNkzXNnBFhof_yeeaivgRz_jhBDHgzGHiCVcF9QlIYbkztX2K-QRt0148XvdWQnFlIMfILuBnDamIocGCy6wT5U8DwKq88Mo2_7aKy8RXTWUh0W3_Ix7AeyTQ3kmm7RJJnCeVej2E_7n_-jdFpQS5UW0_qXuBBWKrQvO2ghzjUajDCxToq6OACRjD5b-Q450-w0Lj_Hk5MrewSgUpP5V1mXk6xTxs5vva5dpQEi7X7v4ADWX3QoQlRUWtt5d-H9oIqa9XkYCVy-TNAmhrXKxUw


2) user : User with this role are only allowed to perform GET operations.
user token :- eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik9EY3hPVEJDT1VWR09UVXhPREpETmpreU9VRkdRelZHUlVVNU0wRkZPRU15T0RFMk1qZ3hPQSJ9.eyJ1c2VybmFtZSI6IkludmVudG9yeSBVc2VyIiwidXNlcmlkIjoidXNyXzIiLCJyb2xlIjoidXNlciJ9.lldOn1SjHR30ncp380vJeVv8OwhYzkPksVC2LFHEbHQoEjx4KWvZdULiO0pju363e2PVg9UsHyGU-qUsEU4dtt2GXcQEOw9KlfWLVKrlN3fi87SxE8a45tVDrbjSw4LSHR84FkDuh4zaw6c5OBSz0pPIMhqy8vhQFO_XXlEpEL8mkjxSJG_B78nJfGuu0OQVtoOm9rkBseY91_jDiM2pgCaRK5CKGp5ef1-RVWuZx_Lbd5folrwcvKCqSYJemNwgZX4ml10iKBIw_g53_qEeHpLIWugebcOBJDT4X2Tah8aFGJeE5fIAuT9JU2Aw0ZbrTN_yaI0U9gegbVHqOBfRsQ


To access the application add these token in Authorization header before making a request.

The application works with two assumption.
1) Promotion on a stock is applicable when the qunatity of stock is less than 100 Units and number of stocks in Inventory is more than 10 days.

2) Oder additonal stock advise is set, when the qunatity of stock in Inventory is less than 100 Units.

Request URLs:-
GET/POST :- http://localhost:9191/inventory/stocks

Stock Resource
 {
        "id": 1,
        "name": "First Item",
        "quantity": 100000,
        "activatePromotion": false,
        "entryDate": "30-09-2017",
        "lastUpdateDate": "2017-10-01",
        "updatedBy": "Admin User",
        "amountPerDay": 5,
        "orderAdditionalStock": false,
        "daysInInventory": 1,
        "inventoryCost": 5
   }



Note : The entry date of the stock is intentional allowed to edit by sys-admin user, so that the feature of the application can be explored.
With every start application clears the database. And perviously added stock will not appear.

