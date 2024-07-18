package bda.pesci.parcial.services;

import bda.pesci.parcial.entities.Artist;
import bda.pesci.parcial.entities.Playlist;
import bda.pesci.parcial.entities.Track;
import bda.pesci.parcial.entities.dtos.PlaylistDTO;
import bda.pesci.parcial.entities.dtos.PlaylistDTO2;
import bda.pesci.parcial.repositories.PlaylistRepository;
import bda.pesci.parcial.repositories.TrackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.List;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PlaylistServiceImplTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private ArtistService artistService;

    @Mock
    private TrackService trackService;

    private PlaylistServiceImpl playlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        playlistService = new PlaylistServiceImpl(playlistRepository,trackRepository,artistService);
    }

    @Test
    void testAddPlaylist_Success() {
        // Arrange
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName("Test Playlist");

        Playlist playlist = new Playlist();
        playlist.setName(playlistDTO.getName());

        when(playlistRepository.save(playlist)).thenReturn(playlist);

        // Act
        Playlist result = playlistService.add(playlistDTO);

        // Assert
        assertEquals(playlist, result);
    }

    @Test
    void testModifyPlaylist_Success() {
        // Arrange
        int playlistId = 1;
        PlaylistDTO updatedPlaylistDTO = new PlaylistDTO();
        updatedPlaylistDTO.setName("Updated Playlist");

        Playlist existingPlaylist = new Playlist();
        existingPlaylist.setPlaylistId(playlistId);

        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(existingPlaylist));
        when(playlistRepository.save(existingPlaylist)).thenReturn(existingPlaylist);

        // Act
        Playlist result = playlistService.modify(playlistId, updatedPlaylistDTO);

        // Assert
        assertEquals(updatedPlaylistDTO.getName(), result.getName());
    }

    @Test
    void testModifyPlaylist_PlaylistNotFound() {
        // Arrange
        int playlistId = 1;
        PlaylistDTO updatedPlaylistDTO = new PlaylistDTO();
        updatedPlaylistDTO.setName("Updated Playlist");

        when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> playlistService.modify(playlistId, updatedPlaylistDTO));
    }

    @Test
    void testDeletePlaylist_Success() {
        // Arrange
        int playlistId = 1;
        Playlist existingPlaylist = new Playlist();
        existingPlaylist.setPlaylistId(playlistId);

        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(existingPlaylist));

        // Act
        Playlist result = playlistService.delete(playlistId);

        // Assert
        assertEquals(playlistId, result.getPlaylistId());
    }

    @Test
    void testDeletePlaylist_PlaylistNotFound() {
        // Arrange
        int playlistId = 1;

        when(playlistRepository.findById(playlistId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> playlistService.delete(playlistId));
    }

    //tests parcial
    @Test
    public void testCreatePlaylist() {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName("My Playlist");
        Integer artistId = 1;
        Integer genreId = 2;
        Integer minutes = 30;

        Artist artist = new Artist();
        artist.setId(artistId);

        Track track1 = new Track();
        track1.setTrackId(1);
        track1.setMilliseconds(60000);

        Track track2 = new Track();
        track2.setTrackId(2);
        track2.setMilliseconds(60000);

        List<Track> tracks = new ArrayList<>();
        tracks.add(track1);
        tracks.add(track2);

        Playlist playlist = new Playlist();
        playlist.setPlaylistId(1);

        Mockito.when(artistService.getById(artistId)).thenReturn(artist);
        Mockito.when(trackRepository.getTracksByAlbum_Artist_IdAndGenreId(artistId, genreId)).thenReturn(tracks);
        Mockito.when(playlistRepository.save(ArgumentMatchers.any(Playlist.class))).thenReturn(playlist);
        Mockito.when(playlistService.addTrackToPlaylist(1, track1.getTrackId())).thenReturn(playlist);
        //no puedo hacer el que me tome el tipo de Playlist
        PlaylistDTO2 result = playlistService.createPlaylist(playlistDTO, genreId, artistId, minutes);

        assertNotNull(result);
        assertEquals("My Playlist", result.getName());
        assertEquals(1, result.getTracks().size());
        assertEquals(1, result.getTime());
    }

    @Test
    public void testCreatePlaylistArtistNotFound() {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        Integer artistId = 1;
        Integer genreId = 2;
        Integer minutes = 30;

        Mockito.when(artistService.getById(artistId)).thenThrow(new NoSuchElementException());

       assertThrows(NoSuchElementException.class, () -> {
           playlistService.createPlaylist(playlistDTO, genreId, artistId, minutes);
       });
    }

    @Test
    public void testCreatePlaylistNoTracks() {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName("My Playlist");
        Integer artistId = 1;
        Integer genreId = 2;
        Integer minutes = 30;

        Artist artist = new Artist();
        artist.setId(artistId);

        List<Track> tracks = new ArrayList<>(); // Lista vacía

        Playlist playlist = new Playlist();
        playlist.setPlaylistId(1);

        Mockito.when(artistService.getById(artistId)).thenReturn(artist);
        Mockito.when(trackRepository.getTracksByAlbum_Artist_IdAndGenreId(artistId, genreId)).thenReturn(tracks);
        Mockito.when(playlistRepository.save(ArgumentMatchers.any(Playlist.class))).thenReturn(playlist);
        //no puedo hacer el que me tome el tipo de Playlist

        PlaylistDTO2 result = playlistService.createPlaylist(playlistDTO, genreId, artistId, minutes);

        assertNotNull(result);
        assertEquals("My Playlist", result.getName());
        assertTrue(result.getTracks().isEmpty());
        assertEquals(0, result.getTime());
    }
}