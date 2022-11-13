package dongeun.city.dto;

import dongeun.city.entity.City;
import lombok.*;

@Data
@Builder
public class CityDto {

    private Long id;

    private String name;

    private String country;

    private String description;

    public City mapCityDtoToCityEntity() {
        return City.builder()
                .name(name)
                .country(country)
                .description(description)
                .build();
    }
}
