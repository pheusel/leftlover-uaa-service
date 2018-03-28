package dhbw.leftlovers.service.uaa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tbl_standort")
@NoArgsConstructor
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long standortid;

    @Column(name = "name")
    private String long_name;

    @Column(name = "latitude")
    private Double lat;

    @Column(name = "longitude")
    private Double lng;

    public Location(String long_name, Double lng, Double lat) {
        this.long_name = long_name;
        this.lng = lng;
        this.lat = lat;
    }
}
