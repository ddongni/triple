package dongeun.city.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue
    @Column(name = "city_id")
    private Long id;

    private String description;

    private String name;

    private String country;
}
