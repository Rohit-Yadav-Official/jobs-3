package com.rohit.jobs.job.Imp;


import com.rohit.jobs.job.Clients.*;
import com.rohit.jobs.job.Job;
import com.rohit.jobs.job.Repo.ApplicationRepo;
import com.rohit.jobs.job.Repo.JobRepository;
import com.rohit.jobs.job.JobService;
import com.rohit.jobs.job.dto.JobWithCompantDto;
import com.rohit.jobs.job.external.Company;
import com.rohit.jobs.job.external.Reviews;
import com.rohit.jobs.job.external.applicationDetails;
import com.rohit.jobs.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class  JobServiceImpl implements JobService {
   JobRepository jobRepository;
   ApplicationRepo applicationRepo;

   @Autowired
   private RestTemplate restTemplate;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;
    private EmailClient emailClient;
    private CycleBrakerClient cycleBrakerClient;
    private UserClient userClient;
    JobApplicationSubmittedMessageProducer jobApplicationSubmittedMessageProducer;
    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient, ApplicationRepo applicationRepo, EmailClient emailClient, CycleBrakerClient cycleBrakerClient, UserClient userClient,JobApplicationSubmittedMessageProducer jobApplicationSubmittedMessageProducer) {
       this.jobRepository = jobRepository;
       this.companyClient = companyClient;
       this.reviewClient = reviewClient;
       this.applicationRepo = applicationRepo;
       this.emailClient = emailClient;
       this.cycleBrakerClient = cycleBrakerClient;
       this.userClient = userClient;
       this.jobApplicationSubmittedMessageProducer = jobApplicationSubmittedMessageProducer;

   }


    @Override
    public List<JobWithCompantDto> findALL(){
      List<Job> jobs = jobRepository.findAll();
      List<JobWithCompantDto> jobWithCompant = new ArrayList<>();

     // RestTemplate restTemplate = new RestTemplate();
      for (Job job : jobs) {


          Company company = companyClient.getCompany(job.getCompanyId());
          List<Reviews> reviews = reviewClient.getReviews(job.getCompanyId());


          if (reviews == null) {
              reviews = new ArrayList<>();
          }
          JobWithCompantDto jobWithCompantDto= JobMapper.mapToJobWithCompant(job,company,reviews);
          //jobWithCompantDto.setComapny(company);
          jobWithCompant.add(jobWithCompantDto);

      }
  return jobWithCompant;
    }




    @Override
    public String PostJob(Job job){

          jobRepository.save(job);
          return "job added sucessfully";
    }

    @Override
    public JobWithCompantDto findJobById(Long id) {
        // Fetch job from repository
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null) {
            throw new IllegalArgumentException("Job not found for ID: " + id);
        }
        Long companyId = job.getCompanyId();

        // Fetch company details

        Company company = companyClient.getCompany(companyId);
        List<Reviews> reviews = reviewClient.getReviews(companyId);



        // Map data to DTO
        return JobMapper.mapToJobWithCompant(job, company, reviews);
    }



    @Override
    public boolean DeleteJobById(Long id){
        jobRepository.deleteById(id);
        return jobRepository.existsById(id);
    }
    @Override
    public  boolean updateJobById( Long id,Job job){

        Optional<Job> jobOptional=jobRepository.findById(id);
            if(jobOptional.isPresent()) {
               Job job1=jobOptional.get();
                job1.setTitle(job.getTitle());
                job1.setDescription(job.getDescription());
                job1.setMinSalary(job.getMinSalary());
                job1.setMaxSalary(job.getMaxSalary());
                job1.setLocation(job.getLocation());
                jobRepository.save(job1);
                return true;
            }
        return false;
        }

        @Override
        public  boolean applyForJob(applicationDetails application ){
                if(jobRepository.existsById(application.getApplicationId())) {
                    applicationRepo.save(application);

                    Long jobId = application.getApplicationId();
                    Job job =jobRepository.findById(jobId).get();
                    String Jobtitle = job.getTitle();
                  String email= userClient.getEmailByUsername(application.getApplicantName());
                   Company company= companyClient.getCompany(jobRepository.findById(jobId).get().getCompanyId());
                   String companyName=company.getName();;
                   String body= "Job application at " + companyName + " for the position of " + Jobtitle + " has been successfully submitted.";
                   jobApplicationSubmittedMessageProducer.sendJobApplicationSubmittedMessage(email,body);
                    return true;
                }
                return false;


        }
        @Override
        public List<applicationDetails> getApplicationsById(Long id){
                 //List<applicationDetails> applications =applicationRepo.findByapplicationId(id);
                List<applicationDetails> applicationsById = new ArrayList<>();
            List<applicationDetails> applications= applicationRepo.findAll();
              for (applicationDetails application : applications) {
                  if(application.getApplicationId()==(id)){
                      applicationsById.add(application);
                  }

              }

            return applicationsById;
        }

        public boolean applicationIdValid(Long id){
          return jobRepository.existsById(id);

        }
        @Override
        public List<Job> findJobByCompanyId(Long companyId){
           return   jobRepository.findBycompanyId(companyId);

        }
        public boolean companyIdValid(Long companyId){

                List<Job>jobs=jobRepository.findAll();
                for (Job job : jobs) {
                    if(job.getCompanyId()==companyId){
                        return true;
                    }
                }
                return false;
    }
}


