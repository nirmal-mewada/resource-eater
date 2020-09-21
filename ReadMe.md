Application to create dummy load on system, I used it for testing AWS ecs autoscaling
##Build Image
 > `mvn clean package`
>
 >`docker build -t nirmalsuthar/resource_eater .`


##Run Docker Image

>`docker run -it  -e JAVA_OPTS='-DSERVICE_NAME=service1' --rm -p 8081:8080 --name resource_eater_cnt  nirmalsuthar/resource_eater`

##Endpoints
###Actuator/Info
>`http://localhost:8080/api/v1/service1/actuator`
> `http://localhost:8080/api/v1/service1/info`

### Eating Memory
Each iteration increase memory usage by  1 MB. 
>`http://localhost:8080/api/v1/service1/memory?iteration=1000&pause=0`

### Eating CPU
>`http://localhost:8080/api/v1/service1/cpu/iteration/sync?iteration=99999999`
>
>`http://localhost:8080/api/v1/service1/cpu/duration/async?load=0.8&duration=3000`

### Suicide
>`http://localhost:8080/api/v1/service1/exit?code=-1`

Note : If JAVA_OPTS is not orivided then `service1` will become `unknown`
