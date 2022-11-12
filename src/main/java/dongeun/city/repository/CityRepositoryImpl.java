package dongeun.city.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dongeun.city.entity.City;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static dongeun.city.entity.QCity.city;
import static dongeun.trip.entity.QTrip.trip;

public class CityRepositoryImpl implements CityRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CityRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 여행중인 도시 조회
    @Override
    public List<City> getCitiesOnTrip(LocalDateTime now, String userName) {
        return queryFactory
                .selectFrom(city)
                .innerJoin(trip)
                .on(city.id.eq(trip.city.id))
                .where(equalTripUserName(userName),
                        isNowDateBetweenTripStartAndEndDate(now))
                .orderBy(trip.startDate.asc())
                .fetch();
    }

    // 여행이 예정된 도시 조회
    @Override
    public List<City> getCitiesOnPlanning(LocalDateTime now, String userName) {
        return queryFactory
                .selectFrom(city)
                .innerJoin(trip)
                .on(city.id.eq(trip.city.id))
                .where(equalTripUserName(userName),
                        isTripStartDateAfterNowDate(now))
                .orderBy(trip.startDate.asc())
                .limit(10)
                .fetch();
    }

    // 하루 이내에 등록된 도시 조회
    @Override
    public List<City> getRegisteredCitiesWithinOneDay(LocalDateTime now) {
        return queryFactory
                .selectFrom(city)
                .leftJoin(trip)
                .on(city.id.eq(trip.city.id))
                .where(isTripCityIdNull(),
                        isRegisteredCityWithinOneDay(now))
                .orderBy(city.createdAt.asc())
                .limit(10)
                .fetch();
    }

    // 최근 일주일 이내에 한 번이상 조회된 도시 조회
    @Override
    public List<City> getViewedCitiesWithinLastWeek(LocalDateTime now) {
        return queryFactory
                .selectFrom(city)
                .join(trip)
                .on(city.id.eq(trip.city.id))
                .where(isTripCityIdNull(),
                        isNotRegisteredCityWithinOneDay(now),
                        isViewedCityWithinLastWeek(now))
                .orderBy(city.modifiedAt.asc())
                .limit(10)
                .fetch();
    }

    // 위의 조건에 해당하지 않는 모든 도시
    @Override
    public List<City> getOtherRandomCities(LocalDateTime now) {
        return queryFactory
                .selectFrom(city)
                .join(trip)
                .on(city.id.eq(trip.city.id))
                .where(isTripCityIdNull(),
                        isNotRegisteredCityWithinOneDay(now),
                        isViewedCityNotWithinLastWeek(now))
                .limit(10)
                .fetch();
    }

    private BooleanExpression equalTripUserName(String userName) {
        return trip.userName.eq(userName);
    }

    private BooleanExpression isTripStartDateAfterNowDate(LocalDateTime now) {
        return trip.startDate.after(now.toLocalDate());
    }

    private BooleanExpression isNowDateBetweenTripStartAndEndDate(LocalDateTime now) {
        return trip.startDate.before(now.toLocalDate())
                .and(trip.endDate.after(now.toLocalDate()));
    }

    private BooleanExpression isTripCityIdNull() {
        return trip.city.id.isNull();
    }

    private BooleanExpression isRegisteredCityWithinOneDay(LocalDateTime now) {
        return city.createdAt.between(now.minusDays(1), now);
    }

    private BooleanExpression isNotRegisteredCityWithinOneDay(LocalDateTime now) {
        return city.createdAt.notBetween(now.minusDays(1), now);
    }

    private BooleanExpression isViewedCityWithinLastWeek(LocalDateTime now) {
        return city.modifiedAt.between(now.minusWeeks(1), now);
    }

    private BooleanExpression isViewedCityNotWithinLastWeek(LocalDateTime now) {
        return city.modifiedAt.notBetween(now.minusWeeks(1), now);
    }
}
