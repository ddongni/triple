package dongeun.trip.service;

import dongeun.city.entity.City;
import dongeun.city.exception.CityException;
import dongeun.city.repository.CityRepository;
import dongeun.common.exception.ErrorCode;
import dongeun.trip.dto.TripDto;
import dongeun.trip.entity.Trip;
import dongeun.trip.exception.TripException;
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
        City city = cityRepository.findByName(tripDto.getCityName())
                .orElseThrow(() -> new TripException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다. trip cityName : " + tripDto.getCityName()));
        Trip trip = tripDto.mapTripDtoToTripEntity(city);
        return tripRepository.save(trip);
    }

    @Transactional
    public Trip updateTrip(TripDto tripDto) {
        Trip trip = tripRepository.findById(tripDto.getId())
                .orElseThrow(() -> new TripException(ErrorCode.NOT_FOUND_DATA, "해당 여행 정보를 찾을 수 없습니다. trip id : " + tripDto.getId()));

        // 여행 정보에서 도시 정보 수정 시
        if(!tripDto.getCityName().equals(trip.getCity())) {
            City city = cityRepository.findByName(tripDto.getCityName())
                    .orElseThrow(() -> new TripException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다. trip cityName : " + tripDto.getCityName()));
            trip.setCity(city);
        }
        trip.setDescription(tripDto.getDescription());
        trip.setStartDate(tripDto.getStartDate());
        trip.setEndDate(tripDto.getEndDate());
        trip.setTransportation(tripDto.getTransportation());
        trip.setManagerName(tripDto.getManagerName());
        return tripRepository.save(trip);
    }

    @Transactional
    public void deleteTrip(Long tripId) {
        tripRepository.deleteById(tripId);
    }

}
