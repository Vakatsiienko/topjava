package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService mealService;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        final Meal expected = createMeal();
        final Meal created = mealService.save(expected, USER_ID);
        expected.setId(created.getId());
        final Meal actual = mealService.get(expected.getId(), USER_ID);
        MATCHER.assertEquals(expected, actual);
        MATCHER.assertEquals(actual, created);
    }

    @Test(expected = NotFoundException.class)
    public void testGetSomeoneElseMeal() throws Exception {
        Meal expected = createMeal();
        Meal created = mealService.save(expected, ADMIN_ID);
        expected.setId(created.getId());
        mealService.get(expected.getId(), USER_ID);
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        final Meal expected = createMeal();
        final Meal created = mealService.save(expected, USER_ID);
        mealService.delete(created.getId(), USER_ID);
    }
    @Test
    public void testDeleteUnSuccess() throws Exception {
        final Meal expected = createMeal();
        final Meal created = mealService.save(expected, USER_ID);
        //successful delete
        mealService.delete(created.getId(), USER_ID);
        try {
            mealService.delete(created.getId(), USER_ID);
            Assert.fail("Expected NotFoundException exception");
        } catch (NotFoundException e) {
            //expected
        }

    }

    @Test(expected = NotFoundException.class)
    public void testDeleteSomeoneElseMeal() throws Exception {
        final Meal meal = createMeal();
        final Meal created = mealService.save(meal, ADMIN_ID);
        mealService.delete(created.getId(), USER_ID);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<Meal> betweenDatesCollection = mealService.getBetweenDates(LocalDate.of(2015, 5, 30), LocalDate.of(2015, 5, 30), USER_ID);
        Assert.assertEquals(betweenDatesCollection.size(), 3);
        Collection<Meal> allCollection = mealService.getBetweenDates(DateTimeUtil.MIN_DATE, DateTimeUtil.MAX_DATE, USER_ID);
        Assert.assertEquals(allCollection.size(), 6);
    }

    @Test
    public void testGetBetweenDatesSomeoneElseMeal() throws Exception {
        LocalDate mealDate = LocalDate.of(2015, 5, 29);
        Meal someoneElseMeal = new Meal(LocalDateTime.of(mealDate, LocalTime.of(10, 0)), "Someone else meal", 1000);
        mealService.save(someoneElseMeal, ADMIN_ID);
        Collection<Meal> betweenDatesCollection = mealService.getBetweenDates(mealDate, mealDate, USER_ID);
        Assert.assertTrue(betweenDatesCollection.isEmpty());
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<Meal> betweenDatesCollection = mealService.getBetweenDates(LocalDate.of(2015, 5, 30), LocalDate.of(2015, 5, 30), USER_ID);
        Assert.assertEquals(betweenDatesCollection.size(), 3);
        Collection<Meal> allCollection = mealService.getBetweenDateTimes(
                LocalDateTime.of(DateTimeUtil.MIN_DATE, LocalTime.MIN),
                LocalDateTime.of(DateTimeUtil.MAX_DATE, LocalTime.MAX), USER_ID);
        Assert.assertEquals(allCollection.size(), 6);
    }
    @Test
    public void testGetBetweenDateTimesSomeoneElseMeal() throws Exception {
        LocalDateTime mealDateTime = LocalDateTime.of(2015, 5, 29, 10, 0);
        Meal someoneElseMeal = createMeal();
        mealService.save(someoneElseMeal, ADMIN_ID);
        Collection<Meal> betweenDatesCollection = mealService.getBetweenDateTimes(mealDateTime, mealDateTime, USER_ID);
        Assert.assertTrue(betweenDatesCollection.isEmpty());
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> meals = mealService.getAll(USER_ID);
        Assert.assertEquals(meals.size(), 6);
        mealService.delete(meals.iterator().next().getId(), USER_ID);
        meals = mealService.getAll(USER_ID);
        Assert.assertEquals(meals.size(), 5);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = mealService.getAll(USER_ID).iterator().next();
        changeMeal(meal);
        Meal returned = mealService.update(meal, USER_ID);
        Meal updated = mealService.get(meal.getId(), USER_ID);
        MATCHER.assertEquals(meal, returned);
        MATCHER.assertEquals(meal, updated);
    }
    private void changeMeal(Meal meal){
        meal.setCalories(meal.getCalories() + 40);
        meal.setDateTime(meal.getDateTime().plusDays(10));
        meal.setDescription("Updated " + meal.getDescription());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateSomeoneElseMeal() throws Exception {
        Meal meal = mealService.getAll(ADMIN_ID).iterator().next();
        changeMeal(meal);
        mealService.update(meal, USER_ID);
    }

    @Test
    public void testSave() throws Exception {
        Meal savingMeal = createMeal();
        Meal returned = mealService.save(savingMeal, USER_ID);
        savingMeal.setId(returned.getId());
        Meal actual = mealService.get(returned.getId(), USER_ID);
        MATCHER.assertEquals(savingMeal, returned);
        MATCHER.assertEquals(savingMeal, actual);
    }
    public Meal createMeal(){
        return new Meal(LocalDateTime.now(), "Description", 1000);
    }
}