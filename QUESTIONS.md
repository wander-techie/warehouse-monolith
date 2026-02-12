# Questions

Here we have 3 questions related to the code base for you to answer. It is not about right or wrong, but more about what's the reasoning behind your decisions.

1. In this code base, we have some different implementation strategies when it comes to database access layer and manipulation. If you would maintain this code base, would you refactor any of those? Why?

**Answer:**
```
Yes, I would try to standardize the database access approach. Right now, the project mixes a Ports & Adapters style repository pattern. While both work, having two different approaches in the same codebase can make it harder to maintain and understand. I would prefer using a consistent repository pattern because it separates business logic from persistence logic and makes testing easier.
```
----
2. When it comes to API spec and endpoints handlers, we have an Open API yaml file for the `Warehouse` API from which we generate code, but for the other endpoints - `Product` and `Store` - we just coded directly everything. What would be your thoughts about what are the pros and cons of each approach and what would be your choice?

**Answer:**
```
Using OpenAPI to generate code is good because it ensures a clear API contract and up-to-date documentation. It is especially useful when multiple teams depend on the API, it adds some complexity to the build process. Writing endpoints manually is faster and simpler for smaller services but can lead to inconsistencies. I would prefer a consistent approach across the project, and I would use OpenAPI for APIs that are externally exposed.
```
----
3. Given the need to balance thorough testing with time and resource constraints, how would you prioritize and implement tests for this project? Which types of tests would you focus on, and how would you ensure test coverage remains effective over time?

**Answer:**
```
With limited time, I would focus first on unit tests for the Use Cases, since they contain the main business logic. These tests are fast and easy to maintain. After that, I would add a few integration tests to ensure that the database and REST endpoints work correctly together. This way, the most important logic is covered while keeping the testing effort reasonable.
```