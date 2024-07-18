package bda.pesci.parcial.controllers;


import bda.pesci.parcial.entities.Playlist;
import bda.pesci.parcial.entities.dtos.PlaylistDTO;
import bda.pesci.parcial.entities.dtos.PlaylistDTO2;
import bda.pesci.parcial.services.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Playlist>> findAll() {
        List<Playlist> playlists = playlistService.findAll();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getById(@PathVariable("id") Integer id) {
        try {
            Playlist playlist = playlistService.getById(id);
            return ResponseEntity.ok(playlist);
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Playlist> delete(@PathVariable("id") Integer id) {
        try {
            Playlist playlist = playlistService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(playlist);
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping
    public ResponseEntity<Playlist> add(@RequestBody PlaylistDTO entity){
        try {
            Playlist playlist = playlistService.add(entity);

            return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Playlist> modify(@PathVariable("id") Integer id,@RequestBody PlaylistDTO entity) {
        try {
            Playlist playlist = playlistService.modify(id, entity);

            return ResponseEntity.status(HttpStatus.OK).body(playlist);
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/{playlistId}/tracks/{trackId}")
    public ResponseEntity<Playlist> addTrackToPlaylist(@PathVariable("playlistId") Integer playlistId, @PathVariable("trackId") Integer trackId) {
        try {
            Playlist playlist = playlistService.addTrackToPlaylist(playlistId, trackId);
            return ResponseEntity.status(HttpStatus.OK).body(playlist);
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{playlistId}/tracks/{trackId}")
    public ResponseEntity<Playlist> removeTrackFromPlaylist(@PathVariable("playlistId") Integer playlistId, @PathVariable("trackId") Integer trackId) {
        try {
            Playlist playlist = playlistService.removeTrackFromPlaylist(playlistId, trackId);
            return ResponseEntity.status(HttpStatus.OK).body(playlist);
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
//    Crear un nuevo playlist en base a: nombre de la playlist, id de artista, id de género y una cantidad
//    de tiempo en minutos que agregue tracks del artista y genero a la nueva lista en orden aleatorio
//    hasta alcanzar el tiempo solicitado o se acaben los tracks disponibles, (URL sugerida: POST o PUT
///api/playlist, con un que body contenga los datos mencionados).
//    El resultado debe responder con la estructura de la playlist, es decir, el nombre, la duración total,
//    y la lista de tracks incluidos ordenados de mayor a menor según UnitPrice. Además debe
//    retornar el código 200 o 201 si la operación fue correcta, 204 en caso que no existan tracks para
//    el id de artista especificado, que cumplan con el filtro de id de género y 404 en caso que no
//    exista el id de artista, en los casos de error no se debe crear la playlist

    @PostMapping("/playlist")
    public ResponseEntity<PlaylistDTO2> createPlaylist(@RequestBody PlaylistDTO entity,@RequestParam Integer genreId,
                                                   @RequestParam Integer artistId, @RequestParam Integer minutes) {
        try {
            PlaylistDTO2 playlist = playlistService.createPlaylist(entity, genreId, artistId, minutes);
            if (playlist.getTracks().isEmpty()) {
                    //si el artista no tiene ningun track de ese genero: 204
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(playlist);
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
