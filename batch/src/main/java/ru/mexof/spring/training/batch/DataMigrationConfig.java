package ru.mexof.spring.training.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration
public class DataMigrationConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource h2DataSource;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public DataMigrationConfig(JobBuilderFactory jobBuilderFactory,
                               StepBuilderFactory stepBuilderFactory,
                               DataSource h2DataSource,
                               MongoTemplate mongoTemplate) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.h2DataSource = h2DataSource;
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public JdbcCursorItemReader<Book> reader() {
        JdbcCursorItemReader<Book> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(h2DataSource);
        reader.setSql("SELECT * FROM BOOKS");
        reader.setRowMapper(new BookRowMapper());

        return reader;
    }

    @Bean
    public ItemProcessor<Book, Book> processor() {
        return book -> {
            book.setAuthor(book.getAuthor() + " " + book.getGenre());
            return book;
        };
    }

    @Bean
    public MongoItemWriter<Book> writer() {
        MongoItemWriter<Book> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);

        return writer;
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<Book, Book>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("migrationFromRelativeDbToNoSQL")
                .start(step())
                .build();
    }
}
