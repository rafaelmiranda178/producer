package br.com.fiap.integration.producer.config;

import br.com.fiap.integration.producer.bolsa.BolsaFamilia;
import org.springframework.batch.item.ItemProcessor;

public class DBLogProcessor implements ItemProcessor<BolsaFamilia, BolsaFamilia>{
    public BolsaFamilia process(BolsaFamilia bolsaFamilia) throws Exception
    {
        System.out.println("Inserting bolsa familia : " + bolsaFamilia);
        return bolsaFamilia;
    }
}
