
# brightwheel-project

##  Pre-requisites
### a. Signup for mailgun service
*  Fetch your domain name
* Fetch your api key
### b. Signup for SendGrid service
* Fetch your api key
    
## Installation Instructions
### 1. Clone the repo in your local by running the following command:
git clone https://github.com/nikitakale04/brightwheel-project.git
### 2. Update application.properties
#### a. Configure emailProvider API Keys

` mailgun.apiKey= `
`sendgrid.apiKey=`


#### b. Configure your domain name for mailgun
  `mailgun.domainName=`

##### c. Configure emailProvider to use
`emailServiceProvider.serviceName=mailgun`
  or
`emailServiceProvider.serviceName=sendgrid`

### 3. Build and Run Unit Tests
`./mvnw clean verify`

### 4. Run application
go to the path /email-service in your terminal and run the following command:
`./mvnw spring-boot:run`

### 5. Use the APIs
#### Swagger UI: 
Open the Swagger UI : http://localhost:8080/swagger-ui.html
#### Sample Input for POST body
{
  "body": "Sample Email Content. Hello World!",
  "from": "noreply@mybrightwheel.com",
  "from_name": "brightwheel swagger",
  "subject": "brightwheel test email",
  "to": "recipient_email@example.com",
  "to_name": "Recipient Name"
}

## Choice of language, framework and libraries
### JAVA 8, Spring Boot
I have extensively used these technologies.
Spring Boot have lot of built in libraries which makes coding faster and easier. Spring Boot reduces lots of development time and increases productivity. It avoids writing lots of boilerplate Code, Annotations and XML Configuration.
### Libraries used:
1. org.jsoup - To remove HTML tags from the body text
1. org.apache.commons - 
1. io.springfox.springfox-swagger-ui - to add swagger doc
1. org.apache.httpcomponents - to add the http client to make the rest call
1. junit - to add junit tests

## Things I might do differently if I get to spend additional time 
1. I would like to add more junit tests if I get more time.
1. Convert to reactive Spring boot to improve scalability.
1. Add more functions in services to have more separation of concerns

## Notes
1. Messages delivered via sendgrid may end up in spam folders
