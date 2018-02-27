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
  
  
  ### Log sample from cloudwatch
  To access lambda log:
  * Go to AWS console
  * Open lambda service
  * select your function
  * Open 'Monitoring' tab
  * Go to "Viw logs"
  
  Sample log for one stream event looks like below
  ```
  START RequestId: 225c79c0-4fc3-4c4b-86f4-848c17a50b06 Version: $LATEST
  
12:50:17.484 [main] INFO com.github.joraclista.ElasticReindexingLambda - handleRequest input.size = 1 
12:50:17.489 [main] INFO com.github.joraclista.ElasticReindexingLambda - Event id = 5f587c64dcd1eb2739fbf16ec42b085b; name = MODIFY; source = aws:dynamodb; source.arn = arn:aws:dynamodb:us-east-1:324609709848:table/Products/stream/2017-02-15T10:26:02.608;

12:50:17.509 [main] INFO com.github.joraclista.ElasticReindexingLambda - tableName = [Products]; region = us-east-1 

12:50:18.149 [main] INFO com.github.joraclista.handlers.ModifyRecordHandler - handle: newImage = {images=[jadore-you/img_7820__17314-1478694744-332-500.jpg, jadore-you/img_7820__17314-1478694744-244-365.jpg, jadore-you/img_7824__60490-1478694745-244-365.jpg], added=2016-11-10T15:07:25.062Z, finalStock=18, commentSummary={starsAverage=0, starsCount=0, starsCount1=0, starsCount5=0, starsCount4=0, starsCount3=0, starsCount2=0, starsValue=0}, title=Haley Mini Glitter Pattern Bardot Dress, version=1, tags=[partyseason, littleblackdress], productPopularity=11, merchantProductId=1963, material=[], merchantId=jadore-you, price=17.5, variations=[{image=jadore-you/img_7820__17314-1478694744-332-500.jpg, images=[jadore-you/img_7820__17314-1478694744-332-500.jpg, jadore-you/img_7820__17314-1478694744-244-365.jpg, jadore-you/img_7824__60490-1478694745-244-365.jpg], color=Black, size=S, sizeSelect=UK 8, sKU=1963blackuk-8, stock=2}, {image=jadore-you/img_7820__17314-1478694744-332-500.jpg, images=[jadore-you/img_7820__17314-1478694744-332-500.jpg, jadore-you/img_7820__17314-1478694744-244-365.jpg, jadore-you/img_7824__60490-1478694745-244-365.jpg], color=Black, size=S, sizeSelect=UK 10, sKU=1963blackuk-10, stock=2}, {image=jadore-you/img_7820__17314-1478694744-332-500.jpg, images=[jadore-you/img_7820__17314-1478694744-332-500.jpg, jadore-you/img_7820__17314-1478694744-244-365.jpg, jadore-you/img_7824__60490-1478694745-244-365.jpg], color=Black, size=M, sizeSelect=UK 12, sKU=1963blackuk-12, stock=2}, {image=jadore-you/img_7820__17314-1478694744-332-500.jpg, images=[jadore-you/img_7820__17314-1478694744-332-500.jpg, jadore-you/img_7820__17314-1478694744-244-365.jpg, jadore-you/img_7824__60490-1478694745-244-365.jpg], color=Black, size=M, sizeSelect=UK 14, sKU=1963blackuk-14, stock=2}], modified=2017-02-17T20:08:55.288Z, currency=GBP, id=9e5caccb-fb8e-43f1-9acc-32edd8d20a15, slug=haley-mini-glitter-pattern-bardot-dress_36ed123a-1a34-318d-9fc1-109f48bb2d26, status=PUBLIC}; oldImage = {images=[jadore-you/img_7820__17314-1478694744-332-500.jpg, jadore-you/img_7820__17314-1478694744-244-365.jpg, jadore-you/img_7824__60490-1478694745-244-365.jpg], added=2016-11-10T15:07:25.062Z, finalStock=18, commentSummary={starsAverage=0, starsCount=0, starsCount1=0, starsCount5=0, starsCount4=0, starsCount3=0, starsCount2=0, starsValue=0}, title=Haley Mini Glitter Pattern Bardot Dress, version=1, tags=[partyseason, littleblackdress], productPopularity=111, merchantProductId=1963, material=[], merchantId=jadore-you, price=17.5, variations=[{image=jadore-you/img_7820__17314-1478694744-332-500.jpg, images=[jadore-you/img_7820__17314-1478694744-332-500.jpg, jadore-you/img_7820__17314-1478694744-244-365.jpg, jadore-you/img_7824__60490-1478694745-244-365.jpg], color=Black, size=S, sizeSelect=UK 8, sKU=1963blackuk-8, stock=2}, {image=jadore-you/img_7820__17314-1478694744-332-500.jpg, images=[jadore-you/img_7820__17314-1478694744-332-500.jpg, jadore-you/img_7820__17314-1478694744-244-365.jpg, jadore-you/img_7824__60490-1478694745-244-365.jpg], color=Black, size=S, sizeSelect=UK 10, sKU=1963blackuk-10, stock=2}, {image=jadore-you/img_7820__17314-1478694744-332-500.jpg, images=[jadore-you/img_7820__17314-1478694744-332-500.jpg, jadore-you/img_7820__17314-1478694744-244-365.jpg, jadore-you/img_7824__60490-1478694745-244-365.jpg], color=Black, size=M, sizeSelect=UK 12, sKU=1963blackuk-12, stock=2}, {image=jadore-you/img_7820__17314-1478694744-332-500.jpg, images=[jadore-you/img_7820__17314-1478694744-332-500.jpg, jadore-you/img_7820__17314-1478694744-244-365.jpg, jadore-you/img_7824__60490-1478694745-244-365.jpg], color=Black, size=M, sizeSelect=UK 14, sKU=1963blackuk-14, stock=2}], modified=2017-02-17T20:08:55.288Z, currency=GBP, id=9e5caccb-fb8e-43f1-9acc-32edd8d20a15, slug=haley-mini-glitter-pattern-bardot-dress_36ed123a-1a34-318d-9fc1-109f48bb2d26, status=PUBLIC}; 

END RequestId: 225c79c0-4fc3-4c4b-86f4-848c17a50b06

REPORT RequestId: 225c79c0-4fc3-4c4b-86f4-848c17a50b06	
Duration: 238.21 ms	
Billed Duration: 300 ms 
Memory Size: 512 MB	
Max Memory Used: 58 MB	
```


### Test

for testing purporse valid jsons for incoming lambda data is used, please [see in test/resorces folder](https://github.com/joraclista/dynamodb-table-update-lambda/tree/master/src/test/resources)
