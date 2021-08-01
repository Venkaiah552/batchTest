package com.cvs.cdc.job;


import com.cvs.cdc.model.CdcRequestToApi;
import com.cvs.cdc.model.CdcResponseToDb;
import com.cvs.cdc.processor.CdcResponseProcessorApi;
import com.cvs.cdc.writer.CdcResponseDBWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
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
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

@Configuration
public class SpringBatchCdc {


    @Autowired
    @Qualifier("cdcresponseeprocessor")
    public CdcResponseProcessorApi cdcresponseeprocessor;


    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("cdcdbresponsewriter")
    public CdcResponseDBWriter cdcResponseDbWriter;



    private Resource outputResource = new FileSystemResource("output/employee_output.csv");

    @Value("${name}")
    private String name;


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public void test() {
        System.out.println("value of name is " + name);
    }

    @Qualifier("dbtoapi")
    @Bean
    public Job dbtoapiJob(JobBuilderFactory jobBuilderFactory) throws Exception {
        return jobBuilderFactory.get("dbtoapi")
                .start(step1DbtoApi())
                .build();
    }



    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

    @Bean("partitioner")
    @StepScope
    public Partitioner partitioner() {
        //log.info("In Partitioner");
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        try {
            resources = resolver.getResources("/*.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        partitioner.setResources(resources);
        partitioner.partition(10);
        return partitioner;
    }

    @Bean
    public Step step1DbtoApi() throws Exception {
        return this.stepBuilderFactory.get("step1DbtoApi")
                .partitioner("step1", partitioner())
                .step(step1())
                // .gridSize(4)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<CdcRequestToApi, CdcRequestToApi>chunk(500)
                .reader(personItemReader)
                .processor(cdcresponseeprocessor)
                .writer(cdcResponseDbWriter)
                .build();
    }


    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }

    @Autowired
    private FlatFileItemReader<CdcRequestToApi> personItemReader;

    @Autowired
    private ExecutionContext executionContext;

    @Bean
    @StepScope
    @Qualifier("personItemReader")
    public FlatFileItemReader<CdcRequestToApi> personItemReader(@Value("#{stepExecutionContext['fileName']}") String filename) throws MalformedURLException {


        executionContext.put("filename", filename);
        // log.info("In Reader");
        return new FlatFileItemReaderBuilder<CdcRequestToApi>()
                .name("personItemReader")
                //  .delimited()
                //.names(new String[] { "firstName", "lastName" })
                .lineTokenizer(new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB) {
                    {
                        setNames(new String[]{"vaxEventId", "extType", "pprlId", "recipId", "recipFirstName", "recipMiddleName", "recipLastName", "recipDob",
                                "recipSex", "recipAddressStreet", "recipAddressStreet_2", "recipAddressCity", "recipAddressCounty", "recipAddressState", "recipAddressZip", "recipRace1", "recipRace2", "recipRace3",
                                "recipRace4", "recipRace5", "recipRace6", "recipEthnicity", "adminDate", "cvx", "ndc", "mvx", "lotNumber", "vaxExpiration",
                                "vaxAdminSite", "vaxRoute", "doseNum", "vaxSeriesComplete", "responsibleOrg", "adminName", "vtrcksProvPin", "adminType",
                                "adminAddressStreet", "adminAddressStreet_2", "adminAddressCity", "adminAddressCounty", "adminAddressState", "adminAddressZip",
                                "vaxRefusal", "cmorbidStatus", "serology"});
                    }
                })

                .fieldSetMapper(new BeanWrapperFieldSetMapper<CdcRequestToApi>() {
                    {
                        setTargetType(CdcRequestToApi.class);
                    }
                })

                .resource(new UrlResource(filename))
                .build();
    }


    private Map<String, Sort.Direction> sorts;


    @Bean
    public ItemWriter<CdcResponseToDb> cdcResponseDbWriter() throws Exception {
        FlatFileItemWriter<CdcResponseToDb> writer = new FlatFileItemWriter<>();
        writer.setResource(outputResource);
        writer.setLineAggregator(new DelimitedLineAggregator<CdcResponseToDb>() {
            {
                setFieldExtractor(new BeanWrapperFieldExtractor<CdcResponseToDb>() {
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
    public Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) {
        return new ClassPathResource(fileName);
    }

}
