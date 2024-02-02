package br.com.toolschallenge.config.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperConvert {

    @Autowired
    ModelMapper modelMapper;

    public <D, E> D mapEntityToDto(E entities, Class<D> dtoClass){
        return modelMapper.map(entities, dtoClass);
    }

    public <D, E> E mapDtoToEntity(D dto, Class<E> entitiesClass){
        return modelMapper.map(dto,entitiesClass);
    }

    public <D, E> List<D> collectionToDto(List<E> listEntities, Class<D> dtoClass){
        return listEntities.stream().map(l -> mapEntityToDto(l,dtoClass)).collect(Collectors.toList());
    }

}
