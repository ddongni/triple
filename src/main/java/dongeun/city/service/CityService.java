package dongeun.city.service;

import dongeun.city.dto.CityDto;
import dongeun.city.entity.City;
import dongeun.city.exception.CityException;
import dongeun.city.repository.CityRepository;
import dongeun.common.exception.ErrorCode;
import dongeun.trip.entity.Trip;
import dongeun.trip.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        City city = cityRepository.findById(cityDto.getId())
                .orElseThrow(() -> new CityException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다. city id : " + cityDto.getId()));
        city.setName(cityDto.getName());
        city.setDescription(cityDto.getDescription());
        city.setCountry(cityDto.getCountry());
        return cityRepository.save(city);
    }

    @Transactional
    public void deleteCity(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다. city id : " + cityId));
        List<Trip> trips = tripRepository.findAllByCityId(cityId);
        if(!trips.isEmpty() && trips.size() > 0)
            throw new CityException(ErrorCode.DATABASE_ERROR, "해당 도시로 지정된 여행 건이 있습니다.");
        cityRepository.delete(city);
    }

    @Transactional
    public City getCity(Long cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다. city id : " + cityId));
        city.setLastViewedAt(LocalDateTime.now());
        cityRepository.save(city);
        return city;
    }

    @Transactional(readOnly = true)
    public List<City> getCitiesOnTrip(LocalDateTime now, String userName) {
        List<City> citiesOnTrip = cityRepository.getCitiesOnTrip(now, userName);
        checkCityListNull(citiesOnTrip);
        return citiesOnTrip;
    }

    @Transactional(readOnly = true)
    public List<City> getCitiesOnPlanning(LocalDateTime now, String userName) {
        List<City> citiesOnPlanning = cityRepository.getCitiesOnPlanning(now, userName);
        checkCityListNull(citiesOnPlanning);
        return citiesOnPlanning;
    }

    @Transactional(readOnly = true)
    public List<City> getRegisteredCitiesWithinOneDay(LocalDateTime now) {
        List<City> registeredCitiesWithinOneDay = cityRepository.getRegisteredCitiesWithinOneDay(now);
        checkCityListNull(registeredCitiesWithinOneDay);
        return registeredCitiesWithinOneDay;
    }

    @Transactional(readOnly = true)

    public List<City> getViewedCitiesWithinLastWeek(LocalDateTime now) {
        List<City> viewedCitiesAtLeastOnce = cityRepository.getViewedCitiesWithinLastWeek(now);
        checkCityListNull(viewedCitiesAtLeastOnce);
        return viewedCitiesAtLeastOnce;
    }

    @Transactional(readOnly = true)
    public List<City> getOtherRandomCities(LocalDateTime now) {
        List<City> otherRandomCities = cityRepository.getOtherRandomCities(now);
        checkCityListNull(otherRandomCities);
        return otherRandomCities;
    }

    public void checkCityListNull(List<City> cities) {
        if(cities == null)
            throw new CityException(ErrorCode.NOT_FOUND_DATA, "도시 정보를 찾을 수 없습니다.");
    }
}
