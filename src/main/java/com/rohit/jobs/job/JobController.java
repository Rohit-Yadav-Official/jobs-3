package com.rohit.jobs.job;

import com.rohit.jobs.job.Clients.CycleBrakerClient;
import com.rohit.jobs.job.dto.JobWithCompantDto;
import com.rohit.jobs.job.external.UserProfile;
import com.rohit.jobs.job.external.applicationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RequestMapping("/job")
@RestController
public class JobController {

    @Autowired
    CycleBrakerClient cycleBrakerClient;
   private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
   }

    @GetMapping
      public List<JobWithCompantDto> findALL(){
          return jobService.findALL();
      }
    @PostMapping
    public String PostJob(@RequestBody Job job){
        String s=jobService.PostJob( job);
        return s;
    }
    @GetMapping("/company")
    public ResponseEntity<List<Job>> findByCompanyId(@RequestParam Long companyId){
        if(jobService.companyIdValid(companyId)){
         return new ResponseEntity<>( jobService.findJobByCompanyId(companyId), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/{id}/application")
    public ResponseEntity<String> applyJob( @RequestBody applicationDetails application, @RequestHeader("Authorization") String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        String token = authHeader.substring(7);


        String username= cycleBrakerClient.getUsernameFromToken(token);
        if(username!=null && username.equals(application.getApplicantName())){
          if( jobService.applyForJob(application)){
              return new ResponseEntity<>("applied succesfully", HttpStatus.OK);

          }
        }
        if(!username.equals(application.getApplicantName())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        return new ResponseEntity<>("can't apply", HttpStatus.NOT_FOUND);

    }
    @GetMapping("/{id}/applications")
    public ResponseEntity<List<applicationDetails>> getApplication( @PathVariable("id") Long id,@RequestHeader("Authorization") String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<>());
        }
        String token = authHeader.substring(7);
          if(jobService.applicationIdValid(id)){
              Long companyId=jobService.findJobById(id).getCompany().getId();
             Long companyIdformtoken= cycleBrakerClient.getIdByToken(token);
             if(companyIdformtoken!=null && companyIdformtoken.equals(companyId)){
             return new ResponseEntity<>( jobService.getApplicationsById(id), HttpStatus.OK);
          }
             else {
                 return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
             }
          }

          return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }






    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompantDto> findJobById(@PathVariable Long id){
        JobWithCompantDto job= jobService.findJobById(id);
        if(job.getId()!=null){
            return new ResponseEntity<>(job, HttpStatus.OK);
        };

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean delete= jobService.DeleteJobById(id);
        if(delete) {

           return new ResponseEntity<>("Job deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Job deletion failed", HttpStatus.NOT_FOUND);

    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateJobById(@PathVariable Long id, @RequestBody Job job){
       boolean update= jobService.updateJobById(id,job);
       if(update) {
           return new ResponseEntity<>("Job updated", HttpStatus.OK);
       }
        return new ResponseEntity<>("Job updated", HttpStatus.NOT_FOUND);
    }

}
