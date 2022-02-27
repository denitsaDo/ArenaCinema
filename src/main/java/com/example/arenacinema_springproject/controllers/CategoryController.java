package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.CategoryAddDTO;
import com.example.arenacinema_springproject.models.dto.CategoryResponseDTO;
import com.example.arenacinema_springproject.models.entities.Category;
import com.example.arenacinema_springproject.services.CategotyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CategoryController extends BaseController{

    @Autowired
    private CategotyService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseDTO> add(@RequestBody CategoryAddDTO categoryAddDTO, HttpServletRequest request){
        validateLogin(request);
        adminLogin(request);
        CategoryResponseDTO category = categoryService.add(categoryAddDTO);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/categories/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        categoryService.delete(id);
    }

    @GetMapping("/categories/{id}")
    public CategoryResponseDTO getById(@PathVariable int id){
        CategoryResponseDTO category = categoryService.getById(id);
        return category;
    }

    @PutMapping("/categories")
    public ResponseEntity<CategoryResponseDTO> edit(@RequestBody Category category, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        CategoryResponseDTO category1 = categoryService.edit(category);
        return ResponseEntity.ok(category1);
    }

}
