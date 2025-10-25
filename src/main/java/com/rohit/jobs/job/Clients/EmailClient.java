package com.rohit.jobs.job.Clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("MAILSENDER")
public interface EmailClient {

    @GetMapping("/email/verifyEmail")
     void sendMail(@RequestParam("token") String token, @RequestParam("email") String email);


    @GetMapping("/email/applyforjob")
     void sendMailForApplyingJob(@RequestParam ("body") String body, @RequestParam("email") String email);


}
