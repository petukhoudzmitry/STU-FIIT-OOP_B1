# PC Platform
## Documentation
### Project
PC Platform is a web application that provides users with an interactive environment to communicate with other users in form of elections. Ordianry users, called simple users, are able to create petitions addressed to registered companies in which they can share their ideas and enhacements with other users and companies. Companies will then be able to respond to petitions that receive a sufficient number of votes, thereby increasing the quality of services provided in accordance with the real preferences of users. The entire communication process will be under the control of administrators, who are able to deactivate petitions or simple users accounts that violate the rules of integrity. This will ensure transparency and fairness within the platform. The project thus supports democratic dialogue and enables change in the business world based on the opinions and needs of the general public.

### Requirements
  * Installed SQLite
  * Java 21
### Technologies
  * Java 21
  * Database: SQLite
  * Frameworks: Spring Thymeleaf, Spring Security, Spring Boot, Spring Web, Spring JPA, Hibernate, Bootstrap
### How to run
  * Check if you meet the [Requirements](#Requirements "Requirements")
  * Clone the repository
  * Open project in your IDE
  * Run the project
  * Open your browser and visit `http://localhost:8080/` page

## FAQ
  * On the very first run in terminal you'll be prompted with super-user credentials to access super-user account. After creating new super-user account, default super-user account will be deleted and the newly created account will possess root privileges

## Course project requirements
- [x] Use of design patterns:
  * Observer - [Observer path](/blob/main/src/main/java/com/petition/platform/ooprequirements/EventManager.java "Observer")
  * Abstract Factory - [Abstract Factory path](/blob/main/src/main/java/com/petition/platform/ooprequirements/UserFactory.java "Abstract Factory")
  * Double-Cheked Locking - [Double-Cheked Locking path](/blob/main/src/main/java/com/petition/platform/ooprequirements/EventManager.java "Double-Checked Locking")
  * Singletone - [Singletone](/blob/main/src/main/java/com/petition/platform/ooprequirements/EventManager.java "Singletone")
  * MVC - [controllers](/tree/main/src/main/java/com/petition/platform/controllers "controllers") / [models](/tree/main/src/main/java/com/petition/platform/models "models") / [views](/tree/main/src/main/resources/templates "templates")
  * DAO - [repositories](/tree/main/src/main/java/com/petition/platform/repositories "repositories")
  * Dependency Injection - All services are injected into controllers
  * Inversion of Control - Spring ApplicationContext
  * Aggregation - [Aggregation](/blob/main/src/main/java/com/petition/platform/models/SimpleUser.java "Aggregation") - list of SimplePetitions in SimpleUser class
  * 
