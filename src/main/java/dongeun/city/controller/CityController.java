package dongeun.city.controller;

import dongeun.city.dto.CityDto;
import dongeun.city.entity.City;
import dongeun.city.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CityController {

    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities/{cityName}")
    public CityDto getCity(@PathVariable String cityName) {
        City city = cityService.getCity(cityName);
        return city.mapCityEntityToCityDto();
    }

    @GetMapping("/cities")
    public List<CityDto> getCities(@RequestParam String userName) {
        List<City> cities = cityService.getCities(userName);

        List<CityDto> citiesDto = new ArrayList<>();
        cities.stream().forEach((city) -> {
            citiesDto.add(city.mapCityEntityToCityDto());
        });
        return citiesDto;
    }

    @PostMapping("/cities")
    public ResponseEntity<CityDto> saveCity(@RequestBody @Valid CityDto cityDto) {
        City city = cityService.saveCity(cityDto);
        CityDto responseCityDto = city.mapCityEntityToCityDto();
        return ResponseEntity.ok()
                .body(responseCityDto);
    }

    @PutMapping("/cities")
    public ResponseEntity<CityDto> updateCity(@RequestBody @Valid CityDto cityDto) {
        City city = cityService.updateCity(cityDto);
        CityDto responseCityDto = city.mapCityEntityToCityDto();
        return ResponseEntity.ok()
                .body(responseCityDto);
    }

    @DeleteMapping("/cities")
    public ResponseEntity deleteCity(@RequestParam String cityName) {
        cityService.deleteCity(cityName);
        return new ResponseEntity(HttpStatus.OK);
    }
}
