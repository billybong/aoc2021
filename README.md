# Advent of Code 2021

## Java
This Sean Bean of programming languages has been pronounced dead more times than Brian Goetz has had lunch.  
Always frowned upon, considered too slow, wordy and unsexy this language somehow continiously manages to stay relevant within the industry.  
Recent investments in tech, language ergonomics and performance as well as the faster release cadence makes this language more popular than ever and is still the go-to toolbox for a large majority of developers.

## GraalVM
Since the execution duration for AoC assignments are typically really short these solutions all run native compiled code using the [Graal Substrate VM](https://github.com/oracle/graal/blob/master/docs/reference-manual/native-image/README.md), trading JIT compilation for faster startup.  
This also has the effect of slimming down the docker containers significantly. A typical solution is packaged up as a 20Mb container using Alpine.

## Tips for IntelliJ
Since all days share the same class name you need to have only one `src` folder marked as source root at the same time.
Unmark the days you are not using with `Mark Directory As -> Unmark As Source Root`

## Testing
- run all `./test.sh`
- run specific day `./test.sh <day>` where `<day>` is a day number, i.e `1`