# ReqRes.in-API-Testing
API Testing of ReqRes.in with Java Http, TestNG and JUnit

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Structure](#structure)
- [Documentation](#documentation)
- [Testing Approach in Order](testing-approach-in-order)
- [Test Coverage](#test-coverage)
- [Technologies and Tools](#technologies-and-tools)

## Installation

Clone it with the following command:

```bash
  git clone https://github.com/andresismaelsalomon/ReqRes.in-API-Testing.git
```
Open it with your preferred IDE.

## Usage
The tests must be executed from a testng suite.

### Examples
Right click a TESTNG xml file and click "Run '..\test-ng.xml" or "Run '..\test-ng.xml' with coverage

![App Screenshot](https://i.ibb.co/3vVsbmX/image-2023-08-08-182118133.png)

## Structure

The tests suites are located on the resources folder of java test (src.test.java.resources).

```
├───.idea
├───src
|    ├───main
|    │   ├───java
|    │   └───resources
|    └───test
|        ├───java
|            └───resources
|
└───postman-collections
```

## Documentation
This time I made No documentation. And the reason of that is that there already is a proper documentation.

You can find it here:
https://reqres.in/api-docs/

## Testing Approach in Order
### 1-Exploration Testing of the API with Postman.
### 2-Created a postman collection for the endpoints: "resources","users","sessions".
### 3-Created the tests using JUnit.
### 4-Migrated tests to TestNG.


## Test Coverage
Since nothing was done on the main Java package, coverage will stay 0.

## Technologies and Tools
### TestNG, JUnit and Java Http
