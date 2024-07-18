package bda.pesci.parcial.repositories;

import bda.pesci.parcial.entities.Album;
import bda.pesci.parcial.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {

}
