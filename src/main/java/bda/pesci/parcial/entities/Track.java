package bda.pesci.parcial.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="tracks")
@Data

public class Track {
    @Id
    @Column(name="TrackId")
    @GeneratedValue(generator = "TRACKS")
    @TableGenerator(name = "TRACKS", table = "sqlite_sequence",
            pkColumnName = "name", valueColumnName = "seq",
            pkColumnValue="tracks",
            initialValue=1, allocationSize=1)
    private Integer trackId;

    @Column(name="Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "AlbumId")
    @JsonBackReference
    private Album album;

    @Column(name="MediatypeId")
    private Integer mediaTypeId;

    @Column(name="GenreId")
    private Integer genreId;

    @Column(name="Composer")
    private String composer;

    @Column(name="Milliseconds")
    private Integer milliseconds;

    @Column(name="Bytes")
    private Integer bytes;

    @Column(name="Unitprice")
    private Double unitPrice;

    @ManyToMany(mappedBy = "tracks", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Playlist> playlists;


}
