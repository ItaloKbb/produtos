package utfpr.com.produtos.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoResponseDTO {
    private String id;
    private String nome;
    private Double preco;
    private String descricao;
    private String mensagem;
}
