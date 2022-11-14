package dongeun.city.repository;

import dongeun.city.entity.City;

import java.time.LocalDateTime;
import java.util.List;

public interface CityRepositoryCustom {

    // 여행중인 도시 조회
    List<City> getCitiesOnTrip(LocalDateTime now, String userName);

    // 여행이 예정된 도시 조회
    List<City> getCitiesOnPlanning(LocalDateTime now, String userName);

    // 하루 이내에 등록된 도시 조회
    List<City> getRegisteredCitiesWithinOneDay(LocalDateTime now);

    // 최근 일주일 이내에 한 번이상 조회된 도시 조회
    List<City> getViewedCitiesWithinLastWeek(LocalDateTime now);

    // 위의 조건에 해당하지 않는 모든 도시
    List<City> getOtherRandomCities(LocalDateTime now);
}
