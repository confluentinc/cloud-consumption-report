# Overview

Project used for generating a PDF/CSV Reports at organization level for Confluent cloud to measure/monitor the cluster
usage over the period of past 7 days.
Application integrates with Service Quotas API and Metrics API and retrieves environment and cluster configuration from
confluent cloud.
For each cluster metrics are queried using Metrics API which is rate limited to 50 requests per min and loads it into
in-memory database.
Compares the metrics result with the benchmark limits based on the cluster type and resource allocation and provides the
consumption Reports.

## Pre-Requisite

1. Please follow the Confluent documentation for Metrics API setup and generating metrics API Key and Secret. https://docs.confluent.io/cloud/current/monitoring/metrics-api.html
2. Java 17+
3. Docker

**_NOTE:_** Please use a dedicated Metrics API key for this application as there is a rate limit of 50 requests/min on the api calls.

## Report Contents

1. List of clusters which have the overall usage of more than 90% (Over utilized clusters)
2. List of clusters which have the overall usage of less than 10% (Under utilized clusters)
3. Schema Registry utilization with schema count and its max limits. 
4. Detailed view of each cluster with key metrics, limits and its usage
5. Includes the % Storage growth of each cluster over the period of metrics collected.
6. Generates the CSV export of all the data for any other means of consumption.

## Usage

Create the following environment variables:

1. ORG_NAME - Name of the organization
2. METRICS_API_KEY - Cloud API key with metrics viewer privilege at org level
3. METRICS_API_SECRET - Cloud API secret
4. OUTPUT_DIR - Output directory where the jasperReports will be generated.
5. TMP_DIR - Temporary directory path defaults to /tmp (Optional)
6. GENERATE_CSV - To enable csv generation to the output directory, defaults to true (Optional)
7. GENERATE_PDF - To enable pdf generation to the output directory, defaults to true (Optional)
8. DATA_RETENTION_DAYS - No of days of metrics data to be retained, defaults to 30 (Optional). Only applicable if you
   use persistent data store.

### Execution:

#### Option 1:

- Checkout the code
- Compile and create the jar by using the following command

``` SHELL
mvn clean install
```

- Create Docker Image

``` SHELL
docker build -t confluent/cloud-consumption-report .
```

- Run Docker Container

``` SHELL
docker run -v ${OUTPUT_DIR}:/output -e "ORG_NAME=${ORG_NAME}" -e "METRICS_API_KEY=${METRICS_API_KEY}" -e "METRICS_API_SECRET=${METRICS_API_SECRET}" -e "OUTPUT_DIR=/output" -t confluent/cloud-consumption-report
```

#### Option 2:

- Check out the code
- Set the environment variables listed above
- execute following command from the root directory of the application

``` SHELL
mvn spring-boot:run
```

#### Option 3:

- Checkout the code
- Set the environment variables listed above
- Compile and create the jar by using the following command

``` SHELL
mvn clean install
```

- Execute the jar

``` SHELL
java -jar target/cloud-consumption-jasperReports-${version}.jar 
```

**_NOTE:_**  For Option 2 and 3, You will have to install the Open source(GPL) font "Liberation Sans" in the Operating
System. You can download from [here](https://dl.dafont.com/dl/?f=liberation_sans).

## Use with persistent Datastore

For scenarios where you want to generate report with metrics data longer than 7 days, you can configure the application
to use a persistent RDBMS as the backend.

Example below shows how to use postgres database as a backend. Similar can be done with other databases.
Application will take care of creating all the tables required.

### Example Using postgres:

1. Replace the hsqldb dependency in the pom.xml with the postgres database.

Find below section in pom.xml:

``` xml
    <dependency>
        <groupId>org.hsqldb</groupId>
        <artifactId>hsqldb</artifactId>
        <scope>runtime</scope>
    </dependency>
```

Replace with

``` xml
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
```

2. Configure the database connection details in application.yml

Find below section in application.yml

``` yml
spring:
  datasource:
    url: jdbc:hsqldb:mem:testdb
```

Replace with:

``` yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: user
    password: password
```

3. Make sure the application is scheduled to run atleast once every 7 days to avoid gaps in data reported.
