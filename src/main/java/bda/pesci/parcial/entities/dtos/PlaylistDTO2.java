package bda.pesci.parcial.entities.dtos;

import bda.pesci.parcial.entities.Track;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO2 {
    private String name;

    private Float time;

    private List<Track> tracks;
}
