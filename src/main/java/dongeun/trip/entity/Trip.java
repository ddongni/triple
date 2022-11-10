package dongeun.trip.entity;

import dongeun.city.entity.City;
import dongeun.common.entity.TimeStamp;
import dongeun.trip.dto.TripDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trip")
public class Trip extends TimeStamp {

    @Id
    @GeneratedValue
    @Column(name = "trip_id")
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    private LocalDate startDate;

    private LocalDate endDate;

    private String transportation;

    private String managerName;

    public TripDto mapTripEntityToTripDto() {
        return TripDto.builder()
                .description(description)
                .cityName(city.getName())
                .startDate(startDate)
                .endDate(endDate)
                .transportation(transportation)
                .managerName(managerName)
                .build();
    }
}
