package dongeun.trip.dto;

import dongeun.city.entity.City;
import dongeun.trip.entity.Trip;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TripDto {

    private String description;

    private String cityName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String transportation;

    private String managerName;

    public Trip mapTripDtoToTripEntity(City city) {
        return Trip.builder()
                .description(description)
                .city(city)
                .startDate(startDate)
                .endDate(endDate)
                .transportation(transportation)
                .managerName(managerName)
                .build();
    }
}
