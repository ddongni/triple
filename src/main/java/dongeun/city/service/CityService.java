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
import java.util.ArrayList;
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
        City city = cityRepository.findByName(cityDto.getName())
                .orElseThrow(() -> new CityException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다. city name : " + cityDto.getName()));
        city.setName(cityDto.getName());
        city.setDescription(cityDto.getDescription());
        city.setCountry(cityDto.getCountry());
        return cityRepository.save(city);
    }

    @Transactional
    public void deleteCity(String cityName) {
        City city = cityRepository.findByName(cityName)
                .orElseThrow(() -> new CityException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다. city name : " + cityName));
        List<Trip> trips = tripRepository.findAllByCityId(city.getId());
        if(!trips.isEmpty() && trips.size() > 0)
            throw new CityException(ErrorCode.DATABASE_ERROR, "해당 도시로 지정된 여행 건이 있습니다.");
        cityRepository.delete(city);
    }

    @Transactional
    public City getCity(String cityName) {
        City city = cityRepository.findByName(cityName)
                .orElseThrow(() -> new CityException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다. city name : " + cityName));
        city.setLastViewedAt(LocalDateTime.now());
        cityRepository.save(city);
        return city;
    }

    public List<City> getCities(String userName) {
        LocalDateTime now = LocalDateTime.now();
        List<City> cities = new ArrayList<>();
        cities.addAll(getCitiesOnTrip(now, userName));
        cities.addAll(getOtherCities(now, userName));
        return cities;
    }

    public List<City> getCitiesOnTrip(LocalDateTime now, String userName) {
        List<City> citiesOnTrip = cityRepository.getCitiesOnTrip(now, userName);
        checkCityListNull(citiesOnTrip);
        return citiesOnTrip;
    }

    public List<City> getOtherCities(LocalDateTime now, String userName) {
        List<City> otherCities = new ArrayList<>();

        int listSize = 0;
        List<City> citiesOnPlanning = cityRepository.getCitiesOnPlanning(now, userName);
        checkCityListNull(citiesOnPlanning);
        for(City city : citiesOnPlanning) {
            otherCities.add(city);
            listSize++;
            if(listSize == 10)
                return otherCities;
        }

        List<City> registeredCitiesWithinOneDay = cityRepository.getRegisteredCitiesWithinOneDay(now);
        checkCityListNull(registeredCitiesWithinOneDay);
        for(City city : registeredCitiesWithinOneDay) {
            otherCities.add(city);
            listSize++;
            if(listSize == 10)
                return otherCities;
        }

        List<City> viewedCitiesAtLeastOnce = cityRepository.getViewedCitiesWithinLastWeek(now);
        checkCityListNull(viewedCitiesAtLeastOnce);
        for(City city : citiesOnPlanning) {
            otherCities.add(city);
            listSize++;
            if(listSize == 10)
                return otherCities;
        }


        List<City> otherRandomCities = cityRepository.getOtherRandomCities(now);
        checkCityListNull(otherRandomCities);
        for(City city : citiesOnPlanning) {
            otherCities.add(city);
            listSize++;
            if(listSize == 10)
                return otherCities;
        }

        return otherCities;
    }

    public void checkCityListNull(List<City> cities) {
        if(cities == null)
            throw new CityException(ErrorCode.NOT_FOUND_DATA, "도시 정보를 찾을 수 없습니다.");
    }
}
