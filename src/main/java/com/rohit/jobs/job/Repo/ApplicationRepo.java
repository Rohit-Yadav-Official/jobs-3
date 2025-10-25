package com.rohit.jobs.job.Repo;

import com.rohit.jobs.job.Job;
import com.rohit.jobs.job.external.applicationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepo extends JpaRepository<applicationDetails, Long> {
      applicationDetails findByapplicationId(Long id);
}
