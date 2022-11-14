package dongeun.trip.controller;

import dongeun.city.entity.City;
import dongeun.city.repository.CityRepository;
import dongeun.trip.entity.Trip;
import dongeun.trip.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TripControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    CityRepository cityRepository;

    private City savedCity;

    @BeforeEach
    public void saveCity() {
        City city = City.builder()
                .name("서울")
                .country("대한민국")
                .description("대한민국의 수도 서울 입니다.")
                .build();
        savedCity = cityRepository.save(city);
    }
    @Test
    @DisplayName("여행 등록 테스트")
    public void saveTrip() throws Exception {
        // given
        String userName = "신동은";
        Long cityId = savedCity.getId();
        LocalDate startDate = LocalDate.parse("2022-11-01");
        LocalDate endDate = LocalDate.parse("2022-11-30");
        String description = "한달 여행";
        String transportation = "기차";

        // when

        // then
        mockMvc.perform(post("/trips")
                        .param("userName", userName)
                        .param("cityId", cityId.toString())
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("description", description)
                        .param("transportation", transportation))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trip.userName", equalTo(userName)))
                .andExpect(jsonPath("$.trip.cityId", is(cityId), Long.class))
                .andExpect(jsonPath("$.trip.startDate", equalTo(startDate.toString())))
                .andExpect(jsonPath("$.trip.endDate", equalTo(endDate.toString())))
                .andExpect(jsonPath("$.trip.description", equalTo(description)))
                .andExpect(jsonPath("$.trip.transportation", equalTo(transportation)))
                .andDo(print());
    }

    @Test
    @DisplayName("여행 수정 테스트")
    public void updateTrip() throws Exception {
        // given
        String userName = "신동은";
        LocalDate startDate = LocalDate.parse("2022-11-01");
        LocalDate endDate = LocalDate.parse("2022-11-30");
        String transportation = "기차";
        Trip trip = Trip.builder()
                .userName(userName)
                .city(savedCity)
                .startDate(startDate)
                .endDate(endDate)
                .description("한달 여행")
                .transportation(transportation)
                .build();
        Trip savedTrip = tripRepository.save(trip);
        String description = "한달 기차 여행";

        // when

        // then
        mockMvc.perform(put("/trips")
                        .param("tripId", savedTrip.getId().toString())
                        .param("userName", userName)
                        .param("cityId", savedTrip.getCity().getId().toString())
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("description", description)
                        .param("transportation", transportation))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trip.id", is(savedTrip.getId()), Long.class))
                .andExpect(jsonPath("$.trip.userName", equalTo(userName)))
                .andExpect(jsonPath("$.trip.cityId", is(savedTrip.getCity().getId()), Long.class))
                .andExpect(jsonPath("$.trip.startDate", equalTo(startDate.toString())))
                .andExpect(jsonPath("$.trip.endDate", equalTo(endDate.toString())))
                .andExpect(jsonPath("$.trip.description", equalTo(description)))
                .andExpect(jsonPath("$.trip.transportation", equalTo(transportation)))
                .andDo(print());
    }

    @Test
    @DisplayName("여행 삭제 테스트")
    public void deleteTrip() throws Exception {
        // given
        String userName = "신동은";
        LocalDate startDate = LocalDate.parse("2022-11-01");
        LocalDate endDate = LocalDate.parse("2022-11-30");
        String transportation = "기차";
        Trip trip = Trip.builder()
                .userName(userName)
                .city(savedCity)
                .startDate(startDate)
                .endDate(endDate)
                .description("한달 여행")
                .transportation(transportation)
                .build();
        Trip savedTrip = tripRepository.save(trip);
        String description = "한달 기차 여행";

        // when

        // then
        mockMvc.perform(delete("/trips")
                        .param("tripId", savedTrip.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("단일 여행 조회 테스트")
    public void getTrip() throws Exception {
        // given
        String userName = "신동은";
        LocalDate startDate = LocalDate.parse("2022-11-01");
        LocalDate endDate = LocalDate.parse("2022-11-30");
        String transportation = "기차";
        String description = "한달 기차 여행";
        Trip trip = Trip.builder()
                .userName(userName)
                .city(savedCity)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .transportation(transportation)
                .build();
        Trip savedTrip = tripRepository.save(trip);

        // when

        // then
        mockMvc.perform(get("/trips/" + savedTrip.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trip.id", is(savedTrip.getId()), Long.class))
                .andExpect(jsonPath("$.trip.userName", equalTo(userName)))
                .andExpect(jsonPath("$.trip.cityId", is(savedTrip.getCity().getId()), Long.class))
                .andExpect(jsonPath("$.trip.startDate", equalTo(startDate.toString())))
                .andExpect(jsonPath("$.trip.endDate", equalTo(endDate.toString())))
                .andExpect(jsonPath("$.trip.description", equalTo(description)))
                .andExpect(jsonPath("$.trip.transportation", equalTo(transportation)))
                .andDo(print());
    }

    @Test
    @DisplayName("모든 여행 조회 테스트")
    public void getTrips() throws Exception {
        // given
        String userName = "신동은";
        LocalDate startDate = LocalDate.parse("2022-11-01");
        LocalDate endDate = LocalDate.parse("2022-11-30");
        String transportation = "기차";
        String description = "한달 기차 여행";
        Trip trip = Trip.builder()
                .userName(userName)
                .city(savedCity)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .transportation(transportation)
                .build();
        Trip savedTrip = tripRepository.save(trip);

        // when

        // then
        mockMvc.perform(get("/trips"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
