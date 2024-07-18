package bda.pesci.parcial.services;

import bda.pesci.parcial.entities.Artist;
import bda.pesci.parcial.entities.dtos.ArtistDTO;
import bda.pesci.parcial.repositories.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService{

    private  ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }
    @Override
    public List<Artist> findAll() {
        return artistRepository.findAll();}

    @Override
    public Artist getById(Integer id) {
        return artistRepository.findById(id).orElseThrow();
    }

    @Override
    public Artist delete(Integer id) {
        Artist artist = artistRepository.findById(id).orElseThrow();
        artistRepository.delete(artist);
        return artist;

    }

    public Artist add(ArtistDTO entity) {
        Artist artist = new Artist();
        artist.setName(entity.getName());
        return artistRepository.save(artist);
    }

    //http://localhost:8080/swagger-ui/index.html
    public Artist modify(Integer id, ArtistDTO entity) {
        Optional<Artist> artist = Optional.of(artistRepository.findById(id).orElseThrow());
        if (artist.isPresent()){
            artist.get().setName(entity.getName());
            return artistRepository.save(artist.get());}
        else {
            throw new NoSuchElementException("No existente en la Base de Datos");
        }
    }

}
