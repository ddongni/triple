package dongeun.city.repository;

import dongeun.city.dto.CityDto;
import dongeun.city.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findByName(String name);
}
