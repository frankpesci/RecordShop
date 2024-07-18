package bda.pesci.parcial.entities.dtos;

import bda.pesci.parcial.entities.Album;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackDTO {
    private String name;


    private Integer album;


    private Integer mediaTypeId;


    private Integer genreId;


    private String composer;


    private Integer milliseconds;


    private Integer bytes;


    private Double unitPrice;
}
