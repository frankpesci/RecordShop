package bda.pesci.parcial.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="albums")
@Data

public class Album {
    @Id
    @Column(name="AlbumId")
    @GeneratedValue(generator = "ALBUMS")
    @TableGenerator(name = "ALBUMS", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue="albums",
            initialValue=1, allocationSize=1)
    private Integer albumId;

    @Column(name="Title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "ArtistId")
    @JsonBackReference
    private Artist artist;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Track> tracks;
}
