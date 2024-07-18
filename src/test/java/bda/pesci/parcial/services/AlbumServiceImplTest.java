package bda.pesci.parcial.services;
import bda.pesci.parcial.entities.Album;
import bda.pesci.parcial.entities.Artist;
import bda.pesci.parcial.entities.dtos.AlbumDTO;
import bda.pesci.parcial.repositories.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

public class AlbumServiceImplTest {

    @InjectMocks
    private AlbumServiceImpl albumService;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ArtistService artistService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetById() {
        Integer albumId = 1;
        Album album = new Album();
        album.setAlbumId(albumId);

        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));

        Album result = albumService.getById(albumId);

        assertNotNull(result);
        assertEquals(album, result);
    }

    @Test
    public void testGetByIdNotFound() {
        Integer albumId = 1;

        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            albumService.getById(albumId);
        });
    }

    @Test
    public void testDelete() {
        Integer albumId = 1;
        Album album = new Album();
        album.setAlbumId(albumId);

        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));

        Album deletedAlbum = albumService.delete(albumId);

        assertNotNull(deletedAlbum);
        assertEquals(album, deletedAlbum);
    }

    @Test
    public void testModify() {
        Integer albumId = 1;
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setTitle("Álbum Modificado");
        albumDTO.setArtist(1); // Supongamos que 1 es el ID de un artista existente

        Album album = new Album();
        album.setAlbumId(albumId);
        album.setTitle(albumDTO.getTitle());

        Artist artist = new Artist();
        artist.setId(albumDTO.getArtist());

        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));
        Mockito.when(artistService.getById(albumDTO.getArtist())).thenReturn(artist);
        Mockito.when(albumRepository.save(any(Album.class))).thenReturn(album);

        Album result = albumService.modify(albumId, albumDTO);

        assertNotNull(result);
        assertEquals(album, result);
        assertEquals(artist, result.getArtist());
    }

    @Test
    public void testModifyNotFound() {
        Integer albumId = 1;
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setTitle("Álbum Modificado");
        albumDTO.setArtist(1);

        Mockito.when(albumRepository.findById(albumId)).thenReturn(Optional.empty());


        assertThrows(NoSuchElementException.class, () -> {
            albumService.modify(albumId, albumDTO);
        });

    }

    @Test
    public void testAdd() {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setTitle("Nuevo Álbum");
        albumDTO.setArtist(1);

        Album album = new Album();
        album.setTitle(albumDTO.getTitle());

        Artist artist = new Artist();
        artist.setId(albumDTO.getArtist());
        album.setArtist(artist);
        Mockito.when(artistService.getById(albumDTO.getArtist())).thenReturn(artist);
        Mockito.when(albumRepository.save(any(Album.class))).thenReturn(album);

        Album result = albumService.add(albumDTO);

        assertNotNull(result);
        assertEquals(album, result);
        assertEquals(artist, result.getArtist());
    }
}
