package br.com.casadocodigo.loja.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import br.com.casadocodigo.loja.validation.ProductValidator;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	ProductValidator productValidator;

	@Autowired
	private FileSaver fileSaver;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(productValidator);
	}

	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	//@CacheEvict(value = "lastProduct", allEntries = true)
	public ModelAndView save(@Valid Product product, BindingResult bindResult,
			RedirectAttributes redirectAttributes, MultipartFile summary) {

		if (bindResult.hasErrors()) {
			return form(product);
		}
		String webPath = fileSaver.write("uploaded-summaries", summary);
		product.setSummaryPath(webPath);
		productDAO.save(product);
		redirectAttributes.addFlashAttribute("sucesso",
				"Produto cadastrado com sucesso");
		ModelAndView modelAndView = new ModelAndView("redirect:/products");

		return modelAndView;
	}

	@RequestMapping("/form")
	public ModelAndView form(@ModelAttribute Product product) {
		ModelAndView modelAndView = new ModelAndView("products/form");
		modelAndView.addObject("types", BookType.values());
		modelAndView.addObject("product", product);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)
	@Cacheable(value = "lastProduct")
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("products/list");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ModelAndView show(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("products/show");
		modelAndView.addObject("product", productDAO.find(id));
		return modelAndView;
	}
}
