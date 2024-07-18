package bda.pesci.parcial.services;

import bda.pesci.parcial.entities.Artist;
import bda.pesci.parcial.entities.Playlist;
import bda.pesci.parcial.entities.Track;
import bda.pesci.parcial.entities.dtos.PlaylistDTO;
import bda.pesci.parcial.entities.dtos.PlaylistDTO2;
import bda.pesci.parcial.repositories.PlaylistRepository;
import bda.pesci.parcial.repositories.TrackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private PlaylistRepository playlistRepository;
    private TrackRepository trackRepository;

    private ArtistService artistService;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, TrackRepository trackRepository, ArtistService artistService) {
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.artistService = artistService;
    }

    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll();}

    @Override
    public Playlist getById(Integer id) {
        return playlistRepository.findById(id).orElseThrow();
    }

    @Override
    public Playlist delete(Integer id) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow();
        playlistRepository.delete(playlist);
        return playlist;

    }

    public Playlist add(PlaylistDTO entity) {
        Playlist playlist = new Playlist();
        playlist.setName(entity.getName());
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist modify(Integer id, PlaylistDTO entity) {
        Optional<Playlist> playlist = Optional.of(playlistRepository.findById(id).orElseThrow());
        if(playlist.isPresent()){
            playlist.get().setName(entity.getName());
            return playlistRepository.save(playlist.get());
        }
        else {
            throw new NoSuchElementException("No existente en la Base de Datos");
        }
    }
    @Transactional
    public Playlist addTrackToPlaylist(Integer playlistId, Integer trackId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new NoSuchElementException("Playlist no encontrada"));
        Track track = trackRepository.findById(trackId).orElseThrow(() -> new NoSuchElementException("Track no encontrado"));

        if (!playlist.getTracks().contains(track)) {
            playlist.getTracks().add(track);
            return playlistRepository.save(playlist);
        } else {
            // El track ya está en la playlist, no es necesario agregarlo nuevamente.
            return playlist;
        }
    }

    @Transactional
    public Playlist removeTrackFromPlaylist(Integer playlistId, Integer trackId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new NoSuchElementException("Playlist no encontrada"));
        Track track = trackRepository.findById(trackId).orElseThrow(() -> new NoSuchElementException("Track no encontrado"));

        if (playlist.getTracks().contains(track)) {
            playlist.getTracks().remove(track);
            return playlistRepository.save(playlist);
        } else {
            // El track no está en la playlist, no es necesario eliminarlo.
            return playlist;
        }
    }



    @Override
    public PlaylistDTO2 createPlaylist(PlaylistDTO entity, Integer genreId, Integer artistId, Integer minutes) {
        //  Crear un nuevo playlist en base a: nombre de la playlist, id de artista, id de género y una cantidad
        //  de tiempo en minutos que agregue tracks del artista y genero a la nueva lista en orden aleatorio
        //hasta alcanzar el tiempo solicitado o se acaben los tracks disponibles
        Optional<Artist> artist = Optional.of(artistService.getById(artistId));
        if(artist.isPresent()) {
            Playlist playlist = add(entity);
            int time = 0;
            List<Track> tracks = trackRepository.getTracksByAlbum_Artist_IdAndGenreId(artistId, genreId);
            while (time < minutes && !tracks.isEmpty()) {
                int random = (int) (Math.random() * tracks.size());
                Track track = tracks.get(random);
                time += track.getMilliseconds() / 60000;
                addTrackToPlaylist(playlist.getPlaylistId(), track.getTrackId());
                tracks.remove(random);
            }
            playlist.getTracks().sort((t1, t2) -> t1.getUnitPrice().compareTo(t2.getUnitPrice()));
            PlaylistDTO2 playlistToShow = new PlaylistDTO2();
            playlistToShow.setName(playlist.getName());
            playlistToShow.setTime((float) time);
            playlistToShow.setTracks(playlist.getTracks());
            return playlistToShow;
        }
        //si no existe el id del artista:
        else {
            throw new NoSuchElementException();
        }
    }
}
