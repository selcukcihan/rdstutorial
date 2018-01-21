## AWS RDS Sample Code
This is an example java restful web service application to demonstrate [aws rds](https://aws.amazon.com/rds) usage. It is fully functional and close to production quality. There is a single resource, books, clients can request /books for the collection and /books/1 for instance for the book with id 1. The books are, ofcourse, stored in RDS.

## How to run this?
In order to run this application, you need to [build it first](https://spring.io/guides/gs/gradle/). Tested on Gradle 4.4.1, you can run the application simply by issuing `./gradlew bootRun`.

## How to setup aws?
You need to have an [aws account](https://aws.amazon.com). Then you need to follow [these instructions](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html).