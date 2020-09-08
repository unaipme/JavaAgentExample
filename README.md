# _Java Agent_ing

Java agents are Java _programs_ that can attach themselves to running JVM processes and modify bytecode through the _Instrumentation API_. This can be used for a great variety of... stuff.
In the example, I am using it to print out the name of the running threads. This can be useful, for example, for debugging COMPSs.

Sometimes, after the execution of the application is done, COMPSs does not exit. This is because there remain threads running in the background which are not the `main` thread.
To be able to find the problem of why the threads are not properly being terminated, first these threads must be identified. That is what the agent is for.

## Project structure

The project includes two Java classes with a `main` method. One is `EndlessMain`, which is just an endless `while true` loop. This program will represent the target JVM process in which the agent
will be _injected_. The second `main` is `AttachMain`, which is a small but completely necessary program that takes care of injecting the requested agent in the requested JVM process.

For the `AttachMain` code to work, the `tools.jar` dependency is required. The dependency is configured for compile-time in the `pom.xml` file, but, unless the POM is rewritten to make the
generated JAR an über JAR packed with its dependencies, the `tools.jar` dependency will have to be added manually to the classpath upon execution. This approach is explained later on.

Finally, the `Agent` class is the agent per se. The `agentmain` method is the one automatically executed when injecting the class as an agent through the `AttachMain` class.
The implementation of the method included in this repo just prints the name of all running threads, including its own. This code could be anything really, but be sure to check out what can
and can not be done through the _Instrumentation API_.

The _Instrumentation API_ needs to be injected into the code, and this can not be done by running the code regularly. The manifest file must include a line where the `Agent` class is defined as the
`Agent-Class` of the JAR. This has been configured through the POM and is automatically done upon building.

## Building and running the project

The project can be built by just running `mvn clean package`. A JAR is generated, including both main classes and manifest modifications.

For the _demonstration_, the `EndlessMain` must be running first. To do this, run `java -cp target/demo.jar EndlessMain` after building from the same directory as the POM file.
Get the PID of the program by running `ps aux | grep EndlessMain`.

To inject the agent, run the command below from another terminal session. The environmental variable `$JAVA_HOME` is assumed to be set and pointing to a JDK installation. This demo has been tried
with OpenJDK 1.8. Variable `$PID` represents the PID of the `EndlessMain` program.

```bash
java -cp $JAVA_HOME/lib/tools.jar:target/demo.jar AttachMain $PID $PWD/target/demo.jar
```

After this last command, the names of all running threads must have popped out in the standard output of the `EndlessMain` program, which should include, at least, a thread named `main`
and a `THIS IS THE AGENT THREAD` named one.