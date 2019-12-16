package br.com.fiap.integration.producer.integration.bolsa;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@EnableBinding(BolsaFamiliaChannel.class)
public class BolsaFamiliaProducerImpl implements BolsaFamiliaProducer {

    private final BolsaFamiliaChannel channel;

    @Override
    public void produce(BolsaFamiliaEvent event) {
        channel.getMessageChannel().send(MessageBuilder.withPayload(event)
                .setHeader("event_type", event.getEventType().name())
                .build());
    }
}
