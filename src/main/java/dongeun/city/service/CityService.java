package dongeun.city.service;

import dongeun.city.dto.CityDto;
import dongeun.city.entity.City;
import dongeun.city.exception.CityException;
import dongeun.city.repository.CityRepository;
import dongeun.common.exception.ErrorCode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CityService {

    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional
    public City saveCity(CityDto cityDto) {
        City city = cityDto.mapCityDtoToCityEntity();
        return cityRepository.save(city);
    }

    @Transactional
    public City updateCity(CityDto cityDto) {
        City city = cityRepository.findByName(cityDto.getName());
        if(city == null)
            throw new CityException(ErrorCode.NOT_FOUND_DATA, "도시 정보를 찾을 수 없습니다.");
        city.setName(cityDto.getName());
        city.setDescription(cityDto.getDescription());
        city.setCountry(cityDto.getCountry());
        return cityRepository.save(city);
    }
}
