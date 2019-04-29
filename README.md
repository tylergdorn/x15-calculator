# x15-Calculator

x15-Calculator is a coding test I did which takes in a simple "program" and returns the calculated value.

## Example
given the script
```
a = 5;
b = 6;
c = a * 3 + b;
result = c ^ 2;
```

It will print out the result, 441.

It always prints out the value of result at the end of execution.

## Use

The calculator was built on JDK 11, and Gradle 5.14
you can use `gradle build` to build the program and `gradle test` to run all tests.
currently, running your own tests can be done by modifying runner.java.