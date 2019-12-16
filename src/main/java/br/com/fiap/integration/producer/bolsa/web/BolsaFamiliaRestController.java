package br.com.fiap.integration.producer.bolsa.web;

import br.com.fiap.integration.producer.bolsa.BolsaFamilia;
import br.com.fiap.integration.producer.bolsa.BolsaFamiliaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bolsa-familia")
public class BolsaFamiliaRestController {

    private final BolsaFamiliaService service;

    @GetMapping
    List<BolsaFamilia> findAll(){
        return service.findAll();
    }

}
