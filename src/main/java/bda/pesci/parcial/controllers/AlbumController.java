package bda.pesci.parcial.controllers;

import bda.pesci.parcial.entities.Album;
import bda.pesci.parcial.entities.Artist;
import bda.pesci.parcial.entities.dtos.AlbumDTO;
import bda.pesci.parcial.services.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Album>> findAll(){
        try {
            List<Album> albums = albumService.findAll();
            return ResponseEntity.ok(albums);
        }
        catch (NoSuchElementException ex) {
            // 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //  204
        //
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getById(@PathVariable("id") Integer id){
        try {
            Album album = albumService.getById(id);
            return ResponseEntity.ok(album);
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Album> modify(@PathVariable("id") Integer id, @RequestBody AlbumDTO entity){
        try {
            Album album = albumService.modify(id, entity);
            return ResponseEntity.ok(album);
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Album> add(AlbumDTO entity) {
        try{
        Album album = albumService.add(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(album);
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Album> delete(@PathVariable("id")Integer id){
        try{
            Album album = albumService.delete(id);
            return ResponseEntity.ok(album);
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
