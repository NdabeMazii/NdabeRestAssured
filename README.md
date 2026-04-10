# NdabeRestAssured — Simple README

This project contains automated API tests built with RestAssured and run by Maven. Test results are collected by Allure and stored in `allure-results/`.

What this project uses
- Java (JDK) — language/runtime
- Maven — build and test runner
- RestAssured — API testing library
- TestNG — test framework (see `pom.xml`)
- Allure TestNG adapter — generates `allure-results/` for reporting
- MySQL connector — used by test utilities when connecting to a database

Quick commands
- Run all tests:

```powershell
mvn clean test
```

- Run a single test class:

```powershell
mvn -Dtest=MyTestClass test
```

- View Allure report (requires Allure CLI):

```powershell
allure serve allure-results
```

Where to look
- Tests: `src/test/java`
- Allure data: `allure-results/`
- Build config: `pom.xml`

