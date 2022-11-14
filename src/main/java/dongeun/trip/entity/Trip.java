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

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String transportation;

    @Column(name = "user_name")
    private String userName;

    public TripDto mapTripEntityToTripDto() {
        return TripDto.builder()
                .id(id)
                .description(description)
                .cityId(city.getId())
                .startDate(startDate)
                .endDate(endDate)
                .transportation(transportation)
                .userName(userName)
                .build();
    }
}
