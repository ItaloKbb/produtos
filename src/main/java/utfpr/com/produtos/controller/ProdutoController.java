package utfpr.com.produtos.controller;

import utfpr.com.produtos.client.AuthServiceClient;
import utfpr.com.produtos.dto.request.ProdutoRequestDTO;
import utfpr.com.produtos.dto.response.ProdutoResponseDTO;
import utfpr.com.produtos.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private AuthServiceClient authClient;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestHeader("username") String username,
                                                    @RequestHeader("password") String password,
                                                    @Valid @RequestBody ProdutoRequestDTO dto) {
        validarToken(username, password);
        return ResponseEntity.ok(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@RequestHeader("username") String username,
                                                        @RequestHeader("password") String password,
                                                        @PathVariable String id,
                                                        @Valid @RequestBody ProdutoRequestDTO dto) {
        validarToken(username, password);
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@RequestHeader("username") String username,
                                                          @RequestHeader("password") String password,
                                                          @PathVariable String id) {
        validarToken(username, password);
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos(@RequestHeader("username") String username,
                                                                @RequestHeader("password") String password) {
        validarToken(username, password);
        return ResponseEntity.ok(service.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@RequestHeader("username") String username,
                                        @RequestHeader("password") String password,
                                        @PathVariable String id) {
        validarToken(username, password);
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private void validarToken(String username, String password) {
        // Cria o header Authorization no formato Basic Auth
        String credentials = username + ":" + password;
        String basicAuthHeader = "Basic " + Base64.getEncoder()
                                    .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        // Chama a API de validação
        if (Boolean.FALSE.equals(authClient.validateBasicAuth(basicAuthHeader))) {
            throw new RuntimeException("Credenciais inválidas");
        }
    }
}