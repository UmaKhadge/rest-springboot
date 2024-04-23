package com.springcodes.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//spring will find where is transactional written
//@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	//for telling spring there is @Transactional - from this bean spring will check where is transactional written
	/*@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory)
	{
		return new MongoTransactionManager(dbFactory);
	}*/

}
