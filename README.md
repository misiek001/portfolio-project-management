# General info
Purpose of this Application is to help employees of IT Department with Project Portfolio Management process.
Application has a Client-Server, monolithic, layer based architecture. App is built according to REST model.

[API Documentation](https://misiek001.github.io/project-portfolio-management/)

# Domain

As contexts, one can distinguish:
* project workflow (Core context)
* employee management
* project roles management
* business units management

## Project Workflow
[Click to see project workflow](https://drive.google.com/file/d/1Ihta4MocTjkiUhOIOLHBIMsfH1NAleHW/view?usp=sharing)

Currently, open and reject project stages are implemented.

Main aggregate is Project, which is central element of whole application

## Employee Management
In app, there are following types of employees:
* Director - Head of department
* Business Relation Manager - Representative in matter of project run by IT department. Decides about opening or rejecting projects and gathers requirements. Subjects directly to Director. 
* Supervisor - lead the team of Consultants. Team focus on particular IT System of Enterprise
* Consultant - run several projects, which scope is determined by skills and competencies of consultant
* Business Employee - represents other employee than other than of IT Depratment

## Project Role management
Employees can have following Roles in Project:
* Project Manager - reponsible for managing Project in whole lifecycle. Supervisor and Consultant can be PM  .
* Solution Architect - responsible for preparing technical documentation of project and development of solution. Only consultant can be SA. Very often, PM is also SA.
* Resource Manager - mostly one of the Supervisors. Leader of team, which skills matches Project scopes and requirements. RM decides about such matters as, who is PM and SA, budget size and other resource availability.   
* Business Leader - Business Employee, which represents Business Unit, for which project is run.

## Use Case Diagram for Employees and Project Roles
[Click to see different Use Case Diagrams for Different Actors](https://drive.google.com/file/d/1N_0L0MQUMkbNgf6KKb9WDYHpNGVTr6RM/view?usp=sharing)


# Technologies
Java, Spring Framework: Web, Security, Hibernate, Swagger

