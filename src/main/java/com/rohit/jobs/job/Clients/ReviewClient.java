package com.rohit.jobs.job.Clients;


import com.rohit.jobs.job.external.Company;
import com.rohit.jobs.job.external.Reviews;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEWSMS")
public interface ReviewClient {


    @GetMapping("/review")
    List<Reviews> getReviews(@RequestParam("companyId")  Long companyId);
}
