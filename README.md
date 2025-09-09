This project has been converted to a Maven project.

* Source code: src/main/java/com/dws/tradestore/
* Application class: com.dws.tradestore.TradeStoreApplication
* To build: mvn clean package
* To run: Run TradeStoreApplication class as Java application

You can now add dependencies to pom.xml as needed.



Assumptions:

1. User will use only 1 DB at a time i.e. sql or no-sql, hence separate application yaml are provided
2. H2 in-memory db is used only for testing
3. Trade can be received from Kafka topic or can be published via API
4. Authentication is done using JWT token



Implementation:

1. Springboot microservices to add and retrieve the trades
2. Validations added in service layer as per the instructions
3. Kakfa configuration added 
4. pipeline is setup using gitlab-ci.yaml. Pipeline contains jobs for sonar and fortify for code validation.
5. Test cases added for controller and service layer
6. Filter added for authentication
