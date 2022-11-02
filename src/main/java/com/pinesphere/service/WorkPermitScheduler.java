package com.pinesphere.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.pinesphere.domain.WorkPermit;
import com.pinesphere.repository.WorkPermitRepository;

@Configuration
@EnableScheduling
public class WorkPermitScheduler implements SchedulingConfigurer{
	
	private static final Logger logger = LoggerFactory.getLogger(WorkPermitScheduler.class);
	
	@Autowired
	WorkPermitService wps;
	
//	@Scheduled(fixedDelay =  1000)
//	public void scheduledTask() {
//		System.out.println("VAlue is being printed");
//		wps.addWorkPermit();
//	}
	
	 @Bean
	    public Executor taskExecutor() {
	        return Executors.newSingleThreadScheduledExecutor();
	    }

	    @Override
	    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
	        taskRegistrar.setScheduler(taskExecutor());
	        taskRegistrar.addTriggerTask(
	          new Runnable() {
	              @Override
	              public void run() {
	            	  if(wps.WORK_PERMIT_ID == null)
	            		  wps.pollDB();
	            	  else
	            		  wps.sendMail();
	                  }
	          },
	          new Trigger() {
	              @Override
	              public Date nextExecutionTime(TriggerContext context) {
	                  Instant nextExecutionTime = wps.NEXT_RUN_TIME.atZone(ZoneId.of("Asia/Kolkata")).toInstant();
	                  return Date.from(nextExecutionTime);
	              }
	          }
	        );
	    }

	@PostConstruct
	public void print() {
		System.out.println("Printing ....");
	}
	
	@PreDestroy
	public void predestroy(){
		System.out.println("Pre destroy");
	}
}
