package dongeun.city.service;

import dongeun.city.dto.CityDto;
import dongeun.city.entity.City;
import dongeun.city.exception.CityException;
import dongeun.city.repository.CityRepository;
import dongeun.common.exception.ErrorCode;
import dongeun.trip.entity.Trip;
import dongeun.trip.repository.TripRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CityService {

    private CityRepository cityRepository;

    private TripRepository tripRepository;

    public CityService(CityRepository cityRepository, TripRepository tripRepository) {
        this.cityRepository = cityRepository;
        this.tripRepository = tripRepository;
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

    @Transactional
    public void deleteCity(String cityName) {
        City city = cityRepository.findByName(cityName);
        if(city == null)
            throw new CityException(ErrorCode.NOT_FOUND_DATA, "도시 정보를 찾을 수 없습니다.");
        List<Trip> trips = tripRepository.findAllByCityId(city.getId());
        if(!trips.isEmpty() && trips.size() > 0)
            throw new CityException(ErrorCode.DATABASE_ERROR, "해당 도시로 지정된 여행 건이 있습니다.");
        cityRepository.delete(city);
    }
}
