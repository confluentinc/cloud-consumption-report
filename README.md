# cloud-consumption-report
Project used for generating a PDF report at organization level for Confluent cloud for measuring the cluster usage over the period of past 7 days.
Application integrates with Service Quotas API and Metrics API and retrieves environment and cluster configuration in confluent cloud. 
For each cluster metrics are queried using metrics api which is rate limited to 50 requests per min and loads it into in-memory database. 
Compares the metrics result with the benchmark limits based on the cluster kind and resource allocation and provides the consumption report.
## Usage
Create the following environment variables:
1. ORG_NAME - Name of the organization
2. METRICS_API_KEY  - Cloud API key with metrics viewer privilege at org level
3. METRICS_API_SECRET - Cloud API secret 
4. OUTPUT_DIR - Output directory where the report will be generated.
5. TMP_DIR - Temporary directory defaults to /tmp (Optional)
### Execution

#### Option 1
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
#### Option 2
- Check out the code
- Set the environment variables listed above
- execute following command from the root directory of the application
``` SHELL
mvn spring-boot:run
```
#### Option 3
- Checkout the code
- Set the environment variables listed above
- Compile and create the jar by using the following command
``` SHELL
mvn clean install
```
- Execute the jar
``` SHELL
java -jar target/cloud-consumption-report-${version}.jar 
```

**_NOTE:_**  For Option 2 and 3, You will have to install the Open source(GPL) font "Liberation Sans" in the Os. You can download from [here](https://dl.dafont.com/dl/?f=liberation_sans).