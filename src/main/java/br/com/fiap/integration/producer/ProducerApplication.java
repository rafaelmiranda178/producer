package br.com.fiap.integration.producer;

import br.com.fiap.integration.producer.bolsa.BolsaFamilia;
import br.com.fiap.integration.producer.util.CsvUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class ProducerApplication {


    private CsvUtil csvUtil;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            List lista = csvUtil.loadObjectList(BolsaFamilia.class);
            System.out.println(lista);
        };
    }

}
