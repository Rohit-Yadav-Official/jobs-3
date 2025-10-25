package com.rohit.jobs.job.external;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "applicationDetail")
public class applicationDetails {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;
    public Long applicationId;
    String applicantName;
    @Temporal(TemporalType.DATE)
    private Date applicationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }
}
