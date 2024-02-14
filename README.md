# Spring Boot Testing Project

This repository is a work in progress.

It demonstrates the basics of writing unit, integration, and end-to-end tests for a Spring Boot application.

I will build out the README as I complete each section.

This example project will cover the various levels of testing.

A good visualization of how the test pyramid for Spring Boot projects can look can be seen in this diagram:

![Spring Test Pyramid](/assets/images/SpringTestPyramid.png)

Taken from the excellent presentation given by Sannidhi Jalukar and Madhura Bhave: [Test Driven Development with Spring Boot.](https://www.youtube.com/watch?v=s9vt6UJiHg4&t=2239s)

From top to bottom:
* We have a small number of tests that work with @SpringBootTest and the entire Spring context (integration or end-to-end
tests)
* We have some unit-like tests with Spring Test support to test slices of our application
* We have a lot of unit tests that solely use JUnit and Mockito

There is no standard definition of what a unit or integration test is. We can find multiple definitions in many 
different books and naming conventions vary from team to team.  Just pick a naming convention and be consistent!  
 