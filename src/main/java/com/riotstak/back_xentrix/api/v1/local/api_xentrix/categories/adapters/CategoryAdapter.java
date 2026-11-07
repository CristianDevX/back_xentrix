package com.riotstak.back_xentrix.api.v1.local.api_xentrix.categories.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riotstak.back_xentrix.api.v1.local.api_xentrix.categories.adapters.bd1.Category;
import com.riotstak.back_xentrix.api.v1.local.api_xentrix.categories.adapters.bd1.CategoryRepository;
import com.riotstak.back_xentrix.api.v1.local.utils.ErrorService;

@Service
public class CategoryAdapter {

    private final String myClassName = CategoryAdapter.class.getName();

    @Autowired
    CategoryRepository categoryRepository;

    public Object getAll() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando todos los eventos", myClassName);
        }
    }

    public Object getById(Long id) {
        try {
            return categoryRepository.findById(id);
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error consultando la categoria con id " + id, myClassName);
        }
    }

    public Object create(Category category) {
        try {
            return categoryRepository.save(category).getId();
        } catch (Exception e) {
            return new ErrorService(e.getMessage(), "Error creando el evento ", myClassName);
        }
    }

}
