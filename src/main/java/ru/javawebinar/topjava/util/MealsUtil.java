package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {
        List<MealWithExceed> mealsWithExceeded = getFilteredWithExceeded
                (MEALS, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExceeded.forEach(System.out::println);
        System.out.println(getFilteredWithExceeded(MEALS, LocalTime.of(7, 0),
                LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY));
    }

    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public static List<MealWithExceed>  getFilteredWithExceeded(Collection<Meal> mealList,
                                                                LocalTime startTime,
                                                                LocalTime endTime,
                                                                int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream().
                collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                Collectors.summingInt(Meal::getCalories)));
        return mealList.stream().filter(um -> DateTimeUtil.isBetween(um.getDateTime()
                .toLocalTime(), startTime, endTime))
                .map(um -> new MealWithExceed(um.getId(), um.getDateTime(), um.getDescription(),
                        um.getCalories(),
                        caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
