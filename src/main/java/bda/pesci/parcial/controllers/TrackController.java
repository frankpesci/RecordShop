package bda.pesci.parcial.controllers;

import bda.pesci.parcial.entities.Track;
import bda.pesci.parcial.entities.dtos.TrackDTO;
import bda.pesci.parcial.services.TrackService;
import bda.pesci.parcial.services.TrackServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Track>> findAll() {
        List<Track> tracks = trackService.findAll();
        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getById(@PathVariable("id") Integer id) {
        try {
            Track track = trackService.getById(id);
            return ResponseEntity.ok(track);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Track> delete(@PathVariable("id") Integer id) {
        try {
            Track track = trackService.delete(id);
            return ResponseEntity.ok(track);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Track> modify(@PathVariable("id") Integer id, @RequestBody TrackDTO entity) {
        try {
            Track track = trackService.modify(id, entity);
            return ResponseEntity.ok(track);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Track> add(@RequestBody TrackDTO entity) {
        try {
            Track track = trackService.add(entity);
            return ResponseEntity.ok(track);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
    //    Obtener todos los Tracks (Pistas o Canciones) dados un Artista y Un Id de género específicos (URL
//    sugerida: GET:/api/tracks?artistid=<id de artista>&genreid=<id de género>). Para ello deberá
//    buscar todos los tracks de todos los álbumes del artista indicado para componer una colección. El
//    resultado debe informar: TrackId, tracks.Name, álbum.Title, artist.Name, miliseconds de cada
//    uno de los tracks retornados (se sugiere crear un dto con esta estructura).
//    Se espera que la respuesta tenga código 200 si responde correctamente, 204 si el id de artista
//    existe, pero no hay ningún track que coincida con el filtro de artista/álbum e id de género y 404
//en el caso que no exista el id de artista.

    @GetMapping("/{artistid}/{genreid}")
    public ResponseEntity<List<Track>> getByArtistIdAndGenreId(@PathVariable("artistid") Integer artistId, @PathVariable("genreid") Integer genreId) {
        try {
            List<Track> tracks = trackService.getByArtistIdAndGenreId(artistId, genreId);
            if(tracks.isEmpty())
                //si el artista no tiene ningun track de ese genero: 204
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            //si esta  el artista y tiene tracks de ese genero:  200
            return ResponseEntity.ok(tracks);
        } catch (Exception ex) {
            //si no lo encuentra al artista entonces devuelvo 404
            return ResponseEntity.notFound().build();
        }
    }
}



