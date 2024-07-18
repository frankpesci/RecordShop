package bda.pesci.parcial.services;

import static org.junit.jupiter.api.Assertions.*;
import bda.pesci.parcial.entities.Artist;
import bda.pesci.parcial.entities.dtos.ArtistDTO;
import bda.pesci.parcial.repositories.ArtistRepository;
import bda.pesci.parcial.services.ArtistServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.ArgumentMatchers.any;

public class ArtistServiceImplTest {

    @InjectMocks
    private ArtistServiceImpl artistService;

    @Mock
    private ArtistRepository artistRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Artist> artists = new ArrayList<>();
        // Agrega artistas ficticios a la lista
        // ...

        Mockito.when(artistRepository.findAll()).thenReturn(artists);

        List<Artist> result = artistService.findAll();

        assertNotNull(result);
        assertEquals(artists.size(), result.size());
    }

    @Test
    public void testGetById() {
        Integer artistId = 1;
        Artist artist = new Artist();
        artist.setId(artistId);

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Artist result = artistService.getById(artistId);

        assertNotNull(result);
        assertEquals(artist, result);
    }

    @Test
    public void testGetByIdNotFound() {
        Integer artistId = 1;

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            artistService.getById(artistId);
        });
    }

    @Test
    public void testDelete() {
        Integer artistId = 1;
        Artist artist = new Artist();
        artist.setId(artistId);

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Artist deletedArtist = artistService.delete(artistId);

        assertNotNull(deletedArtist);
        assertEquals(artist, deletedArtist);
    }

    @Test
    public void testAdd() {
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setName("Nuevo Artista");

        Artist artist = new Artist();
        artist.setName(artistDTO.getName());

        Mockito.when(artistRepository.save(any(Artist.class))).thenReturn(artist);

        Artist result = artistService.add(artistDTO);

        assertNotNull(result);
        assertEquals(artist, result);
    }

    @Test
    public void testModify() {
        Integer artistId = 1;
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setName("Artista Modificado");

        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setName(artistDTO.getName());

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        Mockito.when(artistRepository.save(any(Artist.class))).thenReturn(artist);

        Artist result = artistService.modify(artistId, artistDTO);

        assertNotNull(result);
        assertEquals(artist, result);
    }

    @Test
    public void testModifyNotFound() {
        Integer artistId = 1;
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setName("Artista Modificado");

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            artistService.modify(artistId, artistDTO);
        });
    }
}