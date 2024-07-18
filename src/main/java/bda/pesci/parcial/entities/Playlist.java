package bda.pesci.parcial.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "playlists")
@Data


public class Playlist {
    @Id
    @Column(name = "PlaylistId")
    @GeneratedValue(generator = "PLAYLISTS")
    @TableGenerator(name = "PLAYLISTS", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue="playlists",
            initialValue=1, allocationSize=1)
    private Integer playlistId;

    @Column(name = "Name")
    private String name;

    @ManyToMany
    @JoinTable(name= "Playlist_Track",joinColumns = @JoinColumn(name = "PlaylistId"),
            inverseJoinColumns = @JoinColumn(name = "TrackId"))
    @JsonIgnore
    private List<Track> tracks =new ArrayList<>();

}
