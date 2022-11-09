package dongeun.city.dto;

import dongeun.city.entity.City;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDto {

    private String name;

    private String description;

    private String country;

    public City mapCityDtoToCityEntity() {
        return City.builder()
                .name(name)
                .description(description)
                .country(country)
                .build();
    }
}
