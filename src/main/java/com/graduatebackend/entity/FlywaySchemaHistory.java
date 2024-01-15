package com.graduatebackend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "flyway_schema_history")
public class FlywaySchemaHistory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "installed_rank")
    private int installedRank;
    @Basic
    @Column(name = "version")
    private String version;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "script")
    private String script;
    @Basic
    @Column(name = "checksum")
    private Integer checksum;
    @Basic
    @Column(name = "installed_by")
    private String installedBy;
    @Basic
    @Column(name = "installed_on")
    private Timestamp installedOn;
    @Basic
    @Column(name = "execution_time")
    private int executionTime;
    @Basic
    @Column(name = "success")
    private boolean success;

    public int getInstalledRank() {
        return installedRank;
    }

    public void setInstalledRank(int installedRank) {
        this.installedRank = installedRank;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Integer getChecksum() {
        return checksum;
    }

    public void setChecksum(Integer checksum) {
        this.checksum = checksum;
    }

    public String getInstalledBy() {
        return installedBy;
    }

    public void setInstalledBy(String installedBy) {
        this.installedBy = installedBy;
    }

    public Timestamp getInstalledOn() {
        return installedOn;
    }

    public void setInstalledOn(Timestamp installedOn) {
        this.installedOn = installedOn;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FlywaySchemaHistory that = (FlywaySchemaHistory) o;
        return installedRank == that.installedRank && executionTime == that.executionTime && success == that.success && Objects.equals(version, that.version) && Objects.equals(description, that.description) && Objects.equals(type, that.type) && Objects.equals(script, that.script) && Objects.equals(checksum, that.checksum) && Objects.equals(installedBy, that.installedBy) && Objects.equals(installedOn, that.installedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(installedRank, version, description, type, script, checksum, installedBy, installedOn, executionTime, success);
    }
}
