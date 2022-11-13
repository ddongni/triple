package dongeun.city.repository;

import dongeun.city.entity.City;
import dongeun.trip.entity.Trip;
import dongeun.trip.repository.TripRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    TripRepository tripRepository;

    @Test
    @DisplayName("도시 등록 테스트")
    public void saveCity() {
        // given
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();

        // when
        City savedCity = cityRepository.save(city);

        // then
        assertThat(savedCity.getId()).isNotNull();
        assertThat(savedCity.getName()).isEqualTo(city.getName());
        assertThat(savedCity.getCountry()).isEqualTo(city.getCountry());
        assertThat(savedCity.getDescription()).isEqualTo(city.getDescription());
    }

    @Test
    @DisplayName("도시 수정 테스트")
    public void updateCity() {
        // given
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();

        City savedCity = cityRepository.save(city);

        // when
        String description = "서울은 대한민국의 수도입니다.";
        savedCity.setDescription(description);
        City updatedCity = cityRepository.save(savedCity);

        // then
        assertThat(updatedCity.getDescription()).isEqualTo(description);
        assertThat(updatedCity.getName()).isEqualTo(city.getName());
        assertThat(updatedCity.getCountry()).isEqualTo(city.getCountry());
        assertThat(updatedCity.getDescription()).isEqualTo(city.getDescription());
    }

    @Test
    @DisplayName("도시 삭제 테스트")
    public void deleteCity() {
        // given
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();

        City savedCity = cityRepository.save(city);

        // when
        Long savedCityId = savedCity.getId();
        cityRepository.delete(savedCity);

        // then
        Optional<City> getCity = cityRepository.findById(savedCityId);
        assertThat(getCity).isEmpty();
    }

    @Test
    @DisplayName("단일 도시 조회 테스트")
    public void getCity() {
        // given
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();

        City savedCity = cityRepository.save(city);

        // when
        Long savedCityId = savedCity.getId();
        Optional<City> getCity = cityRepository.findById(savedCityId);

        // then
        assertThat(getCity).isNotEmpty();
        assertThat(getCity.get().getId()).isEqualTo(savedCityId);
        assertThat(getCity.get().getName()).isEqualTo(city.getName());
        assertThat(getCity.get().getCountry()).isEqualTo(city.getCountry());
        assertThat(getCity.get().getDescription()).isEqualTo(city.getDescription());
    }

    @Test
    @DisplayName("여행중인 도시 조회 테스트")
    public void getCitiesOnTrip() {
        // given
        LocalDateTime now = LocalDateTime.parse("2022-11-13T20:01:50.607576"); // 현재 시간 가정
        String userName = "신동은";

        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();
        cityRepository.save(city);

        Trip trip = Trip.builder()
                .userName(userName)
                .city(city)
                .startDate(LocalDate.parse("2022-11-01"))
                .endDate(LocalDate.parse("2022-11-15"))
                .build();
        tripRepository.save(trip);

        // when
        List<City> citiesOnTrip = cityRepository.getCitiesOnTrip(now, userName);

        // then
        assertThat(citiesOnTrip).isNotEmpty();
        assertThat(citiesOnTrip).isNotNull();
    }

    @Test
    @DisplayName("여행이 예정된 도시 조회 테스트")
    public void getCitiesOnPlanning() {
        // given
        LocalDateTime now = LocalDateTime.parse("2022-11-13T20:01:50.607576"); // 현재 시간 가정
        String userName = "신동은";

        City city = City.builder()
                .name("워싱턴")
                .country("미국")
                .description("미국의 수도 워싱턴 입니다.")
                .build();
        cityRepository.save(city);

        Trip trip = Trip.builder()
                .userName(userName)
                .city(city)
                .startDate(LocalDate.parse("2022-11-15"))
                .endDate(LocalDate.parse("2022-11-30"))
                .build();
        tripRepository.save(trip);

        // when
        List<City> citiesOnPlanning = cityRepository.getCitiesOnPlanning(now, userName);

        // then
        assertThat(citiesOnPlanning).isNotEmpty();
        assertThat(citiesOnPlanning).isNotNull();
    }

    @Test
    @DisplayName("하루 이내에 등록된 도시 조회 테스트")
    public void getRegisteredCitiesWithinOneDay() {
        // given
        LocalDateTime now = LocalDateTime.now();


        City city = City.builder()
                .name("도쿄")
                .country("일본")
                .description("일본의 수도 도쿄 입니다.")
                .build();
        cityRepository.save(city);

        // when
        List<City> registeredCitiesWithinOneDay = cityRepository.getRegisteredCitiesWithinOneDay(now);

        // then
        for(City registeredCity : registeredCitiesWithinOneDay) {
            assertThat(registeredCity.getCreatedAt()).isBetween(now.minusDays(1), now);
        }
    }

    @Test
    @DisplayName("최근 일주일 이내에 한 번이상 조회된 도시 조회 테스트")
    public void getViewedCitiesWithinLastWeek() {
        // given
        LocalDateTime now = LocalDateTime.now();


        City city = City.builder()
                .name("런던")
                .country("영국")
                .description("영국의 수도 런던 입니다.")
                .build();
        City savedCity = cityRepository.save(city);
        savedCity.setLastViewedAt(LocalDateTime.now());
        cityRepository.save(savedCity);

        // when
        List<City> viewedCitiesWithinLastWeek = cityRepository.getViewedCitiesWithinLastWeek(now);

        // then
        for(City viewedCity : viewedCitiesWithinLastWeek) {
            assertThat(viewedCity.getLastViewedAt()).isBetween(now.minusWeeks(1), now);
        }
    }

    @Test
    @DisplayName("위의 조건에 해당하지 않는 모든 도시 조회 테스트")
    public void getOtherRandomCities() {
        // given
        LocalDateTime now = LocalDateTime.now();
        City city = City.builder()
                .name("베이징")
                .country("중국")
                .description("중국의 수도 베이징 입니다.")
                .build();
        City savedCity = cityRepository.save(city);

        // when
        List<City> otherRandomCities = cityRepository.getOtherRandomCities(now);

        // then
        assertThat(otherRandomCities).isEmpty();
    }
}
