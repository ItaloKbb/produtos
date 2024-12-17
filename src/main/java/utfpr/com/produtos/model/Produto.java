package utfpr.com.produtos.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "produtos")
public class Produto {
    @Id
    private String id;
    private String nome;
    private Double preco;
    private String descricao;
}