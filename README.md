# spring-batch
Spring Batch Udemy Course: Batch Processing with Spring Batch & Spring Boot
https://www.udemy.com/course/data-batch-processing-with-spring-batch-spring-boot-spring-framework/

Item branch pushed has different item readers code.

**14. What is Job Instance, Job Execution & Job Execution Context?**
https://www.udemy.com/course/data-batch-processing-with-spring-batch-spring-boot-spring-framework/learn/lecture/29563416#overview

Job -> Job Instance (for each execution) -> Job Execution (execution results and details 
(metadata) are stored on the database).

If execution fails, Spring Batch will try to run it again.

**Job Execution Context** (Map). All steps belonging to the job can access the key-value data 
stored in this context.

**15. What is Step Execution & Step Execution Context?**
Step -> Step Execution -> Step Execution Context (Map)

**16. Configure MYSQL with Spring Boot.**
SpringBatch uses an embedded schema. It is located on spring.batch.core library.

**18. Setting Job parameter value.** 
Set as run arguments. 