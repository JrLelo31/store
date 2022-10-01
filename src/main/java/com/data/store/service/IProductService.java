package com.data.store.service;

import java.util.List;

import com.data.store.dto.ProductDto;

public interface IProductService {

	ProductDto creerProduit(ProductDto nouveauProduit);

	ProductDto recuperProduit(Long idProduit);

	ProductDto modifierProduit(ProductDto produit);

	void supprimerProduit(Long idProduit);

	void supprimerProduit(ProductDto produit);

	List<ProductDto> recupererProduits(int page, int limit, String search, int status);
}
