package dongeun.trip.controller;

import dongeun.common.exception.ErrorCode;
import dongeun.trip.dto.TripDto;
import dongeun.trip.entity.Trip;
import dongeun.trip.exception.TripException;
import dongeun.trip.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TripController {

    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/trips/{tripId}")
    public TripDto getTrip(@PathVariable Long tripId) {
        Trip trip = tripService.getTrip(tripId);
        return trip.mapTripEntityToTripDto();
    }

    @GetMapping("/trips")
    public List<TripDto> getTrips() {
        List<Trip> trips = tripService.getTrips();

        List<TripDto> tripsDto = new ArrayList<>();
        trips.stream().forEach((trip) -> {
            tripsDto.add(trip.mapTripEntityToTripDto());
        });
        return tripsDto;
    }

    @PostMapping("/trips")
    public ResponseEntity<TripDto> saveTrip(@RequestBody @Valid TripDto tripDto) {
        Trip trip = tripService.saveTrip(tripDto);
        TripDto savedTripDto = trip.mapTripEntityToTripDto();
        return ResponseEntity.ok()
                .body(savedTripDto);
    }

    @PutMapping("/trips")
    public ResponseEntity<TripDto> updateTrip(@RequestBody @Valid TripDto tripDto) {
        if(tripDto.getId() == null)
            throw new TripException(ErrorCode.INVALID_METHOD_ARGUMENT, "여행 id 값이 null 입니다.");

        Trip trip = tripService.updateTrip(tripDto);
        TripDto updatedTripDto = trip.mapTripEntityToTripDto();
        return ResponseEntity.ok()
                .body(updatedTripDto);
    }

    @DeleteMapping("/trips")
    public ResponseEntity deleteTrip(@RequestParam(required = true) Long tripId) {
        tripService.deleteTrip(tripId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
