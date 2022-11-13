package dongeun.trip.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dongeun.city.entity.City;
import dongeun.trip.entity.Trip;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripDto {

    private Long id;

    private String description;

    private Long cityId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String transportation;

    private String userName;

    public Trip mapTripDtoToTripEntity(City city) {
        return Trip.builder()
                .description(description)
                .city(city)
                .startDate(startDate)
                .endDate(endDate)
                .transportation(transportation)
                .userName(userName)
                .build();
    }
}
