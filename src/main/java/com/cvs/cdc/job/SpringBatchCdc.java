package com.cvs.cdc.job;

import com.cvs.cdc.dto.EmployeeDTO;
import com.cvs.cdc.mapper.EmployeeFileRowMapper;
import com.cvs.cdc.model.Employee;
import com.cvs.cdc.processor.EmployeeProcessorDemo3;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import com.cvs.cdc.processor.EmployeeProcessorDemo1;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;

@Configuration
public class SpringBatchCdc {

    @Autowired
    public DataSource dataSource;



    @Autowired
    public EmployeeProcessorDemo1 employeeProcessorDemo1;

    @Autowired
    @Qualifier("employeeprocessordemo3")
    public EmployeeProcessorDemo3 employeeProcessorDemo3;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("employeewriter")
    public ItemWriter itemWriter;

    private Resource outputResource = new FileSystemResource("output/employee_output.csv");


    @Qualifier("demo1")
    @Bean
    public Job demo1Job(JobBuilderFactory jobBuilderFactory){
        return jobBuilderFactory.get("demo1")
                .start(step1Demo1())
                .build();

    }
  /* public JobBuilderFactory jobBuilderFactory;
    public StepBuilderFactory stepBuilderFactory;
    public EmployeeProcessorDemo1 employeeProcessor;
    public DataSource dataSource;

    @Autowired
    public SpringBatchCdc(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EmployeeProcessorDemo1 employeeProcessor, DataSource dataSource){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.employeeProcessor = employeeProcessor;
        this.dataSource = dataSource;
    }*/

    @Qualifier("demo3")
    @Bean
    public Job demo3Job(JobBuilderFactory jobBuilderFactory) throws Exception {
        return jobBuilderFactory.get("demo3")
                .start(step1Demo3())
                .build();
    }



    @Qualifier("demorunBatchJobCsvToDbMultiThread")
    @Bean
    public Job demorunBatchJobCsvToDbMultiThread(JobBuilderFactory jobBuilderFactory) throws Exception {
        return  jobBuilderFactory.get("demorunBatchJobCsvToDbMultiThread")
                .start(step1JobCsvToDbMultiThread())
                .build();
    }
    @Bean
    public Step step1JobCsvToDbMultiThread() {

        return stepBuilderFactory.get("step1")
                .<EmployeeDTO,Employee>chunk(5)
                .reader(EmployeeReader())
                .processor(employeeProcessorDemo1)
                .writer(/*employeeDBWriterDefault()*/ itemWriter)
                .taskExecutor(taskExecutor())
                .build();

    }
    @Bean
    public Step step1Demo1() {

        return stepBuilderFactory.get("step1")
                .<EmployeeDTO,Employee>chunk(5)
                .reader(EmployeeReader())
                .processor(employeeProcessorDemo1)
                .writer(/*employeeDBWriterDefault()*/ itemWriter)
                .build();
        
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(5);
        return simpleAsyncTaskExecutor;
    }
    @Bean
    public Step step1Demo3() throws Exception {
        return this.stepBuilderFactory.get("step3")
                .<Employee, EmployeeDTO>chunk(10)
                .reader(employeeDBReader())
                .processor(employeeProcessorDemo3)
                .writer(employeeFileWriter())
                .build();
    }

    @Bean
    public ItemStreamReader<Employee> employeeDBReader() {
        JdbcCursorItemReader<Employee> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("select * from employees");
        reader.setRowMapper(((resultSet, i) -> {
           return Employee.builder().age(resultSet.getInt("age")).email(resultSet.getString("email"))
                   .employeeId(resultSet.getString("employee_id")).firstName(resultSet.getString("first_name")).lastName(resultSet.getString("last_name")).build();
        }));
        return reader;
    }

    @Bean
    public ItemWriter<EmployeeDTO> employeeFileWriter() throws Exception {
        FlatFileItemWriter<EmployeeDTO> writer = new FlatFileItemWriter<>();
        writer.setResource(outputResource);
        writer.setLineAggregator(new DelimitedLineAggregator<EmployeeDTO>() {
            {
                setFieldExtractor(new BeanWrapperFieldExtractor<EmployeeDTO>() {
                    {
                        setNames(new String[]{"employeeId", "firstName", "lastName", "email", "age"});
                    }
                });
            }
        });
        writer.setShouldDeleteIfExists(true);
        return writer;
    }


    @Bean
    @StepScope
    public FlatFileItemReader<EmployeeDTO> EmployeeReader() {
        FlatFileItemReader<EmployeeDTO> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(inputFileResource(null));
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setNames("employeeId", "firstName", "lastName", "email", "age");
        DefaultLineMapper<EmployeeDTO> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(new EmployeeFileRowMapper());
        flatFileItemReader.setLineMapper(defaultLineMapper);
        return flatFileItemReader;
    }

    @Bean
    @StepScope
    public Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) {
        return new ClassPathResource(fileName);
    }

    @Bean
    public JdbcBatchItemWriter<Employee> employeeDBWriterDefault() {
        JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into employee (employee_id, first_name, last_name, email, age) values (:employeeId, :firstName, :lastName, :email, :age)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
        return itemWriter;
    }
}