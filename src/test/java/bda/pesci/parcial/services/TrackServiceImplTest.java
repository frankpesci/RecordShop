package bda.pesci.parcial.services;

import bda.pesci.parcial.entities.Artist;
import bda.pesci.parcial.entities.Track;
import bda.pesci.parcial.entities.Album;
import bda.pesci.parcial.entities.dtos.TrackDTO;
import bda.pesci.parcial.repositories.TrackRepository;
import bda.pesci.parcial.services.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class TrackServiceImplTest {

    @InjectMocks
    private TrackServiceImpl trackService;

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private AlbumService albumService;

    @Mock ArtistService artistService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetById() {
        Integer trackId = 1;
        Track track = new Track();
        track.setTrackId(trackId);

        Mockito.when(trackRepository.findById(trackId)).thenReturn(Optional.of(track));

        Track result = trackService.getById(trackId);

        assertNotNull(result);
        assertEquals(track, result);
    }

    @Test
    public void testModify() {
        Integer trackId = 1;
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setName("Track Modificado");
        trackDTO.setAlbum(1);
        // Supongamos que las demás propiedades del DTO también se establecen correctamente

        Track track = new Track();
        track.setTrackId(trackId);
        track.setName("Track Original");

        Album album = new Album();
        album.setAlbumId(1);

        Mockito.when(trackRepository.findById(trackId)).thenReturn(Optional.of(track));
        Mockito.when(albumService.getById(trackDTO.getAlbum())).thenReturn(album);
        Mockito.when(trackRepository.save(any(Track.class))).thenReturn(track);

        Track result = trackService.modify(trackId, trackDTO);

        assertNotNull(result);
        assertEquals(track, result);
        assertEquals(album, result.getAlbum());
        assertEquals("Track Modificado", result.getName());
        // Verificar otras propiedades del track si es necesario
    }

    @Test
    public void testAdd() {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setName("Nuevo Track");
        trackDTO.setAlbum(1);
        // Supongamos que las demás propiedades del DTO también se establecen correctamente

        Album album = new Album();
        album.setAlbumId(1);

        Mockito.when(albumService.getById(trackDTO.getAlbum())).thenReturn(album);
        Mockito.when(trackRepository.save(any(Track.class))).thenAnswer(invocation -> {
            Track savedTrack = invocation.getArgument(0);
            savedTrack.setAlbum(album);
            return savedTrack;
        });

        Track result = trackService.add(trackDTO);

        assertNotNull(result);
        assertEquals("Nuevo Track", result.getName());
        assertEquals(album, result.getAlbum());
        // Verificar otras propiedades del track si es necesario
    }
    @Test
    public void testGetByIdNotFound() {
        Integer trackId = 1;

        Mockito.when(trackRepository.findById(trackId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            trackService.getById(trackId);
        });
    }

    @Test
    public void testModifyNotFound() {
        Integer trackId = 1;
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setName("Track Modificado");
        trackDTO.setAlbum(1);

        Mockito.when(trackRepository.findById(trackId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            trackService.modify(trackId, trackDTO);
        });
    }

    @Test
    public void testAddAlbumNotFound() {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setName("Nuevo Track");
        trackDTO.setAlbum(1);

        Mockito.when(albumService.getById(trackDTO.getAlbum())).thenThrow(new NoSuchElementException("No existente en la Base de Datos"));

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> trackService.add(trackDTO)
        );

        assertEquals("No existente en la Base de Datos", exception.getMessage());
    }

    @Test
    public void testAddSaveError() {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setName("Nuevo Track");
        trackDTO.setAlbum(1);

        Album album = new Album();
        album.setAlbumId(1);

        Mockito.when(albumService.getById(trackDTO.getAlbum())).thenReturn(album);
        Mockito.when(trackRepository.save(any(Track.class))).thenThrow(new RuntimeException("Error al guardar"));

        assertThrows(
                RuntimeException.class,
                () -> trackService.add(trackDTO)
        );
    }
    //tests parcial
    @Test
    public void testGetByArtistIdAndGenreId() {
        Integer artistId = 1;
        Integer genreId = 2;

        Artist artist = new Artist();
        artist.setId(artistId);

        Track track1 = new Track();
        track1.setTrackId(1);
        track1.setGenreId(genreId);

        Track track2 = new Track();
        track2.setTrackId(2);
        track2.setGenreId(genreId);

        List<Track> tracks = List.of(track1, track2);

        Mockito.when(artistService.getById(artistId)).thenReturn(artist);
        Mockito.when(trackRepository.getTracksByAlbum_Artist_IdAndGenreId(artistId, genreId)).thenReturn(tracks);

        List<Track> result = trackService.getByArtistIdAndGenreId(artistId, genreId);

        assertEquals(2, result.size());
        assertTrue(result.contains(track1));
        assertTrue(result.contains(track2));
    }

    @Test
    public void testGetByArtistIdAndGenreIdArtistNotFound() {
        Integer artistId = 1;
        Integer genreId = 2;

        Mockito.when(artistService.getById(artistId)).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> {
            trackService.getByArtistIdAndGenreId(artistId, genreId);
        });
    }

    @Test
    public void testGetByArtistIdAndGenreIdNoTracks() {
        Integer artistId = 1;
        Integer genreId = 2;

        Artist artist = new Artist();
        artist.setId(artistId);

        Mockito.when(artistService.getById(artistId)).thenReturn(artist);
        Mockito.when(trackRepository.getTracksByAlbum_Artist_IdAndGenreId(artistId, genreId)).thenReturn(Collections.emptyList());

        List<Track> result = trackService.getByArtistIdAndGenreId(artistId, genreId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}

