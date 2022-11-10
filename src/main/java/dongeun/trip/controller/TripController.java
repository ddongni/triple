package dongeun.trip.controller;

import dongeun.trip.dto.TripDto;
import dongeun.trip.entity.Trip;
import dongeun.trip.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripController {

    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/trips")
    public ResponseEntity<TripDto> saveTrip(@RequestBody TripDto tripDto) {
        Trip trip = tripService.saveTrip(tripDto);
        TripDto savedTripDto = trip.mapTripEntityToTripDto();
        return ResponseEntity.ok()
                .body(savedTripDto);
    }
}
