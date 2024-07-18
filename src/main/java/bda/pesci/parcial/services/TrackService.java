package bda.pesci.parcial.services;

import bda.pesci.parcial.entities.Track;
import bda.pesci.parcial.entities.dtos.TrackDTO;

import java.util.List;

public interface TrackService {
    List<Track> findAll();

    Track getById(Integer id);

    Track delete(Integer id);

    Track modify(Integer id, TrackDTO entity);

    Track add(TrackDTO entity);

    List<Track> getByArtistIdAndGenreId(Integer artistId, Integer genreId);
}
