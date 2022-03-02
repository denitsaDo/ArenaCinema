package com.example.arenacinema_springproject.controllers;

import com.example.arenacinema_springproject.models.dto.ProjectionAddDTO;
import com.example.arenacinema_springproject.models.dto.ProjectionByIdDTO;
import com.example.arenacinema_springproject.models.dto.ProjectionEditDTO;
import com.example.arenacinema_springproject.models.dto.ProjectionResponseDTO;
import com.example.arenacinema_springproject.services.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProjectionController extends BaseController{

    @Autowired
    private ProjectionService projectionService;

    @PostMapping("/projections")
    public ResponseEntity<ProjectionResponseDTO> add( @RequestBody  ProjectionAddDTO projection, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        ProjectionResponseDTO p = projectionService.addProjection(projection);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/projections/{id}")
    public ResponseEntity <ProjectionByIdDTO> getById(@PathVariable int id, HttpServletRequest request){
        validateLogin(request);
        ProjectionByIdDTO p = projectionService.getProjectionById(id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/projections")
    public ResponseEntity<ProjectionResponseDTO> edit(@RequestBody ProjectionEditDTO projection, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        ProjectionResponseDTO p = projectionService.editProjection(projection);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/projections/{id}")
    public void delete(@PathVariable int id, HttpServletRequest request) {
        validateLogin(request);
        adminLogin(request);
        projectionService.delete(id);

    }
}
