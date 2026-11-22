## Week 10 Assignment 2: Implementing Cross-Cutting Concerns with Spring AOP (Library System)

### AOP Concepts

**Aspect:** An aspect is a class that contains functionality that can be applied across different parts of an application. For example in this assignment the `LoggingAspect` handles logging across all methods in the application and the remaining aspects `PerformanceAspect` and `ValidationAspect` test method performance and validation.

**Join Point:** A join point is a location during program execution where advice can be applied. In this assignment `LibraryService.getBookById()` is used as a join point.

**Advice:** Advice is the code that executes when a method matches an aspect's pointcut. For example, the `logBefore()` method runs before a matching service method can execute.

**Pointcut:** A pointcut determines which methods an aspect should intercept. The `serviceMethods()` pointcut targets methods in the `LibraryService` class and is reused by the logging advice methods.

**Weaving:** Weaving is the process of applying each aspect logic to the system code and uses proxies to intercept calls to Spring-managed objects such as the `LibraryService` class.

### Advice Types

- `@Before`: Executes before a method runs. Used by the `LoggingAspect` to log method names and arguments and by `ValidationAspect` to validate book IDs.
- `@After`: Executes after a method is completed, whether it is successful or not.
- `@AfterReturning`: Executes after a method completes successfully. Used to log the values returned by service methods.
- `@AfterThrowing`: Executes when a method throws an exception. Used to log exception types and messages.
- `@Around`: Executes around an intercepted method. Used by the `PerformanceAspect` to measure execution times of the various methods.

### Implemented Aspects

**LoggingAspect**

Uses `@Before`, `@AfterReturning`, and `@AfterThrowing` to log service method calls, successful results, and exceptions.

**PerformanceAspect**

Uses `@Around` and `System.nanoTime()` to measure excavtly how long repository methods take to execute.

**ValidationAspect**

Uses `@Before` advice to reject null, zero, or negative book IDs before the service method executes. Invalid IDs will cause an `IllegalArgumentException`. and throws a specific error message based on if the ID is null or zero.

### Reusable Pointcuts

### Testing

The application demonstrates the following scenarios:

1. Retrieving an existing book successfully.
```text
--- Logging Aspect: @Before ---
Calling method logged: getBookById
Arguments logged: [1]
(Logging) Method completed: getBookById
(Logging) Returned value: Book{id=1, title='Star Wars: Heir to the Empire'}
```

3. Requesting a nonexistent book and logging the exception.
```text
--- Logging Exception Test ---
(Logging) Exception type: IllegalArgumentException
(Logging) exception message: Book ID: 586 not found!
Exception caught: Book ID: 586 not found!
```

4. Rejecting a negative book ID.
```text
--- Validation Test ---
(Logging) Arguments: [-4]
(Validation) Invalid ID:  Invalid Book ID: ID must have a number and be greater than zero!
```


6. Rejecting a null book ID.
```text
--- Validation Test ---
(Logging) Arguments: [null]
(Validation) Null ID: Invalid Book ID: ID must have a number and be greater than zero!
```


8. Measuring repository execution times.
```text
(Performance) method: findAll
(Performance) Execution time: 106.1768 ms
(Performance) method: save
(Performance) Execution time: 59.4365 ms
(Performance) method: findById
(Performance) Execution time: 4.4108 ms
```

