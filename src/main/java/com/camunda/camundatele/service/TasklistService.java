package com.camunda.camundatele.service;

import com.camunda.camundatele.config.CamundaClientConfig;
import com.camunda.camundatele.dtos.TaskDto;
//import com.camunda.camundatele.entities.FormData;
//import com.camunda.camundatele.entities.UserTask;
import com.camunda.camundatele.exception.BadRequestException;
import com.camunda.camundatele.exception.ForBiddenException;
import com.camunda.camundatele.exception.ResourceNotFoundException;
//import com.camunda.camundatele.repository.UserFormDataRepository;
//import com.camunda.camundatele.repository.UserTaskRepository;
import com.camunda.camundatele.utils.ProcessProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.search.filter.VariableFilter;
import io.camunda.client.api.search.request.TypedFilterableRequest;
import io.camunda.client.api.search.request.VariableSearchRequest;

import io.camunda.client.api.search.response.SearchResponse;
import io.camunda.client.impl.search.filter.UserTaskFilterImpl;
import io.camunda.client.impl.search.filter.VariableFilterMapper;
import io.camunda.client.impl.search.response.SearchResponseMapper;
import io.camunda.client.protocol.rest.UserTaskFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TasklistService {

    private final ModelMapper modelMapper;
    private final WebClient webClient;
    //@Autowired
    //private  CamundaClientConfig camundaClientConfig;
    private final CamundaClient camundaClient;
   // private final String taslkListUrl="http://localhost:8088";
    private final ProcessProperties properties;
    //private final TasklistWebClient tasklistWebClient;
    //private final AuthController tokenProvider;

//    private final UserFormDataRepository userFormDataRepository;
  //  private final UserTaskRepository userTaskRepository;
    Logger logger = LoggerFactory.getLogger(TasklistService.class);


    @Autowired
    public TasklistService(WebClient webClient, ModelMapper modelMapper, CamundaClient camundaClient, ProcessProperties properties) {
        this.webClient=webClient;
       // this.userFormDataRepository=userFormDataRepository;
        this.modelMapper=modelMapper;
        this.camundaClient=camundaClient;
        this.properties=properties;
        //this.userTaskRepository=userTaskRepository;

    }


    public Flux<List<Map<String, Object>>> fetchTasks(String processName, HttpServletRequest request) {

        String token = null;
        String header=null;
        token=fetchToken();
        return
                webClient.post()
                        .uri(properties.getCAMUNDA_REST_ADDRESS()+ "/v1/tasks/search")
                        .header("Authorization", token != null ? "Bearer " + token : "")
                        .bodyValue(Map.of("processName", processName))
                        .retrieve()
                        //.bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                        .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                        .flux();
        //.collectList();
        //         .block();
    }

    public Flux<List<Map<String, Object>>> fetchTask(String taskId, HttpServletRequest request) {

        String token = null;
        token=fetchToken();
        return
                webClient.post()
                        .uri(properties.getCAMUNDA_REST_ADDRESS()+ "/v1/tasks/search")
                        .header("Authorization", token != null ? "Bearer " + token : "")
                        .bodyValue(Map.of("id", taskId))
                        .retrieve()
                        //.bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                        .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                        .flux();
        //.collectList();
        //         .block();
    }

/*    public ResponseEntity<Map<String,Object>> completeTask(String taskId, Map<String, Object> variables, HttpServletRequest request) {
        logger.info("complete task is called with varilables" + variables);
        String token = null;
        token=fetchAssignAndCompleteTask(variables.get("username").toString(),variables.get("password").toString());
        try {

            variables.remove("username");
            variables.remove("password");
            HashMap<String,Object> body=new HashMap<>();
            body.put("Variables", variables);
            logger.info("variables after reoving username and password from request "+ variables);
               Map<String, Object> response=   webClient.patch()
                    .uri(taslkListUrl+ "/v1/tasks/{id}/complete",taskId)
                    .header("Authorization", token != null ? "Bearer " + token : "")
                    .bodyValue( variables)
                    .retrieve()
                    //.toBodilessEntity();
                       .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                           if(clientResponse.statusCode()==HttpStatus.NOT_FOUND){
                               return Mono.error(new RuntimeException("Task not found"));
                           }else if(clientResponse.statusCode()==HttpStatus.BAD_REQUEST){
                               return Mono.error(new RuntimeException("Bad Request"));
                           }else if(clientResponse.statusCode()==HttpStatus.UNAUTHORIZED || clientResponse.statusCode()==HttpStatus.FORBIDDEN){
                               return Mono.error(new RuntimeException("You are not authorized to complete this task"));
                           }
                           else{
                               return Mono.error(new RuntimeException("Task not assigned"));
                           }
                       })
                       .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                     .block();
            logger.info("Task "+ taskId +" completed ");
            return ResponseEntity.status(200).body(Map.of("msg","Success","payload",response));
        }
        catch (Exception e) {
            logger.error("Failed to complete task for TaskList API: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of("msg","Failure","payload",null));
            //throw new RuntimeException("Task not completed", e);
        }
    }

    public ResponseEntity<Map<String, Object>> assignTask(String taskId, Map<String, Object> variables, HttpServletRequest request){
        logger.info("assign task is called with varilables" + variables);
        String token=null;
        token=fetchAssignAndCompleteTask(variables.get("username").toString(),variables.get("password").toString());
        variables.remove("username");
        variables.remove("password");
        logger.info("variables after reoving username and password from request "+variables);
        HashMap<String,Object> body=new HashMap<>();
        if(variables.containsKey("assignee")) {
            body.put("assignee", variables.get("assignee"));
        } else if (variables.containsKey("candidateGroups")) {
            body.put("candidateGroups", variables.get("candidateGroups"));
        } else if(variables.containsKey("candidateUsers")){
            body.put("candidateUsers", variables.get("candidateUsers"));
        }
        logger.info("body of request "+body);
        try {

           Map<String, Object> response= webClient.patch()
                    .uri(taslkListUrl+ "/v1/tasks/{id}/assign",taskId)
                    .header("Authorization", token != null ? "Bearer " + token : "")
                    .bodyValue(body)
                    .retrieve()
                    // .toBodilessEntity()
                    //.bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {
                   .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    if(clientResponse.statusCode()==HttpStatus.NOT_FOUND){
                        return Mono.error(new RuntimeException("Task not found"));
                    }else if(clientResponse.statusCode()==HttpStatus.BAD_REQUEST){
                        return Mono.error(new RuntimeException("Bad Request"));
                    }else if(clientResponse.statusCode()==HttpStatus.UNAUTHORIZED || clientResponse.statusCode()==HttpStatus.FORBIDDEN){
                        return Mono.error(new RuntimeException("You are not authorized to assign this task"));
                    }
                    else{
                        return Mono.error(new RuntimeException("Task not assigned"));
                    }
                   })
                   .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                   .block();
            //.collectList()
            //.block();
            logger.info("Task "+ taskId +" assigned ");
            return ResponseEntity.status(200).body(Map.of("msg","Success","payload",response));
        } catch (RuntimeException e) {
            //throw new RuntimeException("Failed to assign the task",e);
                    return ResponseEntity.status(500).body(Map.of("msg","Failure","payload",null));
        }
    }*/

    public List<List<TaskDto>> fetchTaskByProcessInstanceKey(String processInstanceKey) {
        logger.info("fetchTaskByProcessInstanceId is called with request " + processInstanceKey);
        String token = null;
        token=fetchToken();
        try {
            return
                    webClient.post()
                            .uri(properties.getCAMUNDA_REST_ADDRESS() + "/v2/user-tasks/search")
                            .header("Authorization", token != null ? "Bearer " + token : "")
                          //  .bodyValue(Map.of("processInstanceKey", processInstanceKey))
                            .bodyValue(Map.of(
                                    "filter", Map.of(
                                            "processInstanceKey",processInstanceKey
                                    )
                            ))
                            .retrieve()
                            //.bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                            .bodyToMono(new ParameterizedTypeReference<List<TaskDto>>() {
                            })
                            .retryWhen(Retry.fixedDelay(5, Duration.ofMillis(500)))

                            .flux().collectList().block();
        } catch (Exception e) {
            logger.error("Failed to fetch task by process instance key for TaskList API: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Mono<List<TaskDto>> fetchFilteredTask(Map<String, Object> userRequest, HttpServletRequest request) {
        logger.info("fetchUnassignedTask is called with request " + userRequest);
        String token = null;
        try {
            token = fetchToken();

            return
                    webClient.post()
                            .uri(properties.getCAMUNDA_REST_ADDRESS() + "/v1/tasks/search")
                            .header("Authorization", token != null ? "Bearer " + token : "")
                            .bodyValue(userRequest)
                            .retrieve()
                            //.bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                            .bodyToMono(new ParameterizedTypeReference<List<TaskDto>>() {
                            });
                            //.flux();
                            //.map(response -> response.g);
        } catch (Exception e) {
            logger.error("Failed to fetch unassigned task for TaskList API: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Mono<Map> taskFilter(Map<String, Object> userRequest) {
        logger.info("fetchUnassignedTask is called with request " + userRequest);
        String token = null;
        try {
            token = fetchToken();

            return
                    webClient.post()
                            .uri(properties.getCAMUNDA_REST_ADDRESS() + "/v2/user-tasks/search")
                            .header("Authorization", token != null ? "Bearer " + token : "")
                            .bodyValue(Map.of("filter", userRequest))
                            .retrieve()
                            .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                                logger.info("API Response Code "+ clientResponse.statusCode());
                                if (clientResponse.statusCode() == HttpStatus.BAD_REQUEST) {
                                    return Mono.error(new BadRequestException("Bad Request"));
                                } else if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                                    return Mono.error(new ResourceNotFoundException("Resource Not Found"));
                                } else if (clientResponse.statusCode() == HttpStatus.FORBIDDEN) {
                                    return Mono.error(new ForBiddenException("Forbidden Request"));
                                }
                                return Mono.empty();
                            })


                            //.bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                            //.bodyToMono(new ParameterizedTypeReference<List<Map<String,Object>>>() {
                            //.bodyToMono(new ParameterizedTypeReference<Map<String,Object>>() {
                            //});
                            .bodyToMono(Map.class); // Receive as a generic Map first
                            //.retryWhen(Retry.backoff(3, Duration.ofSeconds(2)));
            //.map(responseMap -> {
            // 'content' is a common name, check your API docs if it's 'tasks' or 'data'
            // return (List<Map<String, Object>>) responseMap.get("content");
        //});
            //.flux();
            //.map(response -> response.g);
        } catch (Exception e) {
            logger.error("Failed to fetch search task : {}", e.getMessage());
            throw e;
        }
    }

    public String fetchToken(){

        String token=null;

        Map<String, String> response =
                webClient.post()
                        .uri(properties.getCAMUNDA_AUTH_URL())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .bodyValue("grant_type=client_credentials" +
                                "&client_id=" +properties.getCAMUNDA_CLIENT_ID()+
                                "&client_secret="+properties.getCAMUNDA_CLIENT_SECRET()+
                                "&audience="+properties.getCAMUNDA_AUDIENCE())
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                        .block();

         token = response.get("access_token");

        return token;
    }

/*   public String fetchAssignAndCompleteTask(String userName,String password){

        String token=null;
        //String userName="test";
        //String password="test";

        Map<String, String> response =
                webClient.post()
                        .uri("http://localhost:18080/auth/realms/camunda-platform/protocol/openid-connect/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .bodyValue("grant_type=password" +
                                "&client_id=camunda-tasklist" +
                                "&username=" +userName+
                                "&password="+password)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                        .block();

        token = response.get("access_token");

        return token;
    }*/


    public ResponseEntity<Map<String, String>> sendFormDataToCamunda(Map<String, Object> data)
    {
        try{
            //camundaClient=new CamundaClientConfig().camundaClient();
            camundaClient.newPublishMessageCommand()
                    .messageName(data.get("name").toString())
                    .correlationKey(data.get("businessKey").toString())
                    .variables(data.get("variables"))
                    .send()
                    .join();

           // Map variables to FormData
           Map<String, Object> variables = (Map<String, Object>) data.get("variables");
           //FormData form = modelMapper.map(variables, FormData.class);

//            userFormDataRepository.save(form);
           
           // Find and update existing form data
           /*this.userFormDataRepository.findByProcessInstanceKey(data.get("userTaskKey").toString())
               .ifPresentOrElse(
                   existingForm -> {
                       modelMapper.map(variables, existingForm);

                   },
                   () -> {
                       userFormDataRepository.save(form);
                       //throw new RuntimeException("Task Id not found in user form data to save " + data.get("userTaskKey"));
                   }
               );*/



            /*camundaClient.newAssignUserTaskCommand((Long.parseLong(data.get("userTaskKey").toString())))
                    .assignee(data.get("agentName").toString())
                            .send();

            camundaClient.newCompleteUserTaskCommand((Long.parseLong(data.get("userTaskKey").toString())))
                    .variables(data.get("variables")).send();*/
            return ResponseEntity.status(200).body(Map.of("mesg","success","desc","created"));
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(500).body(Map.of("mesg","failed","desc","not created"));
        }

    }

    public ResponseEntity<Map<String, String>> taskAssignTo(Map<String, Object> data)
    {
        try{
            //camundaClient=new CamundaClientConfig().camundaClient();
            camundaClient.newAssignUserTaskCommand((Long.parseLong(data.get("userTaskKey").toString())))
                    .assignee(data.get("agentName").toString())
                    .send();
            //camundaClient.close();
            /*this.userTaskRepository.findById(data.get("userTaskKey").toString())
                    .ifPresentOrElse(
                            existingForm->{
                                Map<String,Object> formData=(Map<String,Object>) data.get("variables");
                                modelMapper.map(formData, existingForm);
                                this.userTaskRepository.save(existingForm);
                            },
                            ()->{
                                throw new RuntimeException("Task id not found in user task repository");
                            }

                    );
    */
            return ResponseEntity.status(200).body(Map.of("mesg","success","desc","Assigned to "+data.get("agentName")));
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(500).body(Map.of("mesg","failed","desc","Not assigned to "+data.get("agentName")));
        }
    }

    public ResponseEntity<Map<String, String>> completeTask(Map<String, Object> data)
    {
        logger.info("complete task request "+data);
        try{
            //camundaClient=new CamundaClientConfig().camundaClient();
            camundaClient.newCompleteUserTaskCommand((Long.parseLong(data.get("userTaskKey").toString())))
                    .variables(data.get("variables")).send();

            //camundaClient.close();
     /*       this.userTaskRepository.findById(data.get("userTaskKey").toString())
                    .ifPresentOrElse(
                            existingForm->{
                                Map<String,Object> formData=(Map<String,Object>) data.get("variables");
                                modelMapper.map(formData, existingForm);
                                this.userTaskRepository.save(existingForm);
                            },
                            ()->{
                                throw new RuntimeException("Task id not found in user task repository");
                            }

                    );


            // Find and update existing form data
            this.userFormDataRepository.findByProcessInstanceKey(data.get("processInstanceKey").toString())
                    .ifPresentOrElse(
                            existingForm -> {
                                Map<String,Object> formData=(Map<String,Object>) data.get("variables");
                                //FormData form=this.modelMapper.map(formData, FormData.class);
                                modelMapper.map(formData, existingForm);
                                this.userFormDataRepository.save(existingForm);
                            },
                            () -> {
                                Map<String,Object> formData=(Map<String,Object>) data.get("variables");
                                FormData form=this.modelMapper.map(formData, FormData.class);
                                userFormDataRepository.save(form);
                                //throw new RuntimeException("Task Id not found in user form data to save " + data.get("userTaskKey"));
                            }
                    );*/

            return ResponseEntity.status(200).body(Map.of("mesg","success","desc","Assined Task "+data.get("userTaskKey") + " successfully completed"));
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(500).body(Map.of("mesg","failed","desc","Assigned Task "+data.get("userTaskKey") + " not completed"));
        }
    }

    public ResponseEntity<Map<String, List>> filterTaskClient(Map<String, Object> data)
    {
        try{
            //camundaClient=new CamundaClientConfig().camundaClient();

//            String jsonValue = String.format("\"%s\"", userId);

          List searchResponse=  camundaClient.newVariableSearchRequest().filter(f->f.name("createdBy").value("\"Sumit Kumar\""))
                    .send()
                    .join().items().stream().collect(Collectors.toUnmodifiableList());






            return ResponseEntity.status(200).body(Map.of("response",searchResponse));
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
