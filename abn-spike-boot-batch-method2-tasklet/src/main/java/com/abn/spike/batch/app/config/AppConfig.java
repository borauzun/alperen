package com.abn.spike.batch.app.config;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.abn.spike.batch.app.model.User;
import com.abn.spike.batch.app.model.User2;
import com.abn.spike.batch.app.model.UserRowMapper;
import com.abn.spike.batch.app.model.UserRowMapper2;
import com.abn.spike.batch.app.processor.UserProcessor;;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@ComponentScan({"com.abn"})
public class AppConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	@Value("${batch.job2.file.location}")
	private String fileLocation;
	
	@Autowired
    private SimpleJobLauncher jobLauncher;
	

	//@Scheduled(cron = "0 0 8 1/1 * ?")  
	@Scheduled(fixedDelay = 10000, initialDelay = 1000)
	public void sendSmsForExpiringBookmark() throws Exception 
	{
	  System.out.println(" Job Started at :"+ new Date());
	  JobParameters param = new JobParametersBuilder().addString("age", "20")
	                                                   .addLong("time",System.currentTimeMillis()).toJobParameters();
	  	JobExecution execution = jobLauncher.run(exportUserJob(), param);
		System.out.println("Exit Status : " + execution.getStatus());
		System.out.println("Exit Errors : " + execution.getAllFailureExceptions());
	  
	}
	
	@Bean
	public JdbcCursorItemReader<User2> reader(){
		//JdbcCursorItemReader<User> cursorItemReader = new JdbcCursorItemReader<>();
		JdbcCursorItemReader<User2> cursorItemReader = new JdbcCursorItemReader<>();
		cursorItemReader.setDataSource(dataSource);
		//cursorItemReader.setSql("\"select ID, USER_LOGIN, USER_PASS, AGE from USERS where age > #{jobParameters['age']}\"");
		cursorItemReader.setSql("select * from TSK_TTPUSER_REF");
		cursorItemReader.setRowMapper(new UserRowMapper2());
		return cursorItemReader;
	}
	
	@Bean
	public UserProcessor processor(){
		return new UserProcessor();
	}
	
	@Bean
	public FlatFileItemWriter<User> writer(){
		FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
		//writer.setResource(new ClassPathResource("file://Users.csv"));
		writer.setResource(new FileSystemResource(fileLocation));
		DelimitedLineAggregator<User> lineAggregator = new DelimitedLineAggregator<User>();
		lineAggregator.setDelimiter(",");
		
		BeanWrapperFieldExtractor<User>  fieldExtractor = new BeanWrapperFieldExtractor<User>();
		//This should match with Bean getters
		fieldExtractor.setNames(new String[]{"ID","Username","Password","Age"});
		lineAggregator.setFieldExtractor(fieldExtractor);
		
		writer.setLineAggregator(lineAggregator);
		//
		
		return writer;
	}
	@Bean
	public FlatFileItemWriter<User2> writer2(){
		FlatFileItemWriter<User2> writer = new FlatFileItemWriter<User2>();
		//writer.setResource(new ClassPathResource("file://Users.csv"));
		writer.setResource(new FileSystemResource(fileLocation));
		DelimitedLineAggregator<User2> lineAggregator = new DelimitedLineAggregator<User2>();
		lineAggregator.setDelimiter(",");
		
		BeanWrapperFieldExtractor<User2>  fieldExtractor = new BeanWrapperFieldExtractor<User2>();
		//This should match with Bean getters
		fieldExtractor.setNames(new String[]{"EXTERNAL_REF","EXTERNAL_UID","PROCESS_ID","BC_NUMBER","PROCESS_STATUS","IBAN","THIRD_PARTY_URL"});
		lineAggregator.setFieldExtractor(fieldExtractor);
		
		writer.setLineAggregator(lineAggregator);
		return writer;
	}
	

@Bean
public JdbcBatchItemWriter<User2> dbWriter(){
	JdbcBatchItemWriter<User2> writer = new JdbcBatchItemWriter<User2>();
    writer.setDataSource(dataSource);	
    writer.setSql("insert into EXAM_RESULT(STUDENT_NAME, DOB, PERCENTAGE) \n" + 
    		"                  values (?, ?, ?)");
    //writer.setItemPreparedStatementSetter(preparedStatementSetter);
	return writer;
}


	@Bean
	public Step step1(){
		return stepBuilderFactory.get("step1").<User2,User2>chunk(1).reader(reader()).processor(processor()).writer(writer2()).build();
	     
	}
	
	@Bean
	public Step step2(){
		return stepBuilderFactory.get("step2").tasklet(new Tasklet() {
					@Override
					public RepeatStatus execute(StepContribution contribution,
							ChunkContext chunkContext) throws Exception {
						throw new RuntimeException("Planned");
					}
				}).build();
	}
	
	@Bean
	public Job exportUserJob(){
		//return jobBuilderFactory.get("exportUserJob").incrementer(new RunIdIncrementer()).flow(step1()).next(step2()).end().build();
		return jobBuilderFactory.get("exportUserJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
		
	}
	
	//****
	@Bean
	Tasklet tasklet1() {
	    final String someStringToPass="";

	    Tasklet t=new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				 chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("someStringToPass", someStringToPass);
				  return RepeatStatus.FINISHED;
			}
		};
	    
	    return t;
	}
}