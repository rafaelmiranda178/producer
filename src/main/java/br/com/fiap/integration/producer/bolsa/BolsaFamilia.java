package br.com.fiap.integration.producer.bolsa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "bolsa_familia_")
public class BolsaFamilia {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer mesReferencia;
    private Integer mesCompetencia;
    private String uf;
    private Integer codigoMunicipioSiafi;
    private String nomeMunicipio;
    private String nisFavorecido;
    private String nomeFavorecido;
    private Double valorParcela;

}
