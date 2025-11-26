package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.CollectionPointRequestDto;
import com.dog_feliz.user_service.controller.dto.CollectionPointResponseDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.CollectionPointEntity;
import com.dog_feliz.user_service.repository.AddressRepository;
import com.dog_feliz.user_service.repository.CollectionPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class CollectionPointService {

    @Autowired
    private CollectionPointRepository collectionPointRepository;

    @Autowired
    private AddressRepository addressRepository;

    // C - cadastro de um ponto de coleta //////////////
    public CollectionPointResponseDto addCollectionPoint(CollectionPointRequestDto collectionPointRequest){
        // primeiro a gente salva o endereço do ponto de coleta
        AddressEntity address = addressRepository.save(new AddressEntity(collectionPointRequest.getAddress()));

        // depois a gente salva o ponto de coleta junto com o endereço cadastrado
        CollectionPointEntity collectionPoint = collectionPointRepository.save(new CollectionPointEntity(collectionPointRequest.getName(), address));

        return new CollectionPointResponseDto(collectionPoint);
    }

    // R - ver todos os pontos de coleta cadastrados //////////////
    public List<CollectionPointResponseDto> getCollectionPoints(){

        List<CollectionPointEntity> collectionPoints = collectionPointRepository.findAll();

        return collectionPoints.stream().map(CollectionPointResponseDto::new).toList();
    }
    // R - ver um ponto de coleta pelo id //////////////
    public CollectionPointResponseDto getCollectionPointById(Long id){
        CollectionPointEntity collectionPoint = collectionPointRepository.findById(id)
        .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
        "Ponto de coleta não encontrado com id: " + id));
        return new CollectionPointResponseDto(collectionPoint);
    }

    // U - atualizar um ponto de coleta //////////////
    public CollectionPointResponseDto updateCollectionPoint(Long id, CollectionPointRequestDto collectionPointRequest) {
        // primeiro buscamos o ponto de coleta a ser atualizado
        CollectionPointEntity existingCollectionPoint = collectionPointRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
                        "Ponto de coleta não foi encontrado com o id: " + id));

        // atualizamos o nome
        existingCollectionPoint.setName(collectionPointRequest.getName());

        // atualizamos o endereço (cria um novo ou atualiza o que ja existe)
        AddressEntity existingAddress = existingCollectionPoint.getAddress();
        AddressEntity updatedAddress = addressRepository.save(new AddressEntity(existingAddress.getId(),
                collectionPointRequest.getAddress())
        );

        existingCollectionPoint.setAddress(updatedAddress);

        // salvamos todas as alterações
        CollectionPointEntity updatedCollectionPoint = collectionPointRepository.save(existingCollectionPoint);

        return new CollectionPointResponseDto(updatedCollectionPoint);
    }

    // D - deletar um ponto de coleta //////////////
    public void deleteCollectionPoint(Long id) {
        if (!collectionPointRepository.existsById(id)) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                    "Ponto de coleta não foi encontrado com id: " + id);
        }
        collectionPointRepository.deleteById(id);
    }
}
