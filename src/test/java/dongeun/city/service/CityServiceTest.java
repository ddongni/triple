package dongeun.city.service;

import dongeun.city.dto.CityDto;
import dongeun.city.entity.City;
import dongeun.city.repository.CityRepository;
import dongeun.trip.entity.Trip;
import dongeun.trip.repository.TripRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CityServiceTest {

    @Autowired
    CityService cityService;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    TripRepository tripRepository;

    @Test
    @DisplayName("도시 등록 테스트")
    public void saveCity() {
        // given
        CityDto city = CityDto.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();

        // when
        City savedCity = cityService.saveCity(city);

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
        City updatedCity = cityService.updateCity(savedCity.mapCityEntityToCityDto());

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
        cityService.deleteCity(savedCityId);

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
        City getCity = cityService.getCity(savedCityId);

        // then
        assertThat(getCity).isNotNull();
        assertThat(getCity.getId()).isEqualTo(savedCityId);
        assertThat(getCity.getName()).isEqualTo(city.getName());
        assertThat(getCity.getCountry()).isEqualTo(city.getCountry());
        assertThat(getCity.getDescription()).isEqualTo(city.getDescription());
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
        List<City> citiesOnTrip = cityService.getCitiesOnTrip(now, userName);

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
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
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
        List<City> citiesOnPlanning = cityService.getCitiesOnPlanning(now, userName);

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
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();
        cityRepository.save(city);

        // when
        List<City> registeredCitiesWithinOneDay = cityService.getRegisteredCitiesWithinOneDay(now);

        // then
        registeredCitiesWithinOneDay.stream().forEach((registeredCity) -> {
            assertThat(registeredCity.getCreatedAt()).isBetween(now.minusDays(1), now);
        });
    }

    @Test
    @DisplayName("최근 일주일 이내에 한 번이상 조회된 도시 조회 테스트")
    public void getViewedCitiesWithinLastWeek() {
        // given
        LocalDateTime now = LocalDateTime.now();


        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();
        City savedCity = cityRepository.save(city);

        cityRepository.findById(savedCity.getId());

        // when
        List<City> viewedCitiesWithinLastWeek = cityService.getViewedCitiesWithinLastWeek(now);

        // then
        viewedCitiesWithinLastWeek.stream().forEach((viewedCity) -> {
            assertThat(viewedCity.getLastViewedAt()).isBetween(now.minusWeeks(1), now);
        });
    }

    @Test
    @DisplayName("위의 조건에 해당하지 않는 모든 도시 조회 테스트")
    public void getOtherRandomCities() {
        // given
        LocalDateTime now = LocalDateTime.now();
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();
        City savedCity = cityRepository.save(city);

        // when
        List<City> otherRandomCities = cityService.getOtherRandomCities(now);

        // then
        assertThat(otherRandomCities).isEmpty();
    }
}
