package com.brigada.bloss.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.brigada.bloss.jobs.FilmProducerJob;

import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class SchedulerConfig {

	@Bean
	public JobDetailFactoryBean filmProducerJob() {
		var factoryBean = new JobDetailFactoryBean();
		factoryBean.setJobClass(FilmProducerJob.class);
		factoryBean.setDurability(true);
		return factoryBean;
	}

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		var jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, Trigger filmProducerJobTrigger)
			throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobFactory(jobFactory);
		factory.setQuartzProperties(quartzProperties());
		factory.setTriggers(filmProducerJobTrigger);
		log.info("starting jobs....");
		return factory;
	}

	@Bean
	public SimpleTriggerFactoryBean filmProducerJobTrigger(@Qualifier("filmProducerJob") JobDetail jobDetail,
			@Value("${film-producer-job.frequency}") long frequency) {

		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setStartDelay(5000L);
		factoryBean.setRepeatInterval(frequency);
		factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		return factoryBean;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		var propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.yml"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

}