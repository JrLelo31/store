package com.data.store.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.store.dao.ProductRespository;
import com.data.store.dto.ProductDto;
import com.data.store.entities.Product;

@Service
public class ProductService implements IProductService {
	@Autowired
	private ProductRespository productRespository;

	public List<Product> findAll() {
		return productRespository.findAll();
	}

	public Optional<Product> findById(Long id) {
		return productRespository.findById(id);
	}

	@Override
	public ProductDto creerProduit(ProductDto nouveauProduit) {
		ModelMapper modelMapper = new ModelMapper();
		Product produit = modelMapper.map(nouveauProduit, Product.class);
		LocalDateTime createdAt = nouveauProduit.getCreatedAt() != null ? nouveauProduit.getCreatedAt()
				: LocalDateTime.now();
		LocalDateTime updatedAt = nouveauProduit.getCreatedAt() != null ? nouveauProduit.getCreatedAt()
				: LocalDateTime.now();
		produit.setCreatedAt(createdAt);
		produit.setUpdatedAt(updatedAt);
		produit.setName(nouveauProduit.getName());
		produit.setDescription(nouveauProduit.getDescription());
		produit.setPrice(nouveauProduit.getPrice());
		produit.setImgPath(nouveauProduit.getImgPath());
		Product insertProd = productRespository.save(produit);
		ProductDto produtDtoInsert = modelMapper.map(insertProd, ProductDto.class);
		return produtDtoInsert;
	}

	@Override
	public ProductDto recuperProduit(Long idProduit) {
		Optional<Product> _product = productRespository.findById(idProduit);
		Product prod = _product.get();
		ProductDto prodDto = new ProductDto();
		BeanUtils.copyProperties(prod, prodDto);
		return prodDto;
	}

	@Override
	public ProductDto modifierProduit(ProductDto produit) {
		Product productEntity = productRespository.findById(produit.getId()).get();
		productEntity.setName(produit.getName());
		productEntity.setDescription(produit.getDescription());
		productEntity.setPrice(produit.getPrice());
		productEntity.setImgPath(produit.getImgPath());
		LocalDateTime createdAt = produit.getCreatedAt() != null ? produit.getCreatedAt() : LocalDateTime.now();
		LocalDateTime updatedAt = produit.getCreatedAt() != null ? produit.getCreatedAt() : LocalDateTime.now();
		productEntity.setCreatedAt(createdAt);
		productEntity.setUpdatedAt(updatedAt);
		Product modifProd = productRespository.save(productEntity);
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(modifProd, productDto);
		return productDto;
	}

	@Override
	public void supprimerProduit(Long idProduit) {
		Product _product = productRespository.findById(idProduit).get();
		productRespository.delete(_product);

	}

	@Override
	public void supprimerProduit(ProductDto produit) {
		Product _product = productRespository.findById(produit.getId()).get();
		productRespository.delete(_product);

	}

	@Override
	public List<ProductDto> recupererProduits(int page, int limit, String search, int status) {
		// TODO Auto-generated method stub
		return null;
	}
}
