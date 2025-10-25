package com.rohit.jobs.job.mapper;

import com.rohit.jobs.job.Job;
import com.rohit.jobs.job.dto.JobWithCompantDto;
import com.rohit.jobs.job.external.Company;
import com.rohit.jobs.job.external.Reviews;

import java.util.List;

public class JobMapper {

    public static JobWithCompantDto mapToJobWithCompant(
            Job job  , Company company,
            List<Reviews> review
    ) {

        JobWithCompantDto jobWithCompant = new JobWithCompantDto();
        jobWithCompant.setId(job.getId());
        jobWithCompant.setTitle(job.getTitle());
        jobWithCompant.setDescription(job.getDescription());
        jobWithCompant.setMaxSalary(job.getMaxSalary());
        jobWithCompant.setMinSalary(job.getMinSalary());
        jobWithCompant.setLocation(job.getLocation());
         jobWithCompant.setCompany(company);
         jobWithCompant.setReviews(review);
        return jobWithCompant;


    }

}
