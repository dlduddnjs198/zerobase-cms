package com.zerobase.cms.order.service;

import com.zerobase.cms.order.domain.model.Product;
import com.zerobase.cms.order.domain.model.ProductItem;
import com.zerobase.cms.order.domain.product.AddProductForm;
import com.zerobase.cms.order.domain.product.UpdateProductForm;
import com.zerobase.cms.order.domain.product.UpdateProductItemForm;
import com.zerobase.cms.order.domain.repository.ProductRepository;
import com.zerobase.cms.order.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zerobase.cms.order.exception.ErrorCode.NOT_FOUND_ITEM;
import static com.zerobase.cms.order.exception.ErrorCode.NOT_FOUND_PRODUCT;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product addProduct(Long sellerId, AddProductForm form){
        return productRepository.save(Product.of(sellerId, form));
    }

    @Transactional
    public Product updateProduct(Long sellerId, UpdateProductForm form){
        Product product = productRepository.findBySellerIdAndId(sellerId, form.getId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));
        product.setName(form.getName());
        product.setDescription(form.getDescription());

        for(UpdateProductItemForm itemForm: form.getItems()){
            // item을 못찾으면 던지고
            ProductItem item = product.getProductItems().stream()
                    .filter(pi->pi.getId().equals(itemForm.getId()))
                    .findFirst().orElseThrow(() -> new CustomException(NOT_FOUND_ITEM));
            // 찾으면 값을 바꾼다.
            item.setName(itemForm.getName());
            item.setCount(itemForm.getCount());
            item.setPrice(itemForm.getPrice());
        }
        return product;
    }

    @Transactional
    public void deleteProduct(long sellerId, Long productId){
        Product product = productRepository.findBySellerIdAndId(sellerId, productId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PRODUCT));
        productRepository.delete(product);

    }
}
