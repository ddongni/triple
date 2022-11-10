package dongeun.trip.controller;

import dongeun.common.exception.ErrorCode;
import dongeun.trip.dto.TripDto;
import dongeun.trip.entity.Trip;
import dongeun.trip.exception.TripException;
import dongeun.trip.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TripController {

    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
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
}
