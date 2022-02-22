package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.CategoryAddDTO;
import com.example.arenacinema_springproject.models.entities.Category;
import com.example.arenacinema_springproject.models.entities.City;
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
    public ResponseEntity<Category> add(@RequestBody CategoryAddDTO categoryAddDTO, HttpServletRequest request){
        validateLogin(request);
        adminLogin(request);
        String name = categoryAddDTO.getName();
        String description = categoryAddDTO.getDescription();
        Category category = categoryService.add(name, description);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/categories/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Category category = categoryService.getById(id);
        categoryService.delete(category);
    }

    @GetMapping("/categories/{id}")
    public Category getById(@PathVariable int id){
        Category category = categoryService.getById(id);
        return category;
    }

    @PutMapping("/categories")
    public ResponseEntity<Category> edit(@RequestBody Category category, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Category category1 = categoryService.edit(category);
        return ResponseEntity.ok(category1);
    }

}
