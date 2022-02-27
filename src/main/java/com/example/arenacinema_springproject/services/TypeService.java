package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NoContentException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.TypeAddDto;
import com.example.arenacinema_springproject.models.entities.Type;
import com.example.arenacinema_springproject.models.repositories.TypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Type add(TypeAddDto type) {
        String name = type.getName();
        if (name == null || name.isBlank()){
            throw new BadRequestException("Type is mandatory!");
        }
        if (typeRepository.findByName(name)!= null) {
            throw new BadRequestException("Type already exists!");
        }
        Type t = new Type();
        t.setName(name);
        typeRepository.save(t);
        return t;
    }

    public Type getTypeById(int id) {
        Optional<Type> type = typeRepository.findById(id);
        if (type.isPresent()) {
            return type.get();
        }
        else {
            throw new NotFoundException("Type not found");
        }
    }

    public void delete(Type type) {
        Optional<Type> optional = typeRepository.findById(type.getId());
        if (optional.isPresent() ) {
            typeRepository.delete(type);
            throw new NoContentException();
        }
        else {
            throw new NotFoundException("Type not found.");
        }
    }

    public Type edit(Type type) {
        Optional<Type> opt = typeRepository.findById(type.getId());
        if(opt.isPresent()){
            typeRepository.save(type);
            return type;
        }
        else{
            throw new NotFoundException("Type not found.");
        }
    }


    public List<Type> getAll() {
       return typeRepository.findAll();
    }
}
