package com.frankie.workdev.service.implement;

import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.subscriber.CreateSubscriberDto;
import com.frankie.workdev.dto.subscriber.SubscriberDto;
import com.frankie.workdev.entity.FileEntity;
import com.frankie.workdev.entity.Job;
import com.frankie.workdev.entity.Skill;
import com.frankie.workdev.entity.Subscriber;
import com.frankie.workdev.repository.FileRepository;
import com.frankie.workdev.repository.JobRepository;
import com.frankie.workdev.repository.SubscriberRepository;
import com.frankie.workdev.service.EmailService;
import com.frankie.workdev.service.SubscriberService;
import com.frankie.workdev.util.EmailContentBuilder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;
    private SubscriberRepository subscriberRepository;
    private JobRepository jobRepository;
    private EmailContentBuilder emailContentBuilder;
    private FileRepository fileRepository;

    @Override
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        javaMailSender.send(message);
    }

    @Scheduled(cron = "0 0 7 * * ?")
//    @Scheduled(fixedDelay = 30000)
    @Override
    public void scheduleEmail() throws MessagingException {
        List<Subscriber> subscribersList = subscriberRepository.findAll();
        for (Subscriber subscriber : subscribersList) {
            List<Skill> skills = subscriber.getSkills();
            List<Job> jobs = jobRepository.findJobBySkills(skills);
            Context context = new Context();
            context.setVariable("userName", subscriber.getName());
            String emailContent = emailContentBuilder.builderEmailContent(jobs, context);
            sendEmail(subscriber.getEmail(), "Work Dev Job Alerts", emailContent);
        }
    }


//    private String createEmailContent(List<Job> jobs) {
//        StringBuilder emailContent = new StringBuilder("Here are some jobs for you:\n");
//        for (Job job : jobs) {
//            emailContent.append("- ").append(job.getName()).append("\n");
//        }
//        return emailContent.toString();
//    }
}

