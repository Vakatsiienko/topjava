package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService mealService;

    public Collection<MealWithExceed> findByUser() {
        LOG.info("findByUser");
        return mealService.findByUser(AuthorizedUser.id());
    }

    public Collection<MealWithExceed> findFilteredByDateAndTimeByUser(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LOG.info("findFilteredByDateAndTimeByUser");
        return mealService.findByUserInDateAndTimeRange(AuthorizedUser.id(), startDate, endDate, startTime, endTime);
    }
//    public Collection<MealWithExceed> findFilteredByDate(LocalDate startDate, LocalDate endDate) {
//        LOG.info("findFilteredByDate");
//        return mealService.findByUserInDateAndTimeRange(AuthorizedUser.id(), startDate, endDate, LocalTime.MIN, LocalTime.MAX);
//    }
//    public Collection<MealWithExceed> findFilteredByTime(LocalTime startTime, LocalTime endTime) {
//        LOG.info("findFilteredByTime");
//        return mealService.findByUserInDateAndTimeRange(AuthorizedUser.id(), LocalDate.MIN, LocalDate.MAX, startTime, endTime);
//    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return mealService.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        LOG.info("create " + meal);
        return mealService.save(meal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        mealService.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal, int mealId) {
        meal.setId(mealId);
        LOG.info("update " + meal);
        mealService.update(meal, AuthorizedUser.id());
    }

}
