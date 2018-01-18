This project has been created to demonstrate the assertion failure with some french messages, as discussed [here](https://github.com/zalando/problem-spring-web/issues/123).

##### Building the Project

```Shell
gradlew clean build
```

The build will fail with:

> Expected: "Le code doit contenir entre 10 et 20 caractères"
>      but: was "Le code doit contenir entre 10 et 20 caractÃ¨res"

In order to make the test succeed, the [FrenchAssertionFailureDemo#negotiate](https://github.com/sbraconnier/problem-spring-web-demo/blob/master/src/main/java/com/example/demo/FrenchAssertionFailureDemo.java#L39) function must be uncommented.