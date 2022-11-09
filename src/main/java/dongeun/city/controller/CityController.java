package dongeun.city.controller;

import dongeun.city.dto.CityDto;
import dongeun.city.entity.City;
import dongeun.city.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/cities")
    public ResponseEntity<CityDto> saveCity(CityDto cityDto) {
        City city = cityService.saveCity(cityDto);
        CityDto responseCityDto = city.mapCityEntityToCityDto();
        return ResponseEntity.ok()
                .body(responseCityDto);
    }
}
