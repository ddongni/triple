package dongeun.city.entity;

import dongeun.city.dto.CityDto;
import dongeun.common.entity.TimeStamp;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "city")
public class City extends TimeStamp {

    @Id
    @GeneratedValue
    @Column(name = "city_id")
    private Long id;

    private String name;

    private String description;

    private String country;

    private long views;

    public CityDto mapCityEntityToCityDto() {
        return CityDto.builder()
                .name(name)
                .description(description)
                .country(country)
                .views(views)
                .build();
    }
}
