package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.entities.Category;
import com.example.arenacinema_springproject.models.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategotyService {

    @Autowired
    private CategoryRepository categoryRepository;

    public static final int MAX_LENGTH = 30;

    public Category add(String name, String description) {
        if (name == null || name.isBlank()){
            throw new BadRequestException("Category name is mandatory!");
        }
        if (name.length() >MAX_LENGTH){
            throw new BadRequestException("Category name is too long!");
        }
        if (categoryRepository.findByName(name)!= null) {
            throw new BadRequestException("Category already exists!");
        }
        if (description == null || description.isBlank()){
            throw new BadRequestException("Description name is mandatory!");
        }
        if (description.length() >MAX_LENGTH){
            throw new BadRequestException("Description is too long!");
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
        return  categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category not found!"));
    }

    public void delete(Category category) {
        categoryRepository.delete(categoryRepository.findById(category.getId()).orElseThrow(() -> new NotFoundException("Category not found!")));
    }

    public Category edit(Category category) {
        String name = category.getName();
        String description = category.getDescription();
        if (name == null || name.isBlank()){
            throw new BadRequestException("Category name is mandatory!");
        }
        if (name.length() >MAX_LENGTH){
            throw new BadRequestException("Category name is too long!");
        }
        if (categoryRepository.findByName(name)!= null) {
            throw new BadRequestException("Category already exists!");
        }
        if (description == null || description.isBlank()){
            throw new BadRequestException("Description name is mandatory!");
        }
        if (description.length() >MAX_LENGTH){
            throw new BadRequestException("Description is too long!");
        }
        if (categoryRepository.findByDescription(description)!= null) {
            throw new BadRequestException("Description already exists!");
        }
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
