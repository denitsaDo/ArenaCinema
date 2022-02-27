package com.example.arenacinema_springproject.controllers;


import com.example.arenacinema_springproject.models.dto.TypeAddDto;
import com.example.arenacinema_springproject.models.entities.Type;
import com.example.arenacinema_springproject.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TypeController extends BaseController{


    @Autowired
    private TypeService typeService;

    @PostMapping("/types")
    public ResponseEntity<Type> add(@RequestBody TypeAddDto type, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Type t = typeService.add(type);
        return ResponseEntity.ok(t);
    }

    @DeleteMapping("/types/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Type t = typeService.getTypeById(id);
        typeService.delete(t);
    }

    @PutMapping("/types")
    public ResponseEntity<Type> edit(@RequestBody Type type, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        Type t = typeService.edit(type);
        return ResponseEntity.ok(t);
    }

    @GetMapping("/types/{id}")
    public ResponseEntity<Type> getById(@PathVariable int id){
             return ResponseEntity.ok(typeService.getTypeById(id));
    }

    @GetMapping("/types")
    public List getAll(){
         return typeService.getAll();
    }


}
