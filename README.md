# Coding Challenge
## What's Provided
A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped 
with data. The application contains information about all employees at a company. On application start-up, an in-memory 
Mongo database is bootstrapped with a serialized snapshot of the database. While the application runs, the data may be
accessed and mutated in the database without impacting the snapshot.

### How to Run
The application may be executed by running `gradlew bootRun`.

### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```
The Employee has a JSON schema of:
```json
{
  "type":"Employee",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
          "type": "string"
    },
    "position": {
          "type": "string"
    },
    "department": {
          "type": "string"
    },
    "directReports": {
      "type": "array",
      "items" : "string"
    }
  }
}
```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

## What to Implement
Clone or download the repository, do not fork it.

### Task 1
Create a new type, ReportingStructure, that has two properties: employee and numberOfReports.

For the field "numberOfReports", this should equal the total number of reports under a given employee. The number of 
reports is determined to be the number of directReports for an employee and all of their distinct reports. For example, 
given the following employee structure:
```
                    John Lennon
                /               \
         Paul McCartney         Ringo Starr
                               /        \
                          Pete Best     George Harrison
```
The numberOfReports for employee John Lennon (employeeId: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4. 

This new type should have a new REST endpoint created for it. This new endpoint should accept an employeeId and return 
the fully filled out ReportingStructure for the specified employeeId. The values should be computed on the fly and will 
not be persisted.

### Task 2
Create a new type, Compensation. A Compensation has the following fields: employee, salary, and effectiveDate. Create 
two new Compensation REST endpoints. One to create and one to read by employeeId. These should persist and query the 
Compensation from the persistence layer.

## Delivery
Please upload your results to a publicly accessible Git repo. Free ones are provided by Github and Bitbucket.


## Task 1

I added additional classes:
- data/ReportingStructure.java
- service/ReportingStructureService.java
- service/impl/ReportingStructureServiceImpl.java
- controller/ReportingStructureConroller.java

The core method in <em>ReportingStructure.java </em> is recursive <em>getDirectReports(Employee)</em>. It explores all the reports of the root employer, the reports of the root's reports and so on. It does so in a recursive depth-first search fashion. While exploring it increments a global counter <em>numberOfReports</em>.<br>
I provided two constructors for <em>ReportingStructure</em>. One gets passed <em>Employee</em> type and <em>EmployeeRepository</em> from which it can retrieve all reports when in  <em>getDirectReports(Employee)</em>.

## Task 2

I added additional classes:
- data/Compensation.java
- dao/CompensationRepository.java
- service/CompensationService.java
- service/impl/CompensationServiceImpl.java
- controller/CompensationConroller.java<br>
- and I modified DataBootstrap.java

The implementation of <em>Compensation</em> type is leargely based on the implementation of <em>Employee</em> type. In order to store a reference to the given <em>Employee</em> instance, I added a nested object with <em>employeeId</em> field to the <em>employee</em> field. This way when calling the GET endpoint, the <em>employee</em> field contains all null fields but <em>employeeId</em>. I decided for this implementation as storing a reference to an entire employee class with all the fields would be too heavy-weight. The other empty contructor is used to facilitate calling the <em>getEntityFrom</em> function in a JUnit test which does not support passing contructor parameters.

## Testing

In DataBootstrapTest.java I added a JUnit Test for retrieving a Compensation object for John Lennon from json. In service/impl, I added JUnit tests for read in  ReportingStructure as well as create and read in Compensation. They all pass.
