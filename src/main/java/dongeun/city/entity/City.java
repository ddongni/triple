package dongeun.city.entity;

import dongeun.city.dto.CityDto;
import dongeun.common.util.TimeStamp;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Data
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

    public CityDto mapCityEntityToCityDto() {
        return CityDto.builder()
                .name(name)
                .description(description)
                .country(country)
                .build();
    }
}
