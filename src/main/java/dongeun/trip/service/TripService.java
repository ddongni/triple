package dongeun.trip.service;

import dongeun.city.entity.City;
import dongeun.city.exception.CityException;
import dongeun.city.repository.CityRepository;
import dongeun.common.exception.ErrorCode;
import dongeun.trip.dto.TripDto;
import dongeun.trip.entity.Trip;
import dongeun.trip.repository.TripRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TripService {

    private CityRepository cityRepository;
    private TripRepository tripRepository;

    public TripService(CityRepository cityRepository, TripRepository tripRepository) {
        this.cityRepository = cityRepository;
        this.tripRepository = tripRepository;
    }

    @Transactional
    public Trip saveTrip(TripDto tripDto) {
        City city = cityRepository.findByName(tripDto.getCityName());
        if(city == null)
            throw new CityException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다.");
        Trip trip = tripDto.mapTripDtoToTripEntity(city);
        return tripRepository.save(trip);
    }

}
