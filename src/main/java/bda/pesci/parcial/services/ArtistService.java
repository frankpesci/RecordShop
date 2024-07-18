package bda.pesci.parcial.services;

import bda.pesci.parcial.entities.Artist;
import bda.pesci.parcial.entities.dtos.ArtistDTO;

import java.util.List;

public interface ArtistService {

    List<Artist> findAll();

    Artist getById(Integer id);

    Artist delete(Integer id);

    Artist add(ArtistDTO entity);

    Artist modify(Integer id, ArtistDTO entity);
}
