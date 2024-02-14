# Spring Boot Testing Project

This repository is a work in progress. I will be updating it over the next week or two (Feb 14, 2024)

It demonstrates the basics of writing unit, integration, and end-to-end tests for a Spring Boot application.

Although I've been testing Spring Boot Applications directly and indirectly for many years, I still like to study the
subject and improve my skills. As such, I studied a course provided by Philip Riecks, [Testing Spring Boot Applications Primer](https://rieckpil.de/testing-spring-boot-applications-primer/).  

The code in this project is my implementation of what I studied during the course. I've added and removed elements, as I
saw fit, to better my understanding of the topics covered. As such, I've added a lot more comments than usual to aid as
reminders to myself. I've also used additional components like Lombok to remove boilerplate code.

## Spring Boot and the Test Pyramid

This example project will cover the various levels of testing that takes place in a Spring Boot project.  A good 
visualization of how the test pyramid for Spring Boot projects can look can be seen in this diagram:

![Spring Test Pyramid](/assets/images/SpringTestPyramid.png)

Taken from the excellent presentation given by Sannidhi Jalukar and Madhura Bhave: [Test Driven Development with Spring Boot.](https://www.youtube.com/watch?v=s9vt6UJiHg4&t=2239s)

From top to bottom:
* We have a small number of tests that work with @SpringBootTest and the entire Spring context (integration or end-to-end
tests)
* We have some unit-like tests with Spring Test support to test slices of our application
* We have a lot of unit tests that solely use JUnit and Mockito

There is no standard definition of what a unit or integration test is. We can find multiple definitions in many 
different books and naming conventions vary from team to team.  Just pick a naming convention and be consistent!  
 