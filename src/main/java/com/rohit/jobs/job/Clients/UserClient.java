package com.rohit.jobs.job.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name ="FETCHUSER")
public interface UserClient {

    @GetMapping("/api/user/getId")
    Long getUserId(@RequestParam("username") String username);
    @GetMapping("/api/user/get-email")
    String getEmailByUsername(@RequestParam ("username") String username)throws UsernameNotFoundException;
}
