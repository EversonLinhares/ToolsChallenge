package br.com.toolschallenge.api.controller;

import br.com.toolschallenge.api.dto.request.SaldoRequestDTO;
import br.com.toolschallenge.api.dto.response.SaldoResponseDTO;
import br.com.toolschallenge.config.modelmapper.MapperConvert;
import br.com.toolschallenge.domain.service.SaldoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pagamento/saldo")
public class SaldoController {

    private final SaldoService service;
    private final MapperConvert mapperConvert;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @PutMapping(value = "/adicionar/{id}" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaldoResponseDTO> editarSaldo(@PathVariable Long id, @Valid @RequestBody SaldoRequestDTO saldoRequestDTO) {
            SaldoResponseDTO saldoResponseDTO = service.editarSaldo(id,saldoRequestDTO);
        return ResponseEntity.ok().body(saldoResponseDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado saldo com o id informado."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @GetMapping(value = "/pesquisar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaldoResponseDTO> pesquisarPorId(@PathVariable Long id, HttpServletRequest request) {
        SaldoResponseDTO saldoResponseDTO = mapperConvert.mapEntityToDto(service.pesquisarPorId(id), SaldoResponseDTO.class);
        return ResponseEntity.ok().body(saldoResponseDTO);
    }

}
