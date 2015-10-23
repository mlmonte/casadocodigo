package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;

@Repository
public class ProductDAO {

	final static Logger logger = Logger.getLogger(ProductDAO.class);

	@PersistenceContext
	private EntityManager manager;

	public void save(Product product) {

		// logger.info("Descricao do produto " + product.getDescrition());
		// logger.info("Numeor de paginas: " + product.getNumberOfPages());

		manager.persist(product);

		// logger.info("Produto cadastrado com sucesso");
	}

	public List<Product> list() {
		
		logger.info("ProductDAO - Listando os Produtos");
		
		return manager.createQuery(
				"select distinct(p) from Product p join fetch p.prices",
				Product.class).getResultList();
	}

	public boolean existsTitle(Product product) {
		Query query = manager
				.createQuery("select p from Product p where p.title = :title");
		query.setParameter("title", product.getTitle());
		return !query.getResultList().isEmpty();
	}

	public Product find(Integer id) {
		return manager
				.createQuery(
						"select distinct(p) from Product p join fetch p.prices where p.id = :id",
						Product.class).setParameter("id", id).getSingleResult();
	}
	
	public BigDecimal sumPricesPerType(BookType bookType) {
		TypedQuery<BigDecimal> query = manager.createQuery("select sum(price.value) from Product p "
				+ "join p.prices price where price.bookType = :bookType", BigDecimal.class);
		query.setParameter("bookType", bookType);
		return query.getSingleResult();
	}

}
