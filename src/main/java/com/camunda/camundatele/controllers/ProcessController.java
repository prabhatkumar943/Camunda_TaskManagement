package com.camunda.camundatele.controllers;

//import com.camunda.camundatele.utils.BpmnDeployer;
import com.camunda.camundatele.dtos.TaskDto;
//import com.camunda.camundatele.entities.UserTask;
import com.camunda.camundatele.service.TasklistService;
import com.camunda.camundatele.config.CamundaClientConfig;
//import com.camunda.camundatele.service.UserTaskService;
import com.camunda.camundatele.utils.ProcessProperties;
//import io.camunda.client.CamundaClient;

import com.camunda.camundatele.worker.ServiceJobWorker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.ProcessInstanceEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProcessController {
    Logger logger = LoggerFactory.getLogger(ProcessController.class);
    private String custDependecy;
    private CamundaClient camundaClient;
    //private UserTaskService userTaskService;
    private final ModelMapper modelMapper;
    private final ServiceJobWorker serviceJobWorker;
    @Autowired
    private TasklistService tasklistService;
    //@Autowired
    //private CamundaClientConfig camundaClient;
    /*public ProcessController(CamundaClientConfig camundaClient) {
        this.camundaClient = camundaClient;
    }*/

    public ProcessController( ModelMapper modelMapper, ServiceJobWorker serviceJobWorker)
    {
//        this.userTaskService=userTaskService;
        this.modelMapper=modelMapper;
        this.serviceJobWorker= serviceJobWorker;
    }
    @Autowired
    private ProcessProperties processProperties;


    @PostMapping("/start-process")
    public ResponseEntity<Map<String,Object>> startProcess(@RequestBody Map<String,Object> variables) {
        try {
            logger.info("Received variables in startProcess method: " + variables);
            ProcessInstanceEvent event=null;
            Long instanceKey;
            Long userTaskKey=null;
            Long processDefinitionKey=null;
            ResponseEntity<Mono<Map>> task=null;
            try {

                if(processProperties.getId() == null || processProperties.getId().isEmpty()) {
                    logger.error("❌ Failed to deploy BPMN process: " + "processId is null or empty");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("msg","Failure", "❌ Failed to deploy BPMN process ", "processId is null or empty"));
                }else if(processProperties.getId().equals("backend-telecaller")){
                    //custDependecy=variables.get("custDependency").toString();
                    //variables.put("businessKey", UUID.randomUUID().toString());
                    //if(Integer.parseInt(custDependecy)>0) {
                        camundaClient = new CamundaClientConfig().camundaClient();

                     /*   camundaClient.newDeployResourceCommand()
                                //.addResourceFile("E:\\Java\\backend-telecaller.bpmn")
                                //.addResourceFile()
                                .addResourceFromClasspath("bpmn/backend-telecaller.bpmn")
                                .execute();*/

                         event = camundaClient.newCreateInstanceCommand()

                                 .bpmnProcessId(processProperties.getId())   // must match the ID in your BPMN XML
                                //.re

                                .latestVersion()

                                 .variables(variables)

                                .send()
                                .join();


                        instanceKey=event.getProcessInstanceKey();
                        /*List<io.camunda.client.api.search.response.UserTask> tasks= camundaClient.newUserTaskSearchRequest()
                                .filter(f -> f
                                        .processInstanceKey(instanceKey))


                                        //.state("CREATED"))   // Only active tasks
                                .send()
                                .join()
                                .items();
                        userTaskKey= tasks.get(0).getUserTaskKey();
                        processDefinitionKey=tasks.get(0).getProcessDefinitionKey();
                        */
                        //Thread.sleep(5000);
                        //userTaskKey=this.serviceJobWorker.uTaskKey;
                        //processDefinitionKey=this.serviceJobWorker.processDefinitionKey;

                        //this.serviceJobWorker.handlTaskPersistListener()

                    //setUserTask(instanceKey);
        /*        zeebeClient.newDeployResourceCommand()
                .addResourceFromClasspath(bpmnFile)
                .send()
                .join();*/
                    /*}
                    else{
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("msg","Failure", "msgDesc",  "custDependency is null or empty"));
                    }*/
                   // Thread.sleep(500);
                 //task=    this.tasklistService.taskFilter(Map.of("processInstanceKey", String.valueOf(event.getProcessInstanceKey())));
                    //task= filterTask(Map.of("processInstanceKey", String.valueOf(event.getProcessInstanceKey())));
                 //logger.info("task created when process instance is called"+ task);
                }
                else{
                    camundaClient = new CamundaClientConfig().camundaClient();
                     event=  camundaClient.newCreateInstanceCommand()
                            .bpmnProcessId("backend-telecaller")   // must match the ID in your BPMN XML
                            //.re
                            .latestVersion()
                            .variables(variables)
                            .send()
                            .join();

                     instanceKey=event.getProcessInstanceKey();

        /*        zeebeClient.newDeployResourceCommand()
                .addResourceFromClasspath(bpmnFile)
                .send()
                .join();*/
                }


                logger.info("✅  BPMN process started with process instance key: " + instanceKey );
                return ResponseEntity.status(HttpStatus.OK).body( Map.of("msg","Success","processInstanceKey",instanceKey ));
                //"✅ Deployed BPMN process: " ;
            }
            catch (Exception e) {
                logger.error("❌ Failed to deploy BPMN process: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("msg","Failure", "❌ Failed to deploy BPMN process ", e.getMessage()));
                        //"❌ Failed to deploy BPMN process: " + e.getMessage();
            }

        }
        catch (Exception e) {
            logger.error("Failed to start process: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("msg","Failure", "❌ Failed to deploy BPMN process ", e.getMessage()));
        }
    }

    private void setUserTask(long processInstanceKey) {
        logger.info("set user task method ");
       // UserTask userTask= new UserTask();
        try {
            Thread.sleep(200);
            List<List<TaskDto>> task = tasklistService.fetchTaskByProcessInstanceKey(String.valueOf(processInstanceKey));

            //Flux<List<TaskDto>> taskBody= task.getBody();
            TaskDto taskDto = task.
                    //collectList().block().
                            stream().flatMap(List::stream)

                    .findFirst()
                    .orElse(null);

          /*  userTask.setId(taskDto.getId());
            userTask.setTaskName(taskDto.getName());
            userTask.setTaskProcessName(taskDto.getProcessName());
            userTask.setCandidateUsers(taskDto.getCandidateUsers());
            userTask.setCandidateGroups(taskDto.getCandidateGroups());
            userTask.setAgentName(taskDto.getAssignee());
            userTask.setTaskCreationDt(taskDto.getCreationDate());
            userTask.setTaskCompletionDt(taskDto.getCompletionDate());*/

            //  fluxTask.stream().forEach(taskDtos -> taskDtos.get());
        /*Mono<UserTask> userTaskMono= taskBody.map(taskDto -> {
            userTask.setTaskId(String.valueOf(taskDto.getId()));
            userTask.setTaskName(taskDto.getName());
            userTask.setTaskProcessName(taskDto.getProcessName());
            userTask.setCandidateUsers(taskDto.getCandidateUsers());
            userTask.setCandidateGroups(taskDto.getCandidateGroups());
            userTask.setTaskAssignee(taskDto.getAssignee());
            userTask.setTaskCreationDt(taskDto.getCreationDate());
            userTask.setTaskCompletionDt(taskDto.getCompletionDate());

             return userTask;
        });

        UserTask task2= userTaskMono.block();*/
//        UserTaskDto task2 = this.modelMapper.map(taskDto, UserTaskDto.class);
            //this.userTaskService.createUserTask(userTask);
            logger.info("user task service saved in db successfully ");
        } catch (Exception e) {
            throw new RuntimeException("Task not saved in system");
        }
        }

    @GetMapping(value= "/tasks", params="processName")
    public ResponseEntity<Mono<List<List<Map<String, Object>>>>> getTasks(@RequestParam String processName, HttpServletRequest request) {
        try{
            logger.info("Received processName in getTasks method: " + processName);
            Flux<List<Map<String, Object>>> tasks = tasklistService.fetchTasks(processName, request);
            logger.info("Received tasks in getTasks method: " + tasks);
            return ResponseEntity.ok().body(tasks.collectList());
        }
        catch (Exception e){
            //throw new RuntimeException("Failed to fetch tasks",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value= "/tasks/{taskId}")
    public ResponseEntity<Mono<List<List<Map<String, Object>>>>> getTask(@PathVariable String taskId, HttpServletRequest request) {
        try{
            Flux<List<Map<String, Object>>> task = tasklistService.fetchTask(taskId, request);

            return ResponseEntity.ok().body(task.collectList());
        }
        catch (Exception e){
            //throw new RuntimeException("Failed to fetch tasks",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value= "/tasks/unassigned")
    public ResponseEntity<Mono<List<TaskDto>>> getTask(@RequestBody Map<String,Object> userRequest, HttpServletRequest request) {
        try{
            Mono<List<TaskDto>> unassignedTask = tasklistService.fetchFilteredTask(userRequest, request);


            return ResponseEntity.ok().body(unassignedTask);
        }
        catch (Exception e){
            //throw new RuntimeException("Failed to fetch tasks",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value= "/tasks/filter")
    public ResponseEntity<Mono<Map>> filterTask(@RequestBody Map<String,Object> userRequest) {
        try{

            Mono<Map> unassignedTask = tasklistService.taskFilter(userRequest);
            logger.info("unassignedTask "+ unassignedTask);

            return ResponseEntity.ok().body(unassignedTask);
        }
        catch (Exception e){
            //throw new RuntimeException("Failed to fetch tasks",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 1. Define a helper method for clarity and type safety
    private List<Map<String, Object>> forceToList(Object item) {
        ObjectMapper mapper = new ObjectMapper();
        // This tells Jackson: "I know you think this is a Map, but treat it as a List"
        return mapper.convertValue(item, new TypeReference<List<Map<String, Object>>>() {});
    }

    @GetMapping(value= "/tasks/getByProcessInstanceKey")
    public ResponseEntity<List<TaskDto>> getTaskByProcessInstanceId(@RequestBody String request) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.convertValue(request, Map.class);
            String processInstanceKey= map.get("processInstanceKey").toString();
            List<List<TaskDto>> tasklist = tasklistService.fetchTaskByProcessInstanceKey(processInstanceKey);

            List<TaskDto> task= tasklist.
                    //flatMapIterable(list->list)
                    //stream().filter(map1-> "processInstanceKey".equals(processInstanceKey))
                    // .next();
                            stream().flatMap(List::stream).filter(map1->map1.getProcessInstanceKey().equals(processInstanceKey)).toList();



            return ResponseEntity.ok().body(task);
        }
        catch (Exception e){
            //throw new RuntimeException("Failed to fetch tasks",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/tasks/{taskId}/complete")
    public ResponseEntity<Map<String,Object>> completeTask(@PathVariable String taskId,
                                               @RequestBody Map<String, Object> variables, HttpServletRequest request) {
//        try {
            ResponseEntity<Map<String,Object>> entity  =tasklistService.completeTask(taskId, variables, request);
            return entity;
  //      }
    ///    catch (Exception e){
            //throw new RuntimeException("Failed to complete task",e);
       //     return ResponseEntity.ok().body("Task " + taskId + " not completed successfully");
        //}
    }

    @PostMapping("/tasks/{taskId}/assign")
    public ResponseEntity<Map<String,Object>> assignTask(@PathVariable String taskId,
                                             @RequestBody Map<String, Object> variables, HttpServletRequest request) {
        //try {
            ResponseEntity<Map<String,Object>> entity =tasklistService.assignTask(taskId, variables, request);
            return entity ;
        //}
        //catch (Exception e){
          //  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Task "+ taskId +" not completed");
        //}
    }

    @PostMapping("/tasks/form-submit")
    public ResponseEntity<Map<String,String>> submitForm(@RequestBody Map<String,Object> data){
        logger.info("form data "+ data);
    return     tasklistService.sendFormDataToCamunda(data);

    }

    @PostMapping("/tasks/assignto")
    public ResponseEntity<Map<String,String>> taskAssignTo(@RequestBody Map<String,Object> data){
        logger.info("form data "+ data);
        return     tasklistService.taskAssignTo(data);

    }

    @PostMapping("/tasks/complete")
    public ResponseEntity<Map<String,String>> completeTask(@RequestBody Map<String,Object> data) throws JsonProcessingException {

        logger.info("form data "+ data);
        HashMap<String,Object> fetchUserTaskKey= new HashMap<>();
        String processInstanceKey= data.get("processInstanceKey").toString();
        fetchUserTaskKey.put("processInstanceKey",processInstanceKey);
        fetchUserTaskKey.put("state","CREATED");
        Mono<Map> task= tasklistService.taskFilter(fetchUserTaskKey);
      /*  ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(task.toString());
        JsonNode items = root.path("items");

        List<String> keys = new ArrayList<>();
        if (items.isArray()) {
            for (JsonNode item : items) {
                keys.add(item.path("userTaskKey").asText());
            }
        }*/
        String userTaskKey = task
                .map(response -> {
                    List<Map<String, Object>> items =
                            (List<Map<String, Object>>) response.get("items");

                    if (items != null && !items.isEmpty()) {
                        return (String) items.get(0).get("userTaskKey");
                    }
                    return null;
                })
                .block();
        data.put("userTaskKey",userTaskKey);

        return     tasklistService.completeTask(data);

    }

    @GetMapping("/tasks/filter/variable")
    public ResponseEntity<Map<String, List>> filterTaskClient(@RequestBody Map<String,Object> data){
        logger.info("form data "+ data);
        return     tasklistService.filterTaskClient(data);

    }

}
