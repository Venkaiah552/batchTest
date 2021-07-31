package com.cvs.cdc.job;

//import com.cvs.cdc.dto.EmployeeDTO;
//import com.cvs.cdc.mapper.EmployeeFileRowMapper;

import com.cvs.cdc.model.*;
//import com.cvs.cdc.model.Employee;
import com.cvs.cdc.processor.CdcResponseProcessorApi;
//import com.cvs.cdc.processor.EmployeeProcessorDemo3;
import com.cvs.cdc.reader.*;
import com.cvs.cdc.repo.ImmunizationInfoRepo;
//import com.cvs.cdc.writer.CdcResponseDBWriter;
import com.cvs.cdc.writer.CdcResponseDBWriter;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.*;
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
//import com.cvs.cdc.processor.EmployeeProcessorDemo1;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
//@EnableJpaRepositories
/*@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = "com.cvs.cdc.repo"
)*/
public class SpringBatchCdc {

    /*@Autowired
    public DataSource dataSource;*/
    /*@Bean
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.acme.domain");
        factory.setDataSource(dataSource());
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }*/

    /*



        @Autowired
        public EmployeeProcessorDemo1 employeeProcessorDemo1;

        @Autowired
        @Qualifier("employeeprocessordemo3")
        public EmployeeProcessorDemo3 employeeProcessorDemo3;
    */
    /*@Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("com.cvs.cdc.model");
        factory.setJpaVendorAdapter(getVendorAdapter());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private HibernateJpaVendorAdapter getVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(Boolean.TRUE);
        return vendorAdapter;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        txManager.afterPropertiesSet();
        return txManager;
    }*/

    @Autowired
    @Qualifier("cdcresponseeprocessor")
    public CdcResponseProcessorApi cdcresponseeprocessor;


    @Autowired
    public StepBuilderFactory stepBuilderFactory;
/*
    @Autowired
    @Qualifier("employeewriter")
    public ItemWriter itemWriter;*/

    @Autowired
    @Qualifier("cdcdbresponsewriter")
    public CdcResponseDBWriter cdcResponseDbWriter;

    @Autowired
    public ImmunizationInfoRepo immunizationInfoRepo;

    private Resource outputResource = new FileSystemResource("output/employee_output.csv");

    @Value("${name}")
    private String name;


    /*@Qualifier("demo1")
    @Bean
    public Job demo1Job(JobBuilderFactory jobBuilderFactory){
        return jobBuilderFactory.get("demo1")
                .start(step1Demo1())
                .build();

    }*/
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

   /* @Qualifier("demo3")
    @Bean
    public Job demo3Job(JobBuilderFactory jobBuilderFactory) throws Exception {
        return jobBuilderFactory.get("demo3")
                .start(step1Demo3())
                .build();
    }*/

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



    /*@Qualifier("demorunBatchJobCsvToDbMultiThread")
    @Bean
    public Job demorunBatchJobCsvToDbMultiThread(JobBuilderFactory jobBuilderFactory) throws Exception {
        return  jobBuilderFactory.get("demorunBatchJobCsvToDbMultiThread")
                .start(step1JobCsvToDbMultiThread())
                .build();
    }*/
    /*@Bean
    public Step step1JobCsvToDbMultiThread() {

        return stepBuilderFactory.get("step1")
                .<EmployeeDTO,Employee>chunk(5)
                .reader(EmployeeReader())
                .processor(employeeProcessorDemo1)
                .writer(*//*employeeDBWriterDefault()*//* itemWriter)
                .taskExecutor(taskExecutor())
                .build();

    }*/
    /*@Bean
    public Step step1Demo1() {

        return stepBuilderFactory.get("step1")
                .<EmployeeDTO,Employee>chunk(5)
                .reader(EmployeeReader())
                .processor(employeeProcessorDemo1)
                .writer(employeeDBWriterDefault() itemWriter)
                .build();
        
    }*/

    /*@Bean
    public TaskExecutor taskExecutor1() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(5);
        return simpleAsyncTaskExecutor;
    }*/
   /* @Bean
    public Step step1Demo3() throws Exception {
        return this.stepBuilderFactory.get("step3")
                .<Employee, EmployeeDTO>chunk(10)
                .reader(cdcInfoDBReader())
                .processor(employeeProcessorDemo3)
                .writer(employeeFileWriter())
                .build();
    }*/

    @Autowired
    @Qualifier("immunizationinforeader")
    private ImmunizationInfoDBReader immunizationInfoDBReader;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory entityManagerFactory;

   /* @Bean
    public ItemStreamReader<CdcRequestToApi> immunizationJpaDbItemReader() throws Exception {

        //JpaPagingItemReader<CdcRequestToApi> jpaPagingItemReader = new JpaPagingItemReader<>();
        CustomJpaPagingItemReader<CdcRequestToApi> jpaPagingItemReader = new CustomJpaPagingItemReader<>();
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        JpaQueryProviderImpl<CdcRequestToApi> jpaQueryProviderImpl = new JpaQueryProviderImpl<>();
        jpaQueryProviderImpl.setQuery("CdcRequestToApi.findAll");
        jpaPagingItemReader.setQueryProvider(jpaQueryProviderImpl);
        jpaPagingItemReader.setPageSize(50000);
        // must be set to false if multi threaded
        jpaPagingItemReader.setSaveState(false);
        //jpaPagingItemReader.setMaxItemCount(2);
        jpaPagingItemReader.afterPropertiesSet();

        return jpaPagingItemReader;
    }*/

   /* @Bean
    public DataSource dataSource() {
        *//*teradata.datasource.driverClassName=com.teradata.jdbc.TeraDriver
        teradata.datasource.jdbc-url=jdbc:teradata://TIDWDEV1
        teradata.datasource.username=IDW_STG_CPL
        teradata.datasource.password=rFh_9ghA
        teradata.jpa.database-platform=org.hibernate.dialect.TeradataDialect
*//*
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("com.teradata.jdbc.TeraDriver");
        hikariDataSource.setJdbcUrl("jdbc:teradata://TIDWDEV1");
        hikariDataSource.setUsername("IDW_STG_CPL");
        hikariDataSource.setPassword("rFh_9ghA");
        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setMinimumIdle(0);

        return hikariDataSource;
    }
*/

   /* @Bean
    public ItemReader<? extends CdcRequestToApi> reader() {
        JdbcPagingItemReader<CdcRequestToApi> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource());
        //reader.setPageSize(2);
        reader.setRowMapper(new BeanPropertyRowMapper<CdcRequestToApi>(CdcRequestToApi.class));

       TeradataPagingQueryProvider teradataPagingQueryProvider = new TeradataPagingQueryProvider();
         teradataPagingQueryProvider.setSelectClause("SELECT  sub.* ");
        teradataPagingQueryProvider.setFromClause("from (select ROW_NUMBER() over (order by  EXTR_DT,RXC_IMM_ID,JOB_NM  asc) rid, t.* from IDW_APPS_STG_VW.IMM_COVID_IDNTFD_EXTRACT t where t.actvy_typ_cd is null) sub");
        teradataPagingQueryProvider.setWhereClause("sub.rid> 0 and sub.rid<=2");

        final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(this.dataSource());
        sqlPagingQueryProviderFactoryBean.setSelectClause("select sub.* ");
        sqlPagingQueryProviderFactoryBean.setFromClause("from (select ROW_NUMBER() over (order by  EXTR_DT,RXC_IMM_ID,JOB_NM  asc) rownum, " +
                "t.* from IDW_APPS_STG_VW.IMM_COVID_IDNTFD_EXTRACT t where t.actvy_typ_cd is null) sub ");
        sqlPagingQueryProviderFactoryBean.setWhereClause("sub.rownum> 0 and sub.rownum<=2");
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("JOB_NM", Order.ASCENDING);
        sortKeys.put("EXTR_DT", Order.ASCENDING);
        sortKeys.put("RXC_IMM_ID", Order.ASCENDING);
       // sqlPagingQueryProviderFactoryBean.setSortKey("id");
        sqlPagingQueryProviderFactoryBean.setSortKeys(sortKeys);
        ///teradataPagingQueryProvider.setSortKeys(sortKeys);
        //System.out.println("sqlPagingQueryProviderFactoryBean value is"+sqlPagingQueryProviderFactoryBean.toString());
        try {
            reader.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
            reader.setRowMapper(new CdcRequestRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
        }
      /// reader.setQueryProvider(teradataPagingQueryProvider);
        return reader;
    }
*/



   /* @Bean
    public JdbcPagingItemReader<CdcRequestToApi> pagingItemReader() {
        JdbcPagingItemReader<CdcRequestToApi> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource);
        reader.setPageSize(10);
        reader.setFetchSize(2);
        reader.setRowMapper(new CdcRequestRowMapper());

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        H2PagingQueryProvider queryProvider = new H2PagingQueryProvider();
        queryProvider.setSelectClause("select *");
        queryProvider.setFromClause("from user");
        queryProvider.setSortKeys(sortKeys);


        reader.setQueryProvider(queryProvider);

        return reader;
    }*/

   /* @Bean
    public Step step1DbtoApi() throws Exception {
        return this.stepBuilderFactory.get("step3")
                .<CdcRequestToApi, CdcResponseToDb>chunk(50000)
                .reader(immunizationJpaDbItemReader()) //  //reader()
                //.processor(cdcresponseeprocessor)
                .processor(asyncItemProcessor())
                //.writer(cdcResponseDbWriter)  //employeeFileWriter() //cdcResponseDbWriter()
                .writer(asyncItemWriter())  //employeeFileWriter() //cdcResponseDbWriter()
                //.taskExecutor(taskExecutor())
                .build();
    }*/

    /*@Bean
    public Step step1DbtoApi() throws Exception {
        return this.stepBuilderFactory.get("step3")
                .partitioner(slaveStep().getName(), partitioner1())
                .step(slaveStep())
                .gridSize(4)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }*/

    /*@Bean
    public Step slaveStep() {
        return this.stepBuilderFactory.get("slaveStep")
                .<CdcRequestToApi, CdcResponseToDb>chunk(1000)
                .reader(pagingItemReader(null, null))
                .processor(cdcresponseeprocessor)
                .writer(cdcResponseDbWriter)
                .build();
    }*/

   /* @Bean
    public ColumnRangePartitioner partitioner1() {
        ColumnRangePartitioner columnRangePartitioner = new ColumnRangePartitioner();
        columnRangePartitioner.setDataSource(this.dataSource());
        return columnRangePartitioner;
    }*/

  /*  @Bean
    @StepScope
    public JdbcPagingItemReader<CdcRequestToApi> pagingItemReader(
            @Value("#{stepExecutionContext['minValue']}")Long minValue,
            @Value("#{stepExecutionContext['maxValue']}")Long maxValue) {
        System.out.println("reading " + minValue + " to " + maxValue);
        JdbcPagingItemReader<CdcRequestToApi> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(this.dataSource());
        reader.setFetchSize(1000);
        reader.setRowMapper(new CdcRequestRowMapper());

        TeradataPagingQueryProvider queryProvider = new TeradataPagingQueryProvider();

        queryProvider.setSelectClause("(select ROW_NUMBER() over (order by  EXTR_DT,RXC_IMM_ID,JOB_NM  asc) rid, t.*  ");
        queryProvider.setFromClause("from IDW_APPS_STG_VW.IMM_COVID_IDNTFD_EXTRACT t ");
        queryProvider.setWhereClause("where t.actvy_typ_cd is null and batch_ts<= (select max(batch_ts) from IDW_APPS_STG_VW.CDC_API_EXTRACT_DRIVER) ) sub where rid >= " + minValue + " and rid < " + maxValue);

        Map<String, Order> sortKeys = new HashMap<>(1);

        sortKeys.put("JOB_NM", Order.ASCENDING);
        sortKeys.put("EXTR_DT", Order.ASCENDING);
        sortKeys.put("RXC_IMM_ID", Order.ASCENDING);


        queryProvider.setSortKeys(sortKeys);

        reader.setQueryProvider(queryProvider);

        return reader;
    }*/

   /* @Bean
    public AsyncItemProcessor asyncItemProcessor() throws Exception {
        AsyncItemProcessor<CdcRequestToApi, CdcResponseToDb> asyncItemProcessor = new AsyncItemProcessor();

        asyncItemProcessor.setDelegate(cdcresponseeprocessor);
        asyncItemProcessor.setTaskExecutor(new SimpleAsyncTaskExecutor());
        asyncItemProcessor.afterPropertiesSet();

        return asyncItemProcessor;
    }

    @Bean
    public AsyncItemWriter asyncItemWriter() throws Exception {
        AsyncItemWriter<CdcResponseToDb> asyncItemWriter = new AsyncItemWriter<>();

        asyncItemWriter.setDelegate(cdcResponseDbWriter);
        asyncItemWriter.afterPropertiesSet();

        return asyncItemWriter;
    }*/

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
   /* @Bean
    @Qualifier("masterStep")
    public Step masterStep() {
        return stepBuilderFactory.get("masterStep")
                                 .partitioner("step1", partitioner())
                                 .step(step1())
                                 .taskExecutor(taskExecutor())
                                 .build();
    }*/

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


       executionContext.put("filename",filename);
       // log.info("In Reader");
        return new FlatFileItemReaderBuilder<CdcRequestToApi>()
                .name("personItemReader")
              //  .delimited()
                //.names(new String[] { "firstName", "lastName" })
                .lineTokenizer(new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB) {
                    {
                    setNames(new String[]{"vaxEventId", "extType", "pprlId","recipId","recipFirstName","recipMiddleName","recipLastName","recipDob",
                    "recipSex", "recipAddressStreet", "recipAddressStreet_2", "recipAddressCity", "recipAddressCounty", "recipAddressState", "recipAddressZip", "recipRace1", "recipRace2", "recipRace3",
                    "recipRace4", "recipRace5", "recipRace6", "recipEthnicity", "adminDate", "cvx", "ndc", "mvx", "lotNumber", "vaxExpiration",
                    "vaxAdminSite", "vaxRoute", "doseNum",  "vaxSeriesComplete", "responsibleOrg", "adminName", "vtrcksProvPin", "adminType",
                    "adminAddressStreet", "adminAddressStreet_2", "adminAddressCity", "adminAddressCounty", "adminAddressState", "adminAddressZip",
                   "vaxRefusal", "cmorbidStatus", "serology" });
                }
                })

                .fieldSetMapper(new BeanWrapperFieldSetMapper<CdcRequestToApi>() {
                    {
                        setTargetType(CdcRequestToApi.class);
                    }
                })

                .resource(new  UrlResource(filename))
                .build();
    }

   /* @Bean
    public TaskExecutor taskExecutor(){
        //ThreadPoolExecutor
       // ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //threadPoolTaskExecutor.


        ////////////////////
         SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(2);
        return  simpleAsyncTaskExecutor;
        //return  threadPoolExecutor;
    }*/

    private Map<String, Sort.Direction> sorts;

    /* @Bean
     public ItemStreamReader<CdcRequestToApi> cdcInfoDBReader() {
         *//*JdbcCursorItemReader<CdcRequestToApi> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        //select * from  IMMUNIZATION_INFO  immunizationInfo, cdc_resp_info cdcRespInfo where immunizationInfo.vax_event_id=cdcRespInfo.vax_event_id and cdcRespInfo.status='0'
        reader.setSql("select * from  IMMUNIZATION_INFO  immunizationInfo");
        reader.setRowMapper(((resultSet, employee) -> {
           return CdcRequestToApi.builder().vaxEventId(resultSet.getString("vax_event_id"))
                   .extType(resultSet.getString("ext_type"))
                   .pprlId(resultSet.getString("pprl_id"))
                   .recipId(resultSet.getString("recip_id"))
                   .recipFirstName(resultSet.getString("recip_first_name")).build();
        }));
        return reader;*//*
     *//* RepositoryItemReader<CdcRequestToApi> repositoryItemReader = new RepositoryItemReader<>();
        repositoryItemReader.setRepository(immunizationInfoRepo);
        repositoryItemReader.setMethodName("findAll");
        repositoryItemReader.setSort(sorts);*//*
       // repositoryItemReader.afterPropertiesSet();
        return repositoryItemReader;

    }*/
   /* @Bean
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
    }*/
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

  /*  @Bean
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
    }*/

    @Bean
    @StepScope
    public Resource inputFileResource(@Value("#{jobParameters[fileName]}") final String fileName) {
        return new ClassPathResource(fileName);
    }
/*
    @Bean
    public JdbcBatchItemWriter<Employee> employeeDBWriterDefault() {
        JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into employee (employee_id, first_name, last_name, email, age) values (:employeeId, :firstName, :lastName, :email, :age)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
        return itemWriter;
    }*/
}
