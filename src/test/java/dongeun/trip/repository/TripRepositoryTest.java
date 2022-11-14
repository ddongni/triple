package dongeun.trip.repository;

import dongeun.city.entity.City;
import dongeun.city.repository.CityRepository;
import dongeun.trip.entity.Trip;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TripRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    TripRepository tripRepository;

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

        Trip trip = Trip.builder()
                .userName("신동은")
                .city(city)
                .startDate(LocalDate.parse("2022-11-01"))
                .endDate(LocalDate.parse("2022-12-31"))
                .build();

        // when
        Trip savedTrip = tripRepository.save(trip);

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
        City savedCity = cityRepository.save(city);

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
        Trip updatedTrip = tripRepository.save(savedTrip);

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
        City savedCity = cityRepository.save(city);

        Trip trip = Trip.builder()
                .userName("신동은")
                .city(city)
                .startDate(LocalDate.parse("2022-11-01"))
                .endDate(LocalDate.parse("2022-12-31"))
                .build();
        Trip savedTrip = tripRepository.save(trip);

        // when
        Long savedTripId = savedTrip.getId();
        tripRepository.delete(savedTrip);

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
        City savedCity = cityRepository.save(city);

        Trip trip = Trip.builder()
                .userName("신동은")
                .city(city)
                .startDate(LocalDate.parse("2022-11-01"))
                .endDate(LocalDate.parse("2022-12-31"))
                .build();
        Trip savedTrip = tripRepository.save(trip);
        
        // when
        Long savedTripId = savedTrip.getId();
        Optional<Trip> foundTrip = tripRepository.findById(savedTripId);

        // then
        assertThat(foundTrip).isNotEmpty();
        assertThat(foundTrip.get().getId()).isEqualTo(savedTripId);
        assertThat(foundTrip.get().getUserName()).isEqualTo(savedTrip.getUserName());
        assertThat(foundTrip.get().getCity().getId()).isEqualTo(savedTrip.getCity().getId());
        assertThat(foundTrip.get().getStartDate()).isEqualTo(savedTrip.getStartDate());
        assertThat(foundTrip.get().getEndDate()).isEqualTo(savedTrip.getEndDate());
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
        List<Trip> trips = tripRepository.findAll();

        // then
        assertThat(trips).isNotEmpty();
        assertThat(trips).isNotNull();
    }
}
