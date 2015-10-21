package br.com.casadocodigo.loja.validation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.Product;

@Component
public class ProductValidator implements Validator {

	final static Logger logger = Logger.getLogger(ProductValidator.class);
	@Autowired
	ProductDAO productDAO;

	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
				"field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
				"field.required");
		Product product = (Product) target;
		
		logger.info(product.getTitle());

		if (productDAO.existsTitle(product)) {
			errors.rejectValue("title", "title.unique");
		}
		if (product.getNumberOfPages() < 30) {
			errors.rejectValue("numberOfPages", "field.required");
		}
	}
}
