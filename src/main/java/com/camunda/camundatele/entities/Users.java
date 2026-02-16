/*
package com.camunda.camundatele.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Users-Camunda")
public class Users {
    @Id
//    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name= "strAgentCode")
    private String agentCode;

    @Column(name= "strAgentName")
    private String agentName;
    @Column(name= "strAgentDesgn")
    private String agentDesgn;
    @Column(name= "strSupvCode")
    private String agentSupvCode;
    @Column(name= "strSupvName")
    private String agentSupvName;
    @Column(name= "nAgentStatus")
    private int agentStatus;
    @Column(name= "dtCreated")
    private LocalDateTime dtcreated = LocalDateTime.now();
    @Column(name= "strUpdatedBy")
    private String updatedBy;
    @Column(name= "user_Roles")
    private String roles;
    @Column(name= "user_group")
    private String group;

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentDesgn() {
        return agentDesgn;
    }

    public void setAgentDesgn(String agentDesgn) {
        this.agentDesgn = agentDesgn;
    }

    public String getAgentSupvCode() {
        return agentSupvCode;
    }

    public void setAgentSupvCode(String agentSupvCode) {
        this.agentSupvCode = agentSupvCode;
    }

    public String getAgentSupvName() {
        return agentSupvName;
    }

    public void setAgentSupvName(String agentSupvName) {
        this.agentSupvName = agentSupvName;
    }

    public int getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(int agentStatus) {
        this.agentStatus = agentStatus;
    }

    public LocalDateTime getDtcreated() {
        return dtcreated;
    }

    public void setDtcreated(LocalDateTime dtcreated) {
        this.dtcreated = dtcreated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
*/
