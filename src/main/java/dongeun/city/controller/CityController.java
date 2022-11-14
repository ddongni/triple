package dongeun.city.controller;

import dongeun.city.dto.CityDto;
import dongeun.city.dto.CityResponseDto;
import dongeun.city.entity.City;
import dongeun.city.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CityController {

    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Operation(
            summary = "단일 도시 조회",
            method = "GET"
    )
    @GetMapping("/cities/{cityId}")
    public CityResponseDto getCity(@PathVariable Long cityId) {
        City city = cityService.getCity(cityId);
        return CityResponseDto.builder()
                .message("단일 도시 조회 성공")
                .city(city.mapCityEntityToCityDto())
                .build();
    }

    @Operation(
            summary = "사용자별 도시 목록 조회",
            method = "GET"
    )
    @GetMapping("/cities")
    public List<CityResponseDto> getCities(@RequestParam String userName) {
        LocalDateTime now = LocalDateTime.now();

        List<CityResponseDto> cityResponseDtoList = new ArrayList<>();
        cityResponseDtoList.add(getCitiesOnTrip(now, userName));
        cityResponseDtoList.addAll(getOtherCities(now, userName));

        return cityResponseDtoList;
    }

    public CityResponseDto getCitiesOnTrip(LocalDateTime now, String userName) {
        List<CityDto> cityDtoList = new ArrayList<>();

        List<City> citiesOnTrip = cityService.getCitiesOnTrip(now, userName);
        citiesOnTrip.stream().forEach((city) -> {
            cityDtoList.add(city.mapCityEntityToCityDto());
        });
        return CityResponseDto.builder()
                .message("여행중인 도시 조회")
                .cities(cityDtoList)
                .build();
    }

    public List<CityResponseDto> getOtherCities(LocalDateTime now, String userName) {
        List<CityResponseDto> cityResponseDtoList = new ArrayList<>();

        int listSize = 0;
        List<City> citiesOnPlanning = cityService.getCitiesOnPlanning(now, userName);
        getOtherCitiesResponseDto(cityResponseDtoList, citiesOnPlanning, listSize, "여행이 예정된 도시 조회");
        if(listSize == 10)
            return cityResponseDtoList;

        List<City> registeredCitiesWithinOneDay = cityService.getRegisteredCitiesWithinOneDay(now);
        getOtherCitiesResponseDto(cityResponseDtoList, registeredCitiesWithinOneDay, listSize, "하루 이내에 등록된 도시 조회");
        if(listSize == 10)
            return cityResponseDtoList;

        List<City> viewedCitiesWithinLastWeek = cityService.getViewedCitiesWithinLastWeek(now);
        getOtherCitiesResponseDto(cityResponseDtoList, viewedCitiesWithinLastWeek, listSize, "최근 일주일 이내에 한 번 이상 조회된 도시 조회");
        if(listSize == 10)
            return cityResponseDtoList;

        List<City> otherRandomCities = cityService.getOtherRandomCities(now);
        getOtherCitiesResponseDto(cityResponseDtoList, otherRandomCities, listSize, "위의 조건에 해당하지 않는 모든 도시 조회");
        if(listSize == 10)
            return cityResponseDtoList;

        return cityResponseDtoList;
    }

    public void getOtherCitiesResponseDto(List<CityResponseDto> cityResponseDtoList, List<City> otherCities, int listSize, String message) {
        List<CityDto> cityDtoList = new ArrayList<>();
        for(City otherCity : otherCities) {
            listSize++;
            cityDtoList.add(otherCity.mapCityEntityToCityDto());
            if(listSize == 10)
                return;
        }
        cityResponseDtoList.add(CityResponseDto.builder()
                .message(message)
                .cities(cityDtoList)
                .build());
    }

    @Operation(
            summary = "도시 등록",
            method = "POST"
    )
    @PostMapping("/cities")
    public CityResponseDto saveCity(@RequestParam String name,
                                    @RequestParam String country,
                                    @RequestParam String description) {
        CityDto cityDto = CityDto.builder()
                .name(name)
                .country(country)
                .description(description)
                .build();

        City city = cityService.saveCity(cityDto);
        CityDto responseCityDto = city.mapCityEntityToCityDto();
        return CityResponseDto.builder()
                .message("도시 등록 성공")
                .city(responseCityDto)
                .build();
    }

    @Operation(
            summary = "도시 수정",
            method = "PUT"
    )
    @PutMapping("/cities")
    public CityResponseDto updateCity(@RequestParam Long cityId,
                                      @RequestParam String name,
                                      @RequestParam String country,
                                      @RequestParam String description) {
        CityDto cityDto = CityDto.builder()
                .id(cityId)
                .name(name)
                .country(country)
                .description(description)
                .build();

        City city = cityService.updateCity(cityDto);
        CityDto responseCityDto = city.mapCityEntityToCityDto();
        return CityResponseDto.builder()
                .message("도시 수정 성공")
                .city(responseCityDto)
                .build();
    }

    @Operation(
            summary = "도시 삭제",
            method = "DELETE"
    )
    @DeleteMapping("/cities")
    public CityResponseDto deleteCity(@RequestParam Long cityId) {
        cityService.deleteCity(cityId);
        return CityResponseDto.builder()
                .message("도시 삭제 성공")
                .build();
    }
}
