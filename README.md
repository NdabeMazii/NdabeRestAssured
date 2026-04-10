
# NdabeRestAssured

This repository contains an automated API test suite implemented in Java. The tests exercise REST endpoints and include utilities for test setup and data access.

Tools used
- Java (JDK) — language and runtime
- Maven — build and test runner
- RestAssured — HTTP/API testing library
- TestNG — test framework (configured in `pom.xml`)
- Allure TestNG adapter — test reporting (produces `allure-results/`)
- MySQL Connector/J — database access for test utilities


Project layout (high level)
- Tests and test utilities: `src/test/java`
- Test resources and configuration: `src/test/resources`
- Build configuration: `pom.xml`
- Collected test results: `allure-results/`

What is tested
- End-to-end user lifecycle for the API under test, including:
  - User registration (create a new user)
  - Admin login (uses admin credentials loaded from the test DB)
  - Approving the newly registered user
  - Granting the user an admin role
  - Verifying the user's admin role
  - Logging in as the new user
  - Deleting the user

Package and class responsibilities
- `Tests` package
  - `UserRegistrationTest` — orchestrates the end-to-end user lifecycle test flow. Uses TestNG `@BeforeClass` and `@Test` (with dependsOnMethods) to run steps in order.

- `RequestBuilder` package
  - `ApiRequestBuilder` — builds and sends API calls (register, login, approve, role change, verify, delete). Manages tokens and IDs between calls.

- `Payload` package
  - `PayloadBuilder` — constructs JSON payloads used by the API (login, register, role change, delete, etc.).

- `Utilities` package
  - `Requests` — small wrappers around RestAssured to standardize request/response usage.
  - `DatabaseConnection` — loads admin credentials from a test database (or from `config.properties`/env or system properties). Used to perform admin operations in tests.

- `Base` package
  - `BaseURIs` — defines the base URL used by requests.

