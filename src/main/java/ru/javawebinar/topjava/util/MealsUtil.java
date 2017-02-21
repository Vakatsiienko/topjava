package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {
    public static final Map<Integer, Meal> MEAL_BY_ID;
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(0);

    static {
        MEAL_BY_ID = new ConcurrentHashMap<>();
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static void create(Meal meal) {
        Meal created = new Meal(ID_COUNTER.incrementAndGet(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        MEAL_BY_ID.put(created.getId(), created);
    }

    public static boolean update(Integer id, Meal meal) {
        Meal updated = new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        if (MEAL_BY_ID.put(id, updated) == null) {
            MEAL_BY_ID.remove(id);
            return false;
        }
        return true;
    }

    public static boolean delete(Integer id) {
        return MEAL_BY_ID.remove(id) != null;
    }

    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<MealWithExceed> mealsWithExceeded = getFilteredWithExceeded(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealWithExceed> mealsWithExceeded = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExceeded.add(createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExceeded;
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }
}