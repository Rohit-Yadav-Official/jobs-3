package com.rohit.jobs.job;


import com.rohit.jobs.job.dto.JobWithCompantDto;
import com.rohit.jobs.job.external.UserProfile;
import com.rohit.jobs.job.external.applicationDetails;

import java.util.List;

public interface  JobService {
    List<JobWithCompantDto> findALL();
    String PostJob(Job job);
    JobWithCompantDto findJobById(Long id);
    boolean DeleteJobById(Long id);
    boolean updateJobById(Long id,Job job);
    boolean applyForJob(applicationDetails application );
   List<applicationDetails> getApplicationsById(Long id);
   boolean applicationIdValid(Long id);
   List<Job> findJobByCompanyId(Long CompanyId);
   boolean companyIdValid(Long id);
}
