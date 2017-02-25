package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal meal);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    Collection<MealWithExceed> findByUserInDateAndTimeRange(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    Collection<MealWithExceed> findByUser(int userId);

    void update(Meal meal, int userId);
}
