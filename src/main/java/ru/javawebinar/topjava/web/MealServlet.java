package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Iaroslav on 2/20/2017.
 */
public class MealServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Integer, Meal> mealList = MealsUtil.MEAL_BY_ID;
        List<MealWithExceed> withExceedList = MealsUtil.getFilteredWithExceeded(mealList.values(), LocalTime.MIN, LocalTime.MAX, 2000);
        String strId = req.getParameter("id");
        try {
            if (strId != null) {
                Meal meal = MealsUtil.MEAL_BY_ID.get(Integer.parseInt(strId));
                req.setAttribute("updatingMeal", meal);
            }
        } catch (NumberFormatException e) {
            resp.sendError(400);
            return;
        }
        req.setAttribute("mealList", withExceedList);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        try {
            if ("POST".equals(method)) {
                String description = req.getParameter("description");
                LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"));
                Integer calories = Integer.parseInt(req.getParameter("calories"));
                Meal meal = new Meal(dateTime, description, calories);
                MealsUtil.create(meal);
            } else if ("DELETE".equals(method)) {
                Integer id = Integer.parseInt(req.getParameter("id"));
                MealsUtil.delete(id);
            } else if ("PUT".equals(method)) {
                Integer id = Integer.parseInt(req.getParameter("id"));
                String description = req.getParameter("description");
                LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"));
                Integer calories = Integer.parseInt(req.getParameter("calories"));
                Meal meal = new Meal(dateTime, description, calories);
                MealsUtil.update(id, meal);
            } else {
                resp.sendError(404);
                return;
            }
        } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
            resp.sendError(400);
            return;
        }
        resp.sendRedirect("/meals");
    }
}
