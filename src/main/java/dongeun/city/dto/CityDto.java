package dongeun.city.dto;

import dongeun.city.entity.City;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CityDto {

    @NotNull
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
