package bda.pesci.parcial.controllers;

import bda.pesci.parcial.entities.Artist;
import bda.pesci.parcial.entities.dtos.ArtistDTO;
import bda.pesci.parcial.services.ArtistService;
import bda.pesci.parcial.services.ArtistServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private  ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Artist>> findAll() {
        List<Artist> artists = artistService.findAll();
        return ResponseEntity.ok(artists);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Artist> getById(@PathVariable("id") Integer id) {
        try {
            Artist artist = artistService.getById(id);
            return ResponseEntity.ok(artist);
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Artist> delete(@PathVariable("id") Integer id) {
        try {
            Artist artist = artistService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(artist);
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping
    public ResponseEntity<Artist> add(@RequestBody ArtistDTO entity){
        try {
            Artist artist = artistService.add(entity);

            return ResponseEntity.status(HttpStatus.CREATED).body(artist);
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Artist> modify(@PathVariable("id") Integer id,@RequestBody ArtistDTO entity) {
        try {
            Artist artist = artistService.modify(id, entity);

            return ResponseEntity.status(HttpStatus.OK).body(artist);
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
