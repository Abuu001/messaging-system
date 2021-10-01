package com.trimeo.Uploadservice.config.excellBatchFileConfigs;

import com.trimeo.Uploadservice.amqp.BatchConfigProperties;
import com.trimeo.Uploadservice.config.csvBatchFileConfig.JobCompletionNotificationListener;
import com.trimeo.Uploadservice.domains.UploadMessage;
import com.trimeo.Uploadservice.service.FileDataReaderImp;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

@Configuration
public class ExcellBachConfig { 
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BatchConfigProperties batchConfigProperties;

    @Autowired
    private FileDataReaderImp fileDataReaderImp;

    @Bean
    ItemReader<UploadMessage> excelFileReader() {
        PoiItemReader<UploadMessage> reader = new PoiItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new FileSystemResource(fileDataReaderImp.isFileCsvOrExcell("excellFile") ? batchConfigProperties.getPROPERTY_SOURCE_FILE_PATH() : null));
        reader.setRowMapper(excelRowMapper());

        return reader;
    }

    private RowMapper<UploadMessage> excelRowMapper() {
        return new UploadExcelRowMapper();
    }

    @Bean
    public static ExcellUploadProcessor excellFileProcessor() {
        return new ExcellUploadProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<UploadMessage> excellFileWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<UploadMessage>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO upload_message (id,destination ,message,message_type,source_address,no_of_sends,send_time ) "
                        + " VALUES (:id, :destination, :message, :messageType, :sourceAddress, :noOfSends ,:sendTime)")
                .dataSource(dataSource).build();
    }

    @Bean
    public Job importExcellJob(JobCompletionNotificationListener listener, Step step2) {
        return jobBuilderFactory
                .get("importExcellJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step2)
                .end()
                .build();
    }

    @Bean
    public Step step2(JdbcBatchItemWriter<UploadMessage> excellFileWriter) {
        return stepBuilderFactory
                .get("step2")
                .<UploadMessage, UploadMessage>chunk(batchConfigProperties.getChunckSize())
                .reader(excelFileReader())
                .processor(excellFileProcessor())
                .writer(excellFileWriter)
                .build();
    }

}
