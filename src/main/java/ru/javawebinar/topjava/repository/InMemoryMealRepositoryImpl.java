package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository {

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    private static Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            if (counter==null) {System.out.println ("null");} else System.out.println ("not null");
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
                repository.remove(id);
    }

    @Override
    public Meal get(int id) {
                return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
                return repository.values();
    }
}

