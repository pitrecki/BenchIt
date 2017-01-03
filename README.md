## BenchIt - test performace of your code

#### Description and main features

This small piece of code deliver a very usefull functionality to your code.
You will able to measure the perfomrance of your code
 
Main features:
- Write the result to a file
- Calculation of minimum, maximum and  average execution time of a section
of code

#### How to use

```java

    /*start measure time, YES -> result will be write to file*/
    Benchmark.measureTime(() -> testThisPieceOfCode(), Options.YES);

    /*calculate the statistics, NO -> file with result will be not delete*/
    Benchmark.calculateStatistics(Options.NO)
```
