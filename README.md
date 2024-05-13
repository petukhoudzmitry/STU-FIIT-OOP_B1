# PC Platform
## Documentation
### Project
PC Platform is a web application that provides users with an interactive environment to communicate with other users in form of elections. Ordianry users, called simple users, are able to create petitions addressed to registered companies in which they can share their ideas and enhacements with other users and companies. Companies will then be able to respond to petitions that receive a sufficient number of votes, thereby increasing the quality of services provided in accordance with the real preferences of users. The entire communication process will be under the control of administrators, who are able to deactivate petitions or simple users accounts that violate the rules of integrity. This will ensure transparency and fairness within the platform. The project thus supports democratic dialogue and enables change in the business world based on the opinions and needs of the general public.

### Requirements
  * **_Installed SQLite_**
  * **_Java 21_**
### Technologies
  * Java 21
  * Database: SQLite
  * Frameworks: **Spring Thymeleaf**, **Spring Security**, **Spring Boot**, **Spring Web**, **Spring JPA**, **Hibernate**, **Bootstrap**
### How to run
  * Check if you meet the [Requirements](#Requirements "Requirements")
  * Clone the repository
  * Open project in your IDE
  * Run the project
  * Open your browser and visit `http://localhost:8080/` page

## FAQ
  * On the very first run in terminal you'll be prompted with super-user credentials to access super-user account. After creating new super-user account, default super-user account will be deleted and the newly created account will possess root privileges

## Docs
  * Javadoc

## Course project requirements
- [x] **Use of design patterns**:
  * **Observer** - [Observer path](src/main/java/com/petition/platform/ooprequirements/EventManager.java "Observer")
  * **Abstract Factory** - [Abstract Factory path](src/main/java/com/petition/platform/ooprequirements/UserFactory.java "Abstract Factory")
  * **Double-Cheked Locking** - [Double-Cheked Locking path](src/main/java/com/petition/platform/ooprequirements/EventManager.java "Double-Checked Locking")
  * **Singletone** - [Singletone](src/main/java/com/petition/platform/ooprequirements/EventManager.java "Singletone")
  * **MVC** - [controllers](src/main/java/com/petition/platform/controllers "controllers") / [models](src/main/java/com/petition/platform/models "models") / [views](src/main/resources/templates "templates")
  * **DAO** - [repositories](src/main/java/com/petition/platform/repositories "repositories")
  * **Dependency Injection** - All services are injected into controllers
  * **Inversion of Control** - Spring ApplicationContext
  * **Aggregation** - [Aggregation](src/main/java/com/petition/platform/models/SimpleUser.java "Aggregation") - list of SimplePetitions in SimpleUser class
  * **Composition** - [Composition](src/main/java/com/petition/platform/models/AbstractPetition.java "Composition") - creator field in AbstractPetition class
- [x] **Custom exceptions** - [custom exception](src/main/java/com/petition/platform/ooprequirements/InvalidArgumentListException.java "custom exception")
- [x] **GUI** - MVC
- [x] **Multithreading** - [Multithreading](src/main/java/com/petition/platform/ooprequirements/EventManager.java "Multithreading")
- [x] **Generics in custom classes** - [Generics](src/main/java/com/petition/platform/ooprequirements/UserFactory.java "Generics")
- [x] **RTTI** - [RTTI](src/main/java/com/petition/platform/models/SimplePetition.java "RTTI") - instanceof
- [x] **Nested classes and interfaces** - [Nested classes](src/main/java/com/petition/platform/ooprequirements/EventManager.java "Nested classes") - anonymous inner class that implements Runnable / [Interfaces](src/main/java/com/petition/platform/ooprequirements/EventListener.java "Interfaces") etc.
- [x] **Lambda and method references** - [Lambda](src/main/java/com/petition/platform/services/CustomUserDetailsService.java "Lambda") etc. / [Method reference](src/main/java/com/petition/platform/configs/SecurityConfig.java "Method reference")
- [x] **Default method** - [Default method](src/main/java/com/petition/platform/ooprequirements/EventListener.java "Default method")
- [ ] **AspectJ**
- [x] **Serialization** - [serializable classes](src/main/java/com/petition/platform/models/SuperUser.java "serializable classes"), [serializable classes](src/main/java/com/petition/platform/models/User.java "serializable classes"), [implementation](src/main/java/com/petition/platform/services/CustomUserDetailsService.java "implementation") - rootSuperUserPersist method
