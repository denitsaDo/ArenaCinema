package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NoContentException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.entities.Category;
import com.example.arenacinema_springproject.models.entities.City;
import com.example.arenacinema_springproject.models.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategotyService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category add(String name, String description) {
        if (name == null || name.isBlank()){
            throw new BadRequestException("Category name is mandatory!");
        }
        if (categoryRepository.findByName(name)!= null) {
            throw new BadRequestException("Category already exists!");
        }
        if (description == null || description.isBlank()){
            throw new BadRequestException("Description name is mandatory!");
        }
        if (categoryRepository.findByDescription(description)!= null) {
            throw new BadRequestException("Description already exists!");
        }
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        categoryRepository.save(category);
        return category;
    }

    public Category getById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        else {
            throw new NotFoundException("Category not found");
        }
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public Category edit(Category category) {
        Optional<Category> cat = categoryRepository.findById(category.getId());
        if(cat.isPresent()){
            categoryRepository.save(category);
            return category;
        }
        else{
            throw new NotFoundException("Category not found.");
        }
    }
}
