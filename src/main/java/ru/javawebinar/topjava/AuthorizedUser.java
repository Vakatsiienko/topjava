package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser {

    private static AtomicInteger id = new AtomicInteger(1);

    public static int id() {
        return id.get();
    }

    public static void setAdmin() {
        id.set(1);
    }

    public static void setUser() {
        id.set(2);
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
