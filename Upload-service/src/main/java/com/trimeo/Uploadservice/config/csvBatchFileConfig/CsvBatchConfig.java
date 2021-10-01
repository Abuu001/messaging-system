package com.trimeo.Uploadservice.config.csvBatchFileConfig;

import com.trimeo.Uploadservice.amqp.BatchConfigProperties;
import com.trimeo.Uploadservice.domains.UploadMessage;
import com.trimeo.Uploadservice.service.FileDataReaderImp;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class CsvBatchConfig {

    private final String[] FIELD_NAMES = new String[] {"destination" ,"message"};

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BatchConfigProperties batchConfigProperties;

    @Autowired
    private FileDataReaderImp fileDataReaderImp;

    @Bean
    public FlatFileItemReader<UploadMessage> reader() {
        return new FlatFileItemReaderBuilder<UploadMessage>().name("UploadItemReader")
                .resource(new FileSystemResource(fileDataReaderImp.isFileCsvOrExcell("csvFile") ? "" : batchConfigProperties.getPROPERTY_SOURCE_FILE_PATH()))
                .delimited()
                .names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<UploadMessage>() {
                    {
                        setTargetType(UploadMessage.class);
                    }
                }).build();
    }

    @Bean
    public CsvUploadProcessor csvFileProcessor() {
        return new CsvUploadProcessor();
    }

    public JdbcBatchItemWriter<UploadMessage> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<UploadMessage>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO upload_message (id,message,message_type,destination,no_of_sends,send_time,source_address ) "
                        + " VALUES (:id, :message, :messageType ,:destination,:noOfSends ,:sendTime, :sourceAddress)")
                .dataSource(dataSource).build();
    }

    @Bean
    public Job importCsvJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory
                .get("importCsvJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<UploadMessage> writer) {
        return stepBuilderFactory
                .get("step1")
                .<UploadMessage, UploadMessage>chunk(batchConfigProperties.getChunckSize())
                .reader(reader())
                .processor(csvFileProcessor())
                .writer(writer)
                .build();
    }

}
