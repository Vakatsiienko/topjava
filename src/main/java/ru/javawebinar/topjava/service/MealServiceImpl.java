package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        Meal meal = repository.get(id);
        checkOwnerAndExist(meal, userId);
        repository.delete(id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal meal = repository.get(id);
        checkOwnerAndExist(meal, userId);
        return meal;
    }

    @Override
    public Collection<MealWithExceed> findByUserInDateAndTimeRange(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        Collection<Meal> meals = repository.findByUserInDateRange(userId, startDate, endDate);
        return MealsUtil.getFilteredWithExceeded(meals, startTime, endTime, AuthorizedUser.getCaloriesPerDay());
    }

    @Override
    public Collection<MealWithExceed> findByUser(int userId) {
        Collection<Meal> meals = repository.findByUser(userId);
        return MealsUtil.getWithExceeded(meals, AuthorizedUser.getCaloriesPerDay());
    }

    @Override
    public void update(Meal meal, int userId) {
        Meal beforeUpdate = repository.get(meal.getId());
        checkOwnerAndExist(beforeUpdate, userId);
        repository.save(meal);
    }

    private void checkOwnerAndExist(Meal meal, int userId) {
        if (meal == null || !meal.getUserId().equals(userId)) {
            throw new NotFoundException("You do not have meal with such id");
        }
    }
}
