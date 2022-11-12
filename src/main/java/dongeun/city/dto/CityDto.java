package dongeun.city.dto;

import dongeun.city.entity.City;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CityDto {

    @NotNull
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
