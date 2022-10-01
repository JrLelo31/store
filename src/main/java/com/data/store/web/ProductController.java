package com.data.store.web;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.store.dto.ProductDto;
import com.data.store.entities.Product;
import com.data.store.service.ProductService;
import com.data.store.userRequest.ProductRequest;
import com.data.store.userResponse.ProductResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProductController {
	private static Logger log = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.ok(productService.findAll());
	}

	@PostMapping(path = "/product", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest product) {
		ModelMapper modelMapper = new ModelMapper();
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		ProductDto productInsert = productService.creerProduit(productDto);
		ProductResponse productResponse = modelMapper.map(productInsert, ProductResponse.class);
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.CREATED);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
		ProductDto prodDto = productService.recuperProduit(id);

		ProductResponse userResponse = new ProductResponse();

		BeanUtils.copyProperties(prodDto, userResponse);

		return new ResponseEntity<ProductResponse>(userResponse, HttpStatus.OK);
	}

	@PutMapping(path = "/product/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
			@Valid @RequestBody ProductRequest product) {

		ProductDto productDto = new ProductDto();

		BeanUtils.copyProperties(product, productDto);

		productDto.setId(id);

		ProductDto updateProduct = productService.modifierProduit(productDto);

		ProductResponse updateResponse = new ProductResponse();

		BeanUtils.copyProperties(updateProduct, updateResponse);

		return new ResponseEntity<ProductResponse>(updateResponse, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
		if (!productService.findById(id).isPresent()) {
			log.error("Id " + id + " is not existed");
			ResponseEntity.badRequest().build();
		}

		productService.supprimerProduit(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
