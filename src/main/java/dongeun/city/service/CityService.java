package dongeun.city.service;

import dongeun.city.dto.CityDto;
import dongeun.city.entity.City;
import dongeun.city.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City saveCity(CityDto cityDto) {
        City city = cityDto.mapCityDtoToCityEntity();
        return cityRepository.save(city);
    }
}
