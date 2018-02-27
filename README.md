# dynamodb-table-update-lambda
Lambda function to handle event coming from dynamodb stream on update/insert/delete

### Build
This is a maven project, just do ```mvn clean package```

### Deploy lambda to AWS

Code uses [lambda-maven-plugin](https://github.com/SeanRoy/lambda-maven-plugin)

Remove lambda from AWS: ```mvn lambda:delete-lambda```

Deploy lambda to AWS: ```mvn clean package lambda:deploy-lambda```


mind that code uses profiles and properties:

| Property | Description |
| --- | ---|
|accessKey (Optionally: uncomment to use it)|Your user's AWS access key. |
|secretKey (Optionally: uncomment to use it)|Your user's AWS secret key. |
|lambda.functionCode |  jar file for a Java8 lambda function |
|lambda.version | |
|lambda.publish | used to request AWS Lambda to update the Lambda function and publish a version as an atomic operation |
|lambda.forceUpdate |  used to force update of existing configuration |
|lambda.functionNameSuffix | |
|lambda.region | AWS region to deploy function to |
|lambda.s3.bucket | The AWS S3 bucket to which to upload your code from which it will be deployed to Lambda. |
|lambda.role.arn | The  AWS role ARN - role which lambda user will use for execution. Should be able to execute lambda, perform the GetRecords, GetShardIterator, DescribeStream, and ListStreams Actions on your DynamoDB stream |

Pls see more at [lambda-maven-plugin](https://github.com/SeanRoy/lambda-maven-plugin)

### Prerequisites
  * AWS account
  * DynamoDB with at least one table
  * aws key/secret on your file system in .aws/credentials file (unless you specify it directly in pom (accessKey/secretKey))  
  
### Setup dynamoDB stream:
  * sign-in your AWS console
  * Go to DynamoDB Service page
  * Select table you want to setup stream for
  * Switch to '*Overview*' tab
  * click "Manage stream" button
  * select desired "view type". "New and old images" is default and in most cases should perfectly fit your goals:
  <img src="/pics/ddb-streams.png" alt="screenshot" title="screenshot"  height="400" />
