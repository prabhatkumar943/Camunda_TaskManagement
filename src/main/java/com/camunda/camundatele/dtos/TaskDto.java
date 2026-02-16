package com.camunda.camundatele.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDto {

    private String id;
    private String name;
    private String taskDefinitionId;
    private String processName;
    private Timestamp creationDate;
    private Timestamp completionDate;
    private String assignee;
    private String taskState;
    private ArrayList sortValues;
    private boolean isFirst;
    private String formKey;
    private String formId;
    private String formVersion;
    private String isFormEmbedded;
    private String processDefinitionKey;
    private String processInstanceKey;
    private String tenantId;
    private String dueDate;
    private String followUpDate;
    private ArrayList candidateGroups;
    private ArrayList candidateUsers;
    private ArrayList variables;
    private String context;
    private String implementation;
    private String priority;
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskDefinitionId() {
        return taskDefinitionId;
    }

    public void setTaskDefinitionId(String taskDefinitionId) {
        this.taskDefinitionId = taskDefinitionId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Timestamp completionDate) {
        this.completionDate = completionDate;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public ArrayList getSortValues() {
        return sortValues;
    }

    public void setSortValues(ArrayList sortValues) {
        this.sortValues = sortValues;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(String formVersion) {
        this.formVersion = formVersion;
    }

    public String getIsFormEmbedded() {
        return isFormEmbedded;
    }

    public void setIsFormEmbedded(String isFormEmbedded) {
        this.isFormEmbedded = isFormEmbedded;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessInstanceKey() {
        return processInstanceKey;
    }

    public void setProcessInstanceKey(String processInstanceKey) {
        this.processInstanceKey = processInstanceKey;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public ArrayList getCandidateGroups() {
        return candidateGroups;
    }

    public void setCandidateGroups(ArrayList candidateGroups) {
        this.candidateGroups = candidateGroups;
    }

    public ArrayList getCandidateUsers() {
        return candidateUsers;
    }

    public void setCandidateUsers(ArrayList candidateUsers) {
        this.candidateUsers = candidateUsers;
    }

    public ArrayList getVariables() {
        return variables;
    }

    public void setVariables(ArrayList variables) {
        this.variables = variables;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
