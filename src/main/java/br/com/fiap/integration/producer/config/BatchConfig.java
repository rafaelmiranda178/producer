package br.com.fiap.integration.producer.config;

import br.com.fiap.integration.producer.bolsa.BolsaFamilia;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    @Value("classPath:/input/bolsa_99_lines.csv")
    private Resource inputResource;

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .<BolsaFamilia, BolsaFamilia>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemProcessor<BolsaFamilia, BolsaFamilia> processor() {
        return new DBLogProcessor();
    }

    @Bean
    public FlatFileItemReader<BolsaFamilia> reader() {
        FlatFileItemReader<BolsaFamilia> itemReader = new FlatFileItemReader<BolsaFamilia>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public LineMapper<BolsaFamilia> lineMapper() {
        DefaultLineMapper<BolsaFamilia> lineMapper = new DefaultLineMapper<BolsaFamilia>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("mesReferencia",
                "mesCompetencia",
                "uf",
                "codigoMunicipioSiafi",
                "nomeMunicipio",
                "nisFavorecido",
                "nomeFavorecido",
                "valorParcela");
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4, 5, 6, 7);
        BeanWrapperFieldSetMapper<BolsaFamilia> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BolsaFamilia.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public JdbcBatchItemWriter<BolsaFamilia> writer() {
        JdbcBatchItemWriter<BolsaFamilia> itemWriter = new JdbcBatchItemWriter<BolsaFamilia>();
        itemWriter.setDataSource(dataSource());
        itemWriter.setSql("INSERT INTO EMPLOYEE (" +
                "mesReferencia, " +
                "mesCompetencia, " +
                "uf, " +
                "codigoMunicipioSiafi, " +
                "nomeMunicipio, " +
                "nisFavorecido, " +
                "nomeFavorecido, " +
                "valorParcela) VALUES (" +
                ":mesReferencia, " +
                ":uf, " +
                ":codigoMunicipioSiafi, " +
                ":nomeMunicipio, " +
                ":nisFavorecido, " +
                ":nomeFavorecido, " +
                ":valorParcela)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<BolsaFamilia>());
        return itemWriter;
    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .addScript("classpath:employee.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

}
