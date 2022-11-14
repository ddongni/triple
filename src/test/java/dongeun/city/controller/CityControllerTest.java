package dongeun.city.controller;

import dongeun.city.entity.City;
import dongeun.city.repository.CityRepository;
import dongeun.trip.entity.Trip;
import dongeun.trip.repository.TripRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    TripRepository tripRepository;

    @Test
    @DisplayName("도시 등록 테스트")
    public void saveCity() throws Exception {
        // given
        String name = "서울";
        String country = "대한민국";
        String description = "대한민국의 수도 서울 입니다.";

        // when

        // then
        mockMvc.perform(post("/cities")
                        .param("name", name)
                        .param("country", country)
                        .param("description", description))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.city.name", equalTo(name)))
                .andExpect(jsonPath("$.city.country", equalTo(country)))
                .andExpect(jsonPath("$.city.description", equalTo(description)))
                .andDo(print());
    }

    @Test
    @DisplayName("도시 수정 테스트")
    public void updateCity() throws Exception {
        // given
        String name = "서울";
        String country = "대한민국";

        City city = City.builder()
                .name(name)
                .country(country)
                .description("대한민국의 수도 서울 입니다.")
                .build();

        City savedCity = cityRepository.save(city);
        String description = "서울은 대한민국의 수도 입니다.";

        // when

        // then
        mockMvc.perform(put("/cities")
                        .param("cityId", savedCity.getId().toString())
                        .param("name", name)
                        .param("country", country)
                        .param("description", description))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.city.id", is(savedCity.getId()), Long.class))
                .andExpect(jsonPath("$.city.name", equalTo(name)))
                .andExpect(jsonPath("$.city.country", equalTo(country)))
                .andExpect(jsonPath("$.city.description", equalTo(description)))
                .andDo(print());
    }

    @Test
    @DisplayName("도시 삭제 테스트")
    public void deleteCity() throws Exception {
        // given
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();

        City savedCity = cityRepository.save(city);

        // when

        // then
        mockMvc.perform(delete("/cities")
                        .param("cityId", savedCity.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("단일 도시 조회 테스트")
    public void getCity() throws Exception {
        // given
        String name = "서울";
        String country = "대한민국";
        String description = "대한민국의 수도 서울 입니다.";

        City city = City.builder()
                .name(name)
                .country(country)
                .description(description)
                .build();

        City savedCity = cityRepository.save(city);

        // when

        // then
        mockMvc.perform(get("/cities/" + savedCity.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.city.id", is(savedCity.getId()), Long.class))
                .andExpect(jsonPath("$.city.name", equalTo(name)))
                .andExpect(jsonPath("$.city.country", equalTo(country)))
                .andExpect(jsonPath("$.city.description", equalTo(description)))
                .andDo(print());
    }

    @Test
    @DisplayName("사용자별 도시 목록 조회 테스트")
    public void getCities() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        String userName = "신동은";

        setCitiesOnTrip(userName);
        setCitiesOnPlanning(userName);
        setRegisteredCitiesWithinOneDay();
        setViewedCitiesWithinLastWeek();

        // then
        mockMvc.perform(get("/cities")
                        .param("userName", userName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    // 여행중인 도시 생성
    public void setCitiesOnTrip(String userName) {
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
                .endDate(LocalDate.parse("2023-12-31"))
                .build();
        tripRepository.save(trip);
    }

    // 여행이 예정된 도시 데이터
    public void setCitiesOnPlanning(String userName) {
        City city = City.builder()
                .name("워싱턴")
                .country("미국")
                .description("미국의 수도 워싱턴 입니다.")
                .build();
        cityRepository.save(city);

        Trip trip = Trip.builder()
                .userName(userName)
                .city(city)
                .startDate(LocalDate.parse("2023-12-31"))
                .endDate(LocalDate.parse("2024-12-31"))
                .build();
        tripRepository.save(trip);
    }

    // 하루 이내에 등록된 도시 데이터
    public void setRegisteredCitiesWithinOneDay() {
        City city = City.builder()
                .name("도쿄")
                .country("일본")
                .description("일본의 수도 도쿄 입니다.")
                .build();
        cityRepository.save(city);
    }

    // 최근 일주일 이내에 한 번 이상 조회된 도시 데이터
    public void setViewedCitiesWithinLastWeek() {
        City city = City.builder()
                .name("런던")
                .country("영국")
                .description("영국의 수도 런던 입니다.")
                .build();
        City savedCity = cityRepository.save(city);
        savedCity.setLastViewedAt(LocalDateTime.now());
        cityRepository.save(savedCity);
    }
}
