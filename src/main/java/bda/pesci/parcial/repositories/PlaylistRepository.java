package bda.pesci.parcial.repositories;

import bda.pesci.parcial.entities.Playlist;
import bda.pesci.parcial.entities.Track;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,Integer> {

}
