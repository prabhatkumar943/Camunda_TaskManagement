package com.camunda.camundatele.worker;

import com.camunda.camundatele.config.CamundaClientConfig;
//import com.camunda.camundatele.entities.UserTask;
//import com.camunda.camundatele.service.RedisService;
//import com.camunda.camundatele.service.TasklistService;
//import com.camunda.camundatele.service.UserTaskService;
import io.camunda.client.CamundaClient;
import io.camunda.client.api.command.JobResultCorrections;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
//import io.camunda.spring.boot.starter.annotation.JobWorker;
//import io.camunda.zeebe.client.api.response.ActivatedJob;
//import io.camunda.zeebe.client.api.worker.JobClient;

import io.camunda.client.api.worker.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ServiceJobWorker {

    Logger logger = LoggerFactory.getLogger(JobWorker.class);

//    private final UserTaskService userTaskService;
//    public Long processDefinitionKey=null;
  //  public Long uTaskKey=null;
    //private final RedisService redisService;
    public ServiceJobWorker(){
        //this.userTaskService=userTaskService;
//        this.redisService=redisService;
    }
    /*private final CamundaClient camundaClient;
    public MyJobWorker(CamundaClient camundaClient) {
        this.camundaClient = camundaClient;
    }*/


/*    public void handleJob(final JobClient client, final ActivatedJob job) {
        System.out.println("Received job: " + job.getVariables());
        client.newCompleteCommand(job.getKey())
                .variable("paymentStatus","SUCCESS")
                .send().join();
    }*/

/*    @Override
    public void run() {

        try {
            List<ActivatedJob> jobs =
                    camundaClient.newActivateJobsCommand()
                            .jobType("log-task")
                            .maxJobsToActivate(5)
                            .workerName("manual-log-worker")
                            .timeout(Duration.ofSeconds(30))
                            .send()
                            .join()
                            .getJobs();

            for (ActivatedJob job : jobs) {
                logger.info("Job activated: " + job.getKey());
                logger.info("Variables: " + job.getVariables());

                // business logic here

                camundaClient.newCompleteCommand(job.getKey())
                        .send()
                        .join();
            }

            Thread.sleep(1000); // polling interval

        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {}
        }
    }*/


    @PostConstruct
    public void startWorker() {

        CamundaClient client = new CamundaClientConfig().camundaClient();
                /*CamundaClient.newClientBuilder()
                .grpcAddress(URI.create("http://localhost:26500"))
                .restAddress(URI.create("http://localhost:8088"))
                //.usePlaintext()
                .build();*/

        client.newWorker()
                .jobType("check-dependency")
                .handler(this::handle)
                .open();

        client.newWorker()
                .jobType("check-finalDependency")
                .handler(this::handleFinalDependency)
                .open();

        client.newWorker()
                .jobType("task-persist-listener")
                .handler(this::handlTaskPersistListener)
                .open();

        client.newWorker()
                .jobType("send-notification")
                .handler(this::handleSendNotification)
                .open();

/*        client.newWorker()
                .jobType("assign-task-telecaller")
                .handler(this::handlAssignTaskTelecaller)
                .open();
*/

    }


    private void handle(JobClient jobClient, ActivatedJob job) {
        logger.info("Job activated: " + job.getKey());
        logger.info("Variables: " + job.getVariables());

  int custDependecy=  (int) job.getVariablesAsMap().get("custDependency");
        jobClient.newCompleteCommand(job.getKey()).
                variable("custDependecy",custDependecy)
                .send().join();
    }

    private void handleSendNotification(JobClient jobClient, ActivatedJob job) {
        logger.info("Job activated: " + job.getKey());
        logger.info("Variables: " + job.getVariables());

       // int custDependecy=  (int) job.getVariablesAsMap().get("custDependency");
        jobClient.newCompleteCommand(job.getKey()).
                variable("custDependecy","Notification sent")
                .send().join();
    }


    private void handleFinalDependency(JobClient jobClient, ActivatedJob job) {
        logger.info("Job activated: " + job.getKey());
        logger.info("Variables: " + job.getVariables());

        int custDependecy=  (int) job.getVariablesAsMap().get("finalDependency");
        logger.info("Final depenency value received "+ custDependecy);
        jobClient.newCompleteCommand(job.getKey()).
                variable("finalDependency",custDependecy)
                .send().join();
    }

    public void handlTaskPersistListener(JobClient jobClient, ActivatedJob job){
        logger.info("handlTaskPersistListener is called to persist user task");
        //String userTaskKey =job.getCustomHeaders().get("io.camunda.zeebe:userTaskKey");
        //logger.info("userTaskKey"+ userTaskKey);
        Map<String, Object> variables= job.getVariablesAsMap();
        //String processInstanceKey =variables.get("processInstanceKey").toString();
      //  String businessKey =variables.get("businessKey").toString();

       // UserTask userTask= new UserTask();

        Long userTaskKey= job.getUserTask().getUserTaskKey();
        List<String> candidateUser= job.getUserTask().getCandidateUsers();
        //uTaskKey=userTaskKey;
        //processDefinitionKey=job.getProcessDefinitionKey();
       // this.redisService.saveWithMapping(businessKey,userTaskKey );
        logger.info("Candidate users"+ candidateUser);
       logger.info("usertask key"+ userTaskKey );
       /*userTask.setId(String.valueOf(userTaskKey));
       userTask.setProcessInstanceKey(String.valueOf(job.getProcessInstanceKey()));
       userTask.setCandidateGroups(!job.getUserTask().getCandidateGroups().isEmpty()?job.getUserTask().getCandidateGroups().get(0):null);
       userTask.setAgentName(job.getUserTask().getAssignee());
       userTask.setCandidateUsers(!job.getUserTask().getCandidateUsers().isEmpty()?job.getUserTask().getCandidateUsers().get(0):null);
        userTask.setTaskProcessName(job.getBpmnProcessId());
        userTask.setTaskCreationDt(new Timestamp(System.currentTimeMillis()));
       userTask.setTaskState("Created");
       userTask.setBusinessKey(businessKey);*/
       //this.userTaskService.createUserTask(userTask);


        //JobResultCorrections corrections= new JobResultCorrections();

        jobClient.newCompleteCommand(job.getKey()).send().join();

      /*  jobClient.newCompleteCommand(job.getKey()).
                withResult(a->a.forUserTask().correctAssignee(variables.get("userid").toString())
                        )
                //.variables(variables)
                .send().join();
*/

    }

    private void handlAssignTaskTelecaller(JobClient jobClient, ActivatedJob job){
        logger.info("handlAssignTaskTelecaller is called");
        //HashMap<String,Object> variables= new HashMap<>();
        //variables.put("assignee","rakesh");
        jobClient.newCompleteCommand(job.getKey()).
               withResult(a->a.forUserTask().correctAssignee("test"))
                .send().join();
        logger.info("task assigned to rakesh");
    }


}
