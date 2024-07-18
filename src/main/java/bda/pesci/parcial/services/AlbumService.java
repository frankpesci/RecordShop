package bda.pesci.parcial.services;

import bda.pesci.parcial.entities.Album;
import bda.pesci.parcial.entities.dtos.AlbumDTO;

import java.util.List;

public interface AlbumService {

    Album getById(Integer id);

    List<Album> findAll();

    Album delete(Integer id);

    Album modify(Integer id, AlbumDTO entity);


    Album add(AlbumDTO entity);
}
