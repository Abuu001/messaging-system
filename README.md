# Messaging system
### _Text Message Sending System_

This repository contains code base for backend and api for creating an sms/message service.
The Primary language/framework used in the code base is java/springboot and the app has been built in a microservice architecture.


## The main services include
- __Broadcast-Service__ : one message is sent to a contact list with many numbers
- __Upload-service__ :  Basically different personalised messages sent to different people. This service picks an excel file and extracts the contents and sends the message.
- __Transactional-service__ : One message sent to one person. Most use case is message sent to a person when doing a transaction.
- __ContactList-Service__ : This service picks an excel or csv file with contact details eg name, phone_number and extracts the excel file details and creates a contact list. or adds contacts details to existing contactLists.
- __config-server__ : Holds all the microservices config details.
- __credit-service__ : Service is incharge of creating charges, crediting and debiting clients accounts.
- __notification-service__ : Incharge of all notification or observability of the system and sending alerts.
- __MidlleWare__ : Should be the API interfacing between the front-end and back-end.

### Tech Used(Once everything is completed):
1. [RabbitMQ](https://www.rabbitmq.com/download.html)
2. [Redis](https://redis.io/download)
3. [Mongodb](https://www.mongodb.com/)

### RabbitMQ
- Install RabbitmQ
- Make sure you install [rabbitmq-delayed-message-exchange](https://github.com/rabbitmq/rabbitmq-delayed-message-exchange) plugin. This helps in scheduling the messages.

**NB**
Ensure you run __config-server__ before you run any other service as they get __configuration__ details from config-server service.