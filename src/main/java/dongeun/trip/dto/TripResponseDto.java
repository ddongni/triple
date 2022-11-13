package dongeun.trip.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripResponseDto {

    private String message;

    private TripDto trip;

    private List<TripDto> trips;
}
