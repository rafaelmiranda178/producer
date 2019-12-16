package br.com.fiap.integration.producer.integration.bolsa;

public interface BolsaFamiliaProducer {
    void produce(BolsaFamiliaEvent event);
}
