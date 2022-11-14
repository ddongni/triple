package dongeun.trip.service;

import dongeun.city.entity.City;
import dongeun.city.repository.CityRepository;
import dongeun.trip.dto.TripDto;
import dongeun.trip.entity.Trip;
import dongeun.trip.repository.TripRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TripServiceTest {

    @Autowired
    TripService tripService;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    CityRepository cityRepository;

    @Test
    @DisplayName("여행 등록 테스트")
    public void saveTrip() {
        // given
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();
        City savedCity = cityRepository.save(city);

        TripDto trip = TripDto.builder()
                .userName("신동은")
                .cityId(savedCity.getId())
                .startDate(LocalDate.parse("2022-11-01"))
                .endDate(LocalDate.parse("2022-12-31"))
                .build();

        // when
        Trip savedTrip = tripService.saveTrip(trip);

        // then
        assertThat(savedTrip.getId()).isNotNull();
        assertThat(savedTrip.getUserName()).isEqualTo(trip.getUserName());
        assertThat(savedTrip.getCity().getId()).isEqualTo(savedCity.getId());
        assertThat(savedTrip.getStartDate()).isEqualTo(trip.getStartDate());
        assertThat(savedTrip.getEndDate()).isEqualTo(trip.getEndDate());
    }

    @Test
    @DisplayName("여행 수정 테스트")
    public void updateTrip() {
        // given
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();
        cityRepository.save(city);

        Trip trip = Trip.builder()
                .userName("신동은")
                .city(city)
                .startDate(LocalDate.parse("2022-11-01"))
                .endDate(LocalDate.parse("2022-12-31"))
                .build();
        Trip savedTrip = tripRepository.save(trip);

        // when
        LocalDate startDate = LocalDate.parse("2022-11-11");
        savedTrip.setStartDate(startDate);
        Trip updatedTrip = tripService.saveTrip(savedTrip.mapTripEntityToTripDto());

        // then
        assertThat(updatedTrip.getStartDate()).isEqualTo(startDate);
        assertThat(updatedTrip.getUserName()).isEqualTo(trip.getUserName());
        assertThat(updatedTrip.getCity().getId()).isEqualTo(trip.getCity().getId());
        assertThat(updatedTrip.getEndDate()).isEqualTo(trip.getEndDate());
    }

    @Test
    @DisplayName("여행 삭제 테스트")
    public void deleteTrip() {
        // given
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();
        cityRepository.save(city);

        Trip trip = Trip.builder()
                .userName("신동은")
                .city(city)
                .startDate(LocalDate.parse("2022-11-01"))
                .endDate(LocalDate.parse("2022-12-31"))
                .build();
        Trip savedTrip = tripRepository.save(trip);

        // when
        Long savedTripId = savedTrip.getId();
        tripService.deleteTrip(savedTripId);

        // then
        Optional<Trip> foundTrip = tripRepository.findById(savedTripId);
        assertThat(foundTrip).isEmpty();
    }

    @Test
    @DisplayName("단일 여행 조회 테스트")
    public void getTrip() {
        // given
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();
        cityRepository.save(city);

        Trip trip = Trip.builder()
                .userName("신동은")
                .city(city)
                .startDate(LocalDate.parse("2022-11-01"))
                .endDate(LocalDate.parse("2022-12-31"))
                .build();
        Trip savedTrip = tripRepository.save(trip);

        // when
        Long savedTripId = savedTrip.getId();
        Trip foundTrip = tripService.getTrip(savedTripId);

        // then
        assertThat(foundTrip).isNotNull();
        assertThat(foundTrip.getId()).isEqualTo(savedTripId);
        assertThat(foundTrip.getUserName()).isEqualTo(savedTrip.getUserName());
        assertThat(foundTrip.getCity().getId()).isEqualTo(savedTrip.getCity().getId());
        assertThat(foundTrip.getStartDate()).isEqualTo(savedTrip.getStartDate());
        assertThat(foundTrip.getEndDate()).isEqualTo(savedTrip.getEndDate());
    }

    @Test
    @DisplayName("모든 여행 조회 테스트")
    public void getTrips() {
        // given
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();
        cityRepository.save(city);

        Trip trip = Trip.builder()
                .userName("신동은")
                .city(city)
                .startDate(LocalDate.parse("2022-11-01"))
                .endDate(LocalDate.parse("2022-12-31"))
                .build();
        tripRepository.save(trip);

        // when
        List<Trip> trips = tripService.getTrips();

        // then
        assertThat(trips).isNotEmpty();
        assertThat(trips).isNotNull();
    }
}
