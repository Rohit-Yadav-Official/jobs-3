package com.rohit.jobs.job.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name ="CYCLEBRAKERMS")
public interface CycleBrakerClient {



        @GetMapping("/helper/get-companyId")
        Long getCompanyIdByUsername(@RequestParam ("companyusername") String username);

        @GetMapping("/helper/get-userId")
         Long getUserIdByUsername(@RequestParam ("userusername") String username);

        @GetMapping("/helper/get-IdBytoken")
        Long getIdByToken(@RequestParam ("token") String token);

        @GetMapping("/helper/get-username-from-token")
         String getUsernameFromToken(@RequestParam ("token") String token);
}
