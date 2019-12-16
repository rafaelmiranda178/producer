package br.com.fiap.integration.producer.integration.bolsa;

import br.com.fiap.integration.producer.core.EventType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BolsaFamiliaEvent {

    private Long id;
    private Integer mesReferencia;
    private Integer mesCompetencia;
    private String uf;
    private Integer codigoMunicipioSiafi;
    private String nomeMunicipio;
    private String nisFavorido;
    private String nomeFavorecido;
    private Double valorParcela;

    @JsonIgnore
    private EventType eventType;

}
