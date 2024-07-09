package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);
        Product saved = productRepository.save(product);
        return mapper.map(saved,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(product.getPrice());
        product.setDiscountedPrice(product.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        Product savedProduct = productRepository.save(product);
        ProductDto productDto1 = mapper.map(product, ProductDto.class);

        return productDto1;
    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found"));
        productRepository.delete(product);

    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable page= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> productPage = productRepository.findAll(page);
        return Helper.getPageableResponse(productPage,ProductDto.class);
    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found"));
        ProductDto mapped = mapper.map(product, ProductDto.class);
        return mapped;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable page= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> productPage = productRepository.findByLive(page);
        return Helper.getPageableResponse(productPage,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable page= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> productPage = productRepository.findByTitleContaining(subTitle,page);
        return Helper.getPageableResponse(productPage,ProductDto.class);
    }
}
