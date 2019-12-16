package br.com.fiap.integration.producer.bolsa;

import br.com.fiap.integration.producer.core.EventType;
import br.com.fiap.integration.producer.integration.bolsa.BolsaFamiliaEvent;
import br.com.fiap.integration.producer.integration.bolsa.BolsaFamiliaProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BolsaFamiliaImplService implements BolsaFamiliaService {

    private BolsaFamiliaRepository repository;
    private BolsaFamiliaProducer producer;

    @Override
    public List findAll() {
        List<BolsaFamilia> list = repository.findAll();
        publishEvent(list, EventType.LIST);
        return list;
    }

    private void publishEvent(List<BolsaFamilia> list, EventType eventType) {
        list.stream().forEach((b) -> {
            publishEvent(b, eventType);
        });
    }

    private void publishEvent(BolsaFamilia entity, EventType eventType) {
        BolsaFamiliaEvent event = BolsaFamiliaEvent.builder()
                .id(entity.getId())
                .mesReferencia(entity.getMesReferencia())
                .mesCompetencia(entity.getMesCompetencia())
                .uf(entity.getUf())
                .codigoMunicipioSiafi(entity.getCodigoMunicipioSiafi())
                .nomeMunicipio(entity.getNomeMunicipio())
                .nisFavorido(entity.getNisFavorecido())
                .nomeFavorecido(entity.getNomeFavorecido())
                .valorParcela(entity.getValorParcela())
                .eventType(eventType)
                .build();
        producer.produce(event);
    }

}
