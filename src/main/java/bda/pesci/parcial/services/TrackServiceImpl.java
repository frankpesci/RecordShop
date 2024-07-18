package bda.pesci.parcial.services;

import bda.pesci.parcial.entities.Album;
import bda.pesci.parcial.entities.Artist;
import bda.pesci.parcial.entities.Track;
import bda.pesci.parcial.entities.dtos.TrackDTO;
import bda.pesci.parcial.repositories.TrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final AlbumService albumService;

    private final ArtistService artistService;

    public TrackServiceImpl(TrackRepository trackRepository, AlbumService albumService, ArtistService artistService) {
        this.trackRepository = trackRepository;
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @Override
    public List<Track> findAll() {
        List<Track> tracks = trackRepository.findAll();
        return tracks;
    }

    @Override
    public Track getById(Integer id) {return trackRepository.findById(id).orElseThrow();}

    @Override
    public Track delete(Integer id) {
        Track track = trackRepository.findById(id).orElseThrow();
        trackRepository.delete(track);
        return track;
    }

    public Track modify(Integer id, TrackDTO entity){
        Optional<Track> track = Optional.of(trackRepository.findById(id).orElseThrow());
        Optional<Album> album = Optional.of(albumService.getById((int) entity.getAlbum()));
        if(track.isPresent() && album.isPresent()){
            track.get().setName(entity.getName());
            track.get().setAlbum(album.get());
            track.get().setMediaTypeId(entity.getMediaTypeId());
            track.get().setGenreId(entity.getGenreId());
            track.get().setComposer(entity.getComposer());
            track.get().setMilliseconds(entity.getMilliseconds());
            track.get().setBytes(entity.getBytes());
            track.get().setUnitPrice(entity.getUnitPrice());
            return trackRepository.save(track.get());
        }
        else {
            throw new NoSuchElementException("No existente en la Base de Datos");
        }
    }
    public Track add(TrackDTO entity){
        Track track = new Track();
        Optional<Album> album = Optional.of(albumService.getById((int) entity.getAlbum()));
        if(album.isPresent()){
            track.setName(entity.getName());
            track.setAlbum(album.get());
            track.setMediaTypeId(entity.getMediaTypeId());
            track.setGenreId(entity.getGenreId());
            track.setComposer(entity.getComposer());
            track.setMilliseconds(entity.getMilliseconds());
            track.setBytes(entity.getBytes());
            track.setUnitPrice(entity.getUnitPrice());
            return trackRepository.save(track);
        }
        else {
            throw new NoSuchElementException("No existente en la Base de Datos");
        }
    }
    public List<Track> getByArtistIdAndGenreId(Integer artistId, Integer genreId){
        Optional<Artist> artist = Optional.of(artistService.getById(artistId));
        if(artist.isPresent()){
            List<Track> tracks = trackRepository.getTracksByAlbum_Artist_IdAndGenreId(artistId, genreId);
            return tracks;
        }
        //si no existe el id del artista:
        else {
            throw new NoSuchElementException();
        }


    }

}
