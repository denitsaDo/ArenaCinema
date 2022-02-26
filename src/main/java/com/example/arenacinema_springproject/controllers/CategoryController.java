package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.CategoryAddDTO;
import com.example.arenacinema_springproject.models.dto.CategoryResponseDTO;
import com.example.arenacinema_springproject.models.entities.Category;
import com.example.arenacinema_springproject.models.entities.City;
import com.example.arenacinema_springproject.services.CategotyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CategoryController{

    @Autowired
    private CategotyService categoryService;


    @PostMapping("/categories")
    public ResponseEntity<CategoryAddDTO> add(@RequestBody CategoryAddDTO categoryAddDTO, HttpServletRequest request){
        return ResponseEntity.ok(categoryService.add(categoryAddDTO, request));
    }

    @DeleteMapping("/categories/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        categoryService.delete(id, request);
    }

    @GetMapping("/categories/{id}")
    public CategoryResponseDTO getById(@PathVariable int id){
        return categoryService.getById(id);
    }

    @PutMapping("/categories")
    public ResponseEntity<CategoryResponseDTO> edit(@RequestBody Category category, HttpServletRequest request) {
        return ResponseEntity.ok(categoryService.edit(category, request));
    }

}
