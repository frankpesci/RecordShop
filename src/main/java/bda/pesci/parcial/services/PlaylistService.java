package bda.pesci.parcial.services;


import bda.pesci.parcial.entities.Playlist;
import bda.pesci.parcial.entities.dtos.PlaylistDTO;
import bda.pesci.parcial.entities.dtos.PlaylistDTO2;

import java.util.List;

public interface PlaylistService {

    List<Playlist> findAll();

    Playlist getById(Integer id);

    Playlist delete(Integer id);

    Playlist add(PlaylistDTO entity);

    Playlist modify(Integer id, PlaylistDTO entity);

    Playlist addTrackToPlaylist(Integer playlistId, Integer trackId);

    Playlist removeTrackFromPlaylist(Integer playlistId, Integer trackId);

    PlaylistDTO2 createPlaylist(PlaylistDTO entity, Integer genreId, Integer artistId, Integer minutes);
}
