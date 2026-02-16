/*
package com.camunda.camundatele.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="user-task")
public class UserTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqno;

    @Column(name="taskId")
    private String id;

    @Column(name="agentCode")
    private String agentCode;

    @Column(name="agentName")
    private String agentName;

    @Column(name="taskState")
    private String taskState;

    @Column(name="taskName")
    private String taskName;

    @Column(name="taskProcessName")
    private String taskProcessName;

    @Column(name="taskCreationDt")
    private Timestamp taskCreationDt;

    @Column(name="taskCompletionDt")
    private Timestamp taskCompletionDt;

    @Column(name="candidateGroups")
    private String candidateGroups;

    @Column(name="candidateUsers")
    private String candidateUsers;

    @Column(name="processInstanceKey")
    private String processInstanceKey;

    @Column(name="businessKey")
    private String businessKey;

    public Long getSeqno() {
        return seqno;
    }

    public void setSeqno(Long seqno) {
        this.seqno = seqno;
    }

    public String getId() {
        return id;
    }

    public void setId(String taskId) {
        this.id = taskId;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setStrAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}
*/
