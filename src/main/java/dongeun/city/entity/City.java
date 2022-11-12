package dongeun.city.entity;

import dongeun.city.dto.CityDto;
import dongeun.common.entity.TimeStamp;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "last_viewed_at")
    private LocalDateTime lastViewedAt;

    public CityDto mapCityEntityToCityDto() {
        return CityDto.builder()
                .name(name)
                .description(description)
                .country(country)
                .build();
    }
}
