package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.CategoryResponseDTO;
import com.example.arenacinema_springproject.models.entities.Category;
import com.example.arenacinema_springproject.models.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategotyService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public static final int MAX_LENGTH = 50;

    public CategoryResponseDTO add(String name, String description) {
        if (name == null || name.isBlank()){
            throw new BadRequestException("Category name is mandatory!");
        }
        if (name.length() > MAX_LENGTH){
            throw new BadRequestException("Category name is too long!");
        }
        if (categoryRepository.findByName(name)!= null) {
            throw new BadRequestException("Category already exists!");
        }
        if (description == null || description.isBlank()){
            throw new BadRequestException("Description name is mandatory!");
        }
        if (description.length() > MAX_LENGTH){
            throw new BadRequestException("Description is too long!");
        }
        if (categoryRepository.findByDescription(description)!= null) {
            throw new BadRequestException("Description already exists!");
        }
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        categoryRepository.save(category);
        CategoryResponseDTO dto = modelMapper.map(category, CategoryResponseDTO.class);
        return dto;
    }

    public CategoryResponseDTO getById(int id) {
        Category c = categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category not found!"));
        CategoryResponseDTO dto = modelMapper.map(c, CategoryResponseDTO.class);
        return  dto;
    }

    public void delete(int id) {
        categoryRepository.delete(categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found")));
    }

    public CategoryResponseDTO edit(Category category) {
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
            CategoryResponseDTO dto = modelMapper.map(category, CategoryResponseDTO.class);
            return dto;
        }
        else{
            throw new NotFoundException("Category not found.");
        }
    }
}
