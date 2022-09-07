## How to run
execute the command `sh run.sh` from the base directory.
### Features to add
- Dockerize
- Integration tests
- Prometheus
- Logging
- Error Handler


Swagger: http://localhost:8080/swagger-ui.html

Prometheus: http://localhost:9090


For some strange reason GET request from the swagger is not working so try the curl command below: curl -X GET --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ "policyId": "POLICY_ID", "startDate": "dd.MM.YYYY"}' 'http://localhost:8080/'


TODO: 
1. Write test cases for all the methods in the controller.
