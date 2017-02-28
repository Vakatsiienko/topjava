package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;


/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final List<Meal> userMeal;
    public static final List<Meal> adminMeal;
    static {
        List<Meal> uMeals = new ArrayList<>();
        uMeals.add(new Meal(START_SEQ, LocalDateTime.of(2015, 5, 30, 10, 0), "Завтрак", 1000));
        uMeals.add(new Meal(START_SEQ + 1, LocalDateTime.of(2015, 5, 30, 10, 0), "Обед", 500));
        uMeals.add(new Meal(START_SEQ + 2, LocalDateTime.of(2015, 5, 30, 10, 0), "Ужин", 510));
        uMeals.add(new Meal(START_SEQ + 3, LocalDateTime.of(2015, 5, 31, 10, 0), "Завтрак", 1000));
        uMeals.add(new Meal(START_SEQ + 4, LocalDateTime.of(2015, 5, 31, 10, 0), "Обед", 500));
        uMeals.add(new Meal(START_SEQ + 5, LocalDateTime.of(2015, 5, 31, 10, 0), "Ужин", 500));
        userMeal = Collections.unmodifiableList(uMeals);

        List<Meal> aMeals = new ArrayList<>();
        aMeals.add(new Meal(START_SEQ + 6, LocalDateTime.of(2015, 5, 30, 10, 0), "Завтрак", 1000));
        aMeals.add(new Meal(START_SEQ + 7, LocalDateTime.of(2015, 5, 30, 10, 0), "Обед", 500));
        aMeals.add(new Meal(START_SEQ + 8, LocalDateTime.of(2015, 5, 30, 10, 0), "Ужин", 510));
        aMeals.add(new Meal(START_SEQ + 9, LocalDateTime.of(2015, 5, 31, 10, 0), "Завтрак", 1000));
        aMeals.add(new Meal(START_SEQ + 10, LocalDateTime.of(2015, 5, 31, 10, 0), "Обед", 500));
        aMeals.add(new Meal(START_SEQ + 11, LocalDateTime.of(2015, 5, 31, 10, 0), "Ужин", 500));
        adminMeal = Collections.unmodifiableList(aMeals);
    }
    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getCalories(), actual.getCalories()) &&
                            Objects.equals(expected.getDateTime(), actual.getDateTime()) &&
                            Objects.equals(expected.getDescription(), actual.getDescription()) &&
                            Objects.equals(expected.getId(), actual.getId()))
    );
}
