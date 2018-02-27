# dynamodb-table-update-lambda
Lambda function to handle event coming from dynamodb stream on update/insert/delete

### Prerequisites
  * AWS account
  * DynamoDB with at least one table
  * aws key/secret on your file system in .aws/credentials file
  
### Setup dynamoDB stream:
  * sign-in your AWS console
  * Go to DynamoDB Service page
  * Select table you want to setup stream for
  * Switch to '*Overview*' tab
  * click "Manage stream" button
  * select desired "view type". "New and old images" is default and in most cases should perfectly fit your goals:
  <img src="/pics/ddb-streams.png" alt="screenshot" title="screenshot"  height="400" />
