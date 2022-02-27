package com.example.arenacinema_springproject.services;

import com.example.arenacinema_springproject.exceptions.BadRequestException;
import com.example.arenacinema_springproject.exceptions.NoContentException;
import com.example.arenacinema_springproject.exceptions.NotFoundException;
import com.example.arenacinema_springproject.models.dto.CinemaWithoutCityDTO;
import com.example.arenacinema_springproject.models.dto.CityAddDTO;
import com.example.arenacinema_springproject.models.dto.CityWithCinemasDTO;
import com.example.arenacinema_springproject.models.dto.CityWithoutCinemasDTO;
import com.example.arenacinema_springproject.models.entities.Cinema;
import com.example.arenacinema_springproject.models.entities.City;
import com.example.arenacinema_springproject.models.repositories.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ModelMapper modelMapper;

    public City add(CityAddDTO city) {
        if (city.getName() == null || city.getName().isBlank()){
            throw new BadRequestException("City name is mandatory!");
        }
        if (cityRepository.findByName(city.getName())!= null) {
            throw new BadRequestException("City already exists!");
        }
        City c = modelMapper.map(city, City.class);
        cityRepository.save(c);
        return c;
    }

    public City getById(int id) {
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            return city.get();
        }
        else {
            throw new NotFoundException("City not found");
        }
    }

    public void delete(City city) {
        Optional<City> optional = cityRepository.findById(city.getId());   //check if id is >0
        if (optional.isPresent() ) {
            cityRepository.delete(city);
            throw new NoContentException();
        }
        else {
            throw new NotFoundException("No such city.");
        }
    }

    public City edit(City city) {
        Optional<City> opt = cityRepository.findById(city.getId());
        if(opt.isPresent()){
            cityRepository.save(city);
            return city;
        }
        else{
            throw new NotFoundException("City not found.");
        }
    }

    public CityWithCinemasDTO getCityById(int id) {
        Optional<City> opt = cityRepository.findById(id);
        if (opt.isPresent()){
            City city = opt.get();
            CityWithCinemasDTO dto = modelMapper.map(city, CityWithCinemasDTO.class);
            Set<Cinema> cinemas = city.getTownCinemas();
            dto.setCinemasInTown(cinemas.stream().map(c -> modelMapper.map(c, CinemaWithoutCityDTO.class)).collect(Collectors.toList()));
            return dto;
        }
        else throw new NotFoundException("City not found");

    }

    public List<CityWithoutCinemasDTO> getAll() {
        List<City> cities = cityRepository.findAll();
        List<CityWithoutCinemasDTO> dto = cities.stream().map(city -> modelMapper.map(city, CityWithoutCinemasDTO.class)).collect(Collectors.toList());
        return dto;
    }


}
