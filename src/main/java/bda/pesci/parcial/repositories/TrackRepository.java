package bda.pesci.parcial.repositories;

import bda.pesci.parcial.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track,Integer> {

    List<Track> getTracksByAlbum_Artist_IdAndGenreId(Integer albumId, Integer genreId);
}
