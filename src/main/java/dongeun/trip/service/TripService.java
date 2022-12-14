package dongeun.trip.service;

import dongeun.city.entity.City;
import dongeun.city.repository.CityRepository;
import dongeun.common.exception.ErrorCode;
import dongeun.trip.dto.TripDto;
import dongeun.trip.entity.Trip;
import dongeun.trip.exception.TripException;
import dongeun.trip.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
        City city = cityRepository.findById(tripDto.getCityId())
                .orElseThrow(() -> new TripException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다. trip cityId : " + tripDto.getCityId()));
        Trip trip = tripDto.mapTripDtoToTripEntity(city);
        return tripRepository.save(trip);
    }

    @Transactional
    public Trip updateTrip(TripDto tripDto) {
        Trip trip = tripRepository.findById(tripDto.getId())
                .orElseThrow(() -> new TripException(ErrorCode.NOT_FOUND_DATA, "해당 여행 정보를 찾을 수 없습니다. trip id : " + tripDto.getId()));

        // 여행 정보에서 도시 정보 수정 시
        if(!tripDto.getCityId().equals(trip.getCity().getId())) {
            City city = cityRepository.findById(tripDto.getCityId())
                    .orElseThrow(() -> new TripException(ErrorCode.NOT_FOUND_DATA, "해당 도시 정보를 찾을 수 없습니다. trip cityId : " + tripDto.getCityId()));
            trip.setCity(city);
        }
        trip.setDescription(tripDto.getDescription());
        trip.setStartDate(tripDto.getStartDate());
        trip.setEndDate(tripDto.getEndDate());
        trip.setTransportation(tripDto.getTransportation());
        trip.setUserName(tripDto.getUserName());
        return tripRepository.save(trip);
    }

    @Transactional
    public void deleteTrip(Long tripId) {
        tripRepository.deleteById(tripId);
    }

    @Transactional(readOnly = true)
    public Trip getTrip(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new TripException(ErrorCode.NOT_FOUND_DATA, "해당 여행 정보를 찾을 수 없습니다. trip id : " + tripId));
    }

    @Transactional(readOnly = true)
    public List<Trip> getTrips() {
        List<Trip> trips = tripRepository.findAll();
        if(trips == null || trips.size() == 0)
            throw new TripException(ErrorCode.NOT_FOUND_DATA, "여행 정보를 찾을 수 없습니다.");
        return trips;
    }
}
