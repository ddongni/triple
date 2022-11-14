package dongeun.trip.controller;

import dongeun.trip.dto.TripDto;
import dongeun.trip.dto.TripResponseDto;
import dongeun.trip.entity.Trip;
import dongeun.trip.service.TripService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TripController {

    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/trips/{tripId}")
    public TripResponseDto getTrip(@PathVariable Long tripId) {
        Trip trip = tripService.getTrip(tripId);
        return TripResponseDto.builder()
                .message("단일 여행 조회 성공")
                .trip(trip.mapTripEntityToTripDto())
                .build();
    }

    @GetMapping("/trips")
    public TripResponseDto getTrips() {
        List<Trip> trips = tripService.getTrips();

        List<TripDto> tripsDto = new ArrayList<>();
        trips.stream().forEach((trip) -> {
            tripsDto.add(trip.mapTripEntityToTripDto());
        });
        return TripResponseDto.builder()
                .message("모든 여행 조회 성공")
                .trips(tripsDto)
                .build();
    }

    @PostMapping("/trips")
    public TripResponseDto saveTrip(@RequestParam String userName,
                                    @RequestParam Long cityId,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                    @RequestParam(required = false) String description,
                                    @RequestParam(required = false) String transportation) {
        TripDto tripDto = TripDto.builder()
                .userName(userName)
                .cityId(cityId)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .transportation(transportation)
                .build();

        Trip trip = tripService.saveTrip(tripDto);
        TripDto savedTripDto = trip.mapTripEntityToTripDto();

        return TripResponseDto.builder()
                .message("여행 등록 성공")
                .trip(savedTripDto)
                .build();
    }

    @PutMapping("/trips")
    public TripResponseDto updateTrip(@RequestParam Long tripId,
                                              @RequestParam String userName,
                                              @RequestParam Long cityId,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                              @RequestParam(required = false) String description,
                                              @RequestParam(required = false) String transportation) {
        TripDto tripDto = TripDto.builder()
                .id(tripId)
                .userName(userName)
                .cityId(cityId)
                .startDate(startDate)
                .endDate(endDate)
                .description(description)
                .transportation(transportation)
                .build();

        Trip trip = tripService.updateTrip(tripDto);
        TripDto updatedTripDto = trip.mapTripEntityToTripDto();
        return TripResponseDto.builder()
                .message("여행 수정 성공")
                .trip(updatedTripDto)
                .build();
    }

    @DeleteMapping("/trips")
    public TripResponseDto deleteTrip(@RequestParam Long tripId) {
        tripService.deleteTrip(tripId);
        return TripResponseDto.builder()
                .message("여행 삭제 성공")
                .build();
    }
}
