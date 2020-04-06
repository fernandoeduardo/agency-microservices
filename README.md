
# Agency - Micro Services
The main idea here is to create a basic micro-service project using the Saga pattern with Choreography approach. In addition, the project can be used to apply and exemplify some of the most popular technologies used today.

**The project uses:**
-Java
-Spring Boot
-Jeager (Tracing)
-Kafka (Event Message)
-Feign (Web Communication)
-Swagger (Documentation)
-Refis (Cache)
-H2 (Data Base)
-Docker


# Business rule
The project is based on the idea of a travel agency. The SAGA begins with the purchase of a travel package. At that 
moment, a purchase order is requested at the Order Manager, which saves it in the Data Base (H2) and creates an event
 in Kafka. Another micro-service (Ticket Inventory) consumes this event, saves the information in H2 and creates a new payment event.
Finally, the Payment Processor consumes the event, makes the payment or not and produces a processed payment event.


**How To:**

- Start docke-compose;

- Start the 3 micro-services in the project:
1. order-manager;
2. ticket-inventory;
3. payment-processor;


- Create a new Order by making the Request below:

curl --location --request POST 'localhost:8081/orders' \
--header 'Content-Type: application/json' \
--data-raw '{
	"customerId" : "1",
	"amount" : "344.20",
	"creditCard" : "1234-5678-4321-8765"
	
}'


- Listen to the Topics in Kafka to see the messagens tha has been created in the flow.
Topics:
  - requested-orders
  - processed-orders
  - requested-payments
  - processed-payments

- List created order and see the status:

curl --location --request GET 'localhost:8081/orders' \
--header 'Content-Type: application/json'

