package com.rohit.jobs.job.Repo;

import com.rohit.jobs.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findBycompanyId(Long companyId);


}
