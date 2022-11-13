package dongeun.city.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dongeun.city.entity.City;
import lombok.*;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
