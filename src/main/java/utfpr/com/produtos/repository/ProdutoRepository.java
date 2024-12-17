package utfpr.com.produtos.repository;


import utfpr.com.produtos.model.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
    // Caso precise de métodos personalizados:
    Produto findByNome(String nome);
}