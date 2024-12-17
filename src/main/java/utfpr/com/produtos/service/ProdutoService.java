package utfpr.com.produtos.service;

import utfpr.com.produtos.dto.request.ProdutoRequestDTO;
import utfpr.com.produtos.dto.response.ProdutoResponseDTO;
import utfpr.com.produtos.exception.CustomException;
import utfpr.com.produtos.model.Produto;
import utfpr.com.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setDescricao(dto.getDescricao());

        Produto salvo = repository.save(produto);

        return converterParaResponse(salvo, "Produto criado com sucesso!");
    }

    public ProdutoResponseDTO atualizar(String id, ProdutoRequestDTO dto) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new CustomException("Produto não encontrado", HttpStatus.NOT_FOUND));

        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setDescricao(dto.getDescricao());

        Produto atualizado = repository.save(produto);
        return converterParaResponse(atualizado, "Produto atualizado com sucesso!");
    }

    public ProdutoResponseDTO buscarPorId(String id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new CustomException("Produto não encontrado", HttpStatus.NOT_FOUND));
        return converterParaResponse(produto, "Produto encontrado com sucesso!");
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(produto -> converterParaResponse(produto, ""))
                .collect(Collectors.toList());
    }

    public void deletar(String id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new CustomException("Produto não encontrado", HttpStatus.NOT_FOUND));
        repository.delete(produto);
    }

    private ProdutoResponseDTO converterParaResponse(Produto produto, String mensagem) {
        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .descricao(produto.getDescricao())
                .mensagem(mensagem)
                .build();
    }
}