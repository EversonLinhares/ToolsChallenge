package br.com.toolschallenge.api.controller;

import br.com.toolschallenge.api.dto.request.PagamentoRequestDTO;
import br.com.toolschallenge.api.dto.response.PagamentoResponseDTO;
import br.com.toolschallenge.domain.service.TransacaoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamento")
@RequiredArgsConstructor
public class PagamentoController {

    private final TransacaoService transacaoService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoResponseDTO> realizarPagamento(
            @RequestBody @Valid PagamentoRequestDTO pagamentoRequestDTO, HttpServletRequest request) {
        PagamentoResponseDTO pagamentoResponseDTO = transacaoService.realizarPagamento(pagamentoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoResponseDTO);
    }


//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Estorno Realizado com sucesso."),
//            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
//    })
//    @PutMapping(value = "/estorno/{id}" , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Transacao> realizarEstorno(@PathVariable String id, @RequestBody PagamentoRequestDTO pagamentoRequestDTO) {
//
//        return ResponseEntity.ok().body(transacao);
//    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações encontradas com sucesso."),
            @ApiResponse(responseCode = "204", description = "Não existem registros de transações."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @GetMapping(value = "/pesquisar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PagamentoResponseDTO>> pesquisarTransacoes(HttpServletRequest request) {
        List<PagamentoResponseDTO> pagamentoResponseDTOS = transacaoService.pesquisarTransacoes();
        return ResponseEntity.status(pagamentoResponseDTOS.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .body(pagamentoResponseDTOS);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado uma transação com o id informado."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.")
    })
    @GetMapping(value = "/pesquisar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoResponseDTO> pesquisarPorId(@PathVariable Long id, HttpServletRequest request) {
       PagamentoResponseDTO pagamentoResponseDTO = transacaoService.pesquisarPorId(id);

        return ResponseEntity.ok().body(pagamentoResponseDTO);
    }

}
