package com.rohit.jobs.job.Imp;


import com.rohit.jobs.job.dto.JobApplicationSubmittedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationSubmittedMessageProducer {


    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public JobApplicationSubmittedMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJobApplicationSubmittedMessage(String email, String body) {
        JobApplicationSubmittedMessage message = new JobApplicationSubmittedMessage(email, body);
        rabbitTemplate.convertAndSend("JobApplicationSubmitted", message);
    }
}
