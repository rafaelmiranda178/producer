package br.com.fiap.integration.producer.integration.bolsa;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface BolsaFamiliaChannel {

    String OUTPUT = "bolsa-familia-exchange";

    @Output(OUTPUT)
    MessageChannel getMessageChannel();
}
