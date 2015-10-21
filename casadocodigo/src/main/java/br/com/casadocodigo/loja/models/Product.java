package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/* toda entidade precisa ter uma chave para hibernate
 * a anotacao @transient nao cria a coluna no banco... 
 * se a coluna do banco e diferente do atributo entao da pra forcar 
 * o nome com @column (name="nm_colula") */

@Entity
public class Product {

	@ElementCollection
	private List<Price> prices = new ArrayList<Price>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	// ou @NotEmpety
	private String title;

	@Lob
	@NotBlank(message = "field.required.notblank")
	private String description;

	@Min(30)
	private int numberOfPages;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Calendar releaseDate;

	private String summaryPath;

	public Calendar getReleaseDate() {
		return releaseDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setReleaseDate(Calendar releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getSummaryPath() {
		return summaryPath;
	}

	public void setSummaryPath(String summaryPath) {
		this.summaryPath = summaryPath;
	}

	public BigDecimal priceFor(BookType bookType) {
		return prices.stream()
				.filter(price -> price.getBookType().equals(bookType))
				.findFirst().get().getValue();
	}

}
