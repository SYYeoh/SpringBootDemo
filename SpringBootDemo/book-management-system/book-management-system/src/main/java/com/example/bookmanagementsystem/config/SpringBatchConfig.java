package com.example.bookmanagementsystem.config;

import com.example.bookmanagementsystem.model.entity.Book;
import com.example.bookmanagementsystem.repositories.BookRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

  @Autowired
  private JobRepository jobRepository;
  @Autowired
  private PlatformTransactionManager transactionManager;
  @Autowired
  private BookRepository bookRepository;

  @Bean
  public FlatFileItemReader<Book> reader() {
    FlatFileItemReader<Book> itemReader = new FlatFileItemReader<>();
    itemReader.setName("csvReader");
    itemReader.setResource(new FileSystemResource("src/main/resources/books.csv"));
    itemReader.setLinesToSkip(1);
    itemReader.setLineMapper(lineMapper());
    return itemReader;
  }

  @Bean(name = "bookLineMapper")
  public LineMapper<Book> lineMapper() {
    DefaultLineMapper<Book> lineMapper = new DefaultLineMapper<>();
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setDelimiter(",");
    lineTokenizer.setStrict(false);
    lineTokenizer.setNames("id", "title", "author", "publicationYear");

    BeanWrapperFieldSetMapper<Book> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(Book.class);

    lineMapper.setLineTokenizer(lineTokenizer);
    lineMapper.setFieldSetMapper(fieldSetMapper);
    return lineMapper;
  }

  @Bean
  public BookProcessor processor() {
    return new BookProcessor();
  }

  @Bean
  public RepositoryItemWriter<Book> writer(BookRepository bookRepository) {
    RepositoryItemWriter<Book> writer = new RepositoryItemWriter<>();
    writer.setRepository(bookRepository);
    writer.setMethodName("save");
    return writer;
  }

  @Bean
  public Step step1(BookRepository bookRepository, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("csv-step", jobRepository).<Book, Book>chunk(10, transactionManager)
        .reader(reader())
        .processor(processor())
        .writer(writer(bookRepository))
        .taskExecutor(taskExecutor())
        .build();
  }

  @Bean
  public Job job(JobRepository jobRepository) {
    return new JobBuilder("importCustomers", jobRepository)
        .flow(step1(bookRepository, jobRepository, transactionManager)).end().build();
  }

  @Bean
  public TaskExecutor taskExecutor() {
    SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    asyncTaskExecutor.setConcurrencyLimit(10);
    return asyncTaskExecutor;
  }
}
