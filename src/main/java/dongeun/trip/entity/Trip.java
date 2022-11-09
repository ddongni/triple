package dongeun.trip.entity;

import dongeun.city.entity.City;
import dongeun.common.util.TimeStamp;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
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

}
