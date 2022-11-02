package com.pinesphere.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.pinesphere.service.WorkPermitScheduler;

@SpringBootApplication
@ComponentScan(basePackages = "com.pinesphere.*")
@EntityScan(basePackages = {"com.pinesphere.*"} )
@EnableJpaRepositories(basePackages = {"com.pinesphere.*"})
public class PinesphereApplication implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(PinesphereApplication.class, args);
	}


    public void run(String... args) throws Exception {
        System.out.println("Loaded Environment: ");
    }
}
