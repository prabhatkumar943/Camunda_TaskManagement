package com.camunda.camundatele.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
//import jakarta.persistence.*;

import java.sql.Timestamp;

public class UserTaskDto {

    @JsonAlias("id")
    private String taskId;


            private String strAgentCode;


        private String taskAssignee;


        private String taskState;


        private String taskName;


        private String taskProcessName;


        private Timestamp taskCreationDt;

        private Timestamp taskCompletionDt;


        private String candidateGroups;


        private String candidateUsers;


        private String processInstanceKey;



        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getStrAgentCode() {
            return strAgentCode;
        }

        public void setStrAgentCode(String strAgentCode) {
            this.strAgentCode = strAgentCode;
        }

        public String getTaskAssignee() {
            return taskAssignee;
        }

        public void setTaskAssignee(String taskAssignee) {
            this.taskAssignee = taskAssignee;
        }

        public String getTaskState() {
            return taskState;
        }

        public void setTaskState(String taskState) {
            this.taskState = taskState;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskProcessName() {
            return taskProcessName;
        }

        public void setTaskProcessName(String taskProcessName) {
            this.taskProcessName = taskProcessName;
        }

        public Timestamp getTaskCreationDt() {
            return taskCreationDt;
        }

        public void setTaskCreationDt(Timestamp taskCreationDt) {
            this.taskCreationDt = taskCreationDt;
        }

        public Timestamp getTaskCompletionDt() {
            return taskCompletionDt;
        }

        public void setTaskCompletionDt(Timestamp taskCompletionDt) {
            this.taskCompletionDt = taskCompletionDt;
        }

        public String getCandidateGroups() {
            return candidateGroups;
        }

        public void setCandidateGroups(String candidateGroups) {
            this.candidateGroups = candidateGroups;
        }

        public String getCandidateUsers() {
            return candidateUsers;
        }

        public void setCandidateUsers(String candidateUsers) {
            this.candidateUsers = candidateUsers;
        }

        public String getProcessInstanceKey() {
            return processInstanceKey;
        }

        public void setProcessInstanceKey(String processInstanceKey) {
            this.processInstanceKey = processInstanceKey;
        }
}
