package com.frankie.workdev.util;

import com.frankie.workdev.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Component
public class EmailContentBuilder {
    private final  TemplateEngine templateEngine;

    @Autowired
    public EmailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String builderEmailContent(List<Job> jobs, Context context) {
        context.setVariable("jobs", jobs);
        return templateEngine.process("templateEmailSubscriber", context);
    }


}
