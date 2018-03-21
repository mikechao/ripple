# Trustline

### Introduction
This repo contains my solution to the Ripple Java Technical Challenge
Built using Apache Maven 3.5.2 with Java 1.8

### Building the projects
The following command should compile, test and build the jars
```
ripple/mvn package
```

### Jars are located in
    ripple/server/target
    ripple/client/target

### Code coverage reports located in
    ripple/common/target/coverage-reports/index.html
	ripple/server/target/coverage-reports/index.html

### Launching the server
The following command launches the server for the user Bob
```
java -jar ripple/server/target/trustline-server-0.0.1-SNAPSHOT.jar --user=Bob --server.port=8090
```
The following command launches the server for the user Alice
```
java -jar ripple/server/target/trustline-server-0.0.1-SNAPSHOT.jar --user=Alice --server.port=8091
```

### Launching the client
The following command launches the client
```
java -jar ripple/client/target/trustline-client-0.0.1-SNAPSHOT.jar --server1.port=8090 --server1.name=Bob --server2.port=8091 --server2.name=Alice
```

### Limitations
1. Both servers must run of the same machine
2. The machine must have port 61616 open