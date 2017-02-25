package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.core.ApplicationContextHolder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = appCtx.getBean(MealRestController.class);
        new ApplicationContextHolder().setApplicationContext(appCtx);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                AuthorizedUser.id(), LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            mealController.create(meal);
        } else {
            mealController.update(meal, meal.getId());
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                LOG.info("findByUser");
                request.setAttribute("meals",
                        mealController.findByUser());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);

            } else if ("delete".equals(action)) {
                int id = getId(request);
                LOG.info("Delete {}", id);
                mealController.delete(id);
                response.sendRedirect("meals");
            } else if ("create".equals(action) || "update".equals(action)) {
                final Meal meal = action.equals("create") ?
                        new Meal(AuthorizedUser.id(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("meal.jsp").forward(request, response);
            } else if ("filter".equals(action)) {
                LocalDate startDate = ServletExtractorUtil.extractOrDefault(request, "startDate", LocalDate.MIN, LocalDate::parse);
                LocalDate endDate = ServletExtractorUtil.extractOrDefault(request, "endDate", LocalDate.MAX, LocalDate::parse);
                LocalTime startTime = ServletExtractorUtil.extractOrDefault(request, "startTime", LocalTime.MIN, LocalTime::parse);
                LocalTime endTime = ServletExtractorUtil.extractOrDefault(request, "endTime", LocalTime.MAX, LocalTime::parse);
                Collection<MealWithExceed> meals = mealController.findFilteredByDateAndTimeByUser(startDate, endDate, startTime, endTime);
                request.setAttribute("meals", meals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            }
        } catch (DateTimeParseException e) {
            response.sendError(400, e.getMessage());
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
