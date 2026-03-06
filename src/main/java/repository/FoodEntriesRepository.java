package repository;

import domain.FoodEntries;

public class FoodEntriesRepository extends BaseRepository<FoodEntries, Long> {

    public FoodEntriesRepository() {
        super(FoodEntries.class);
    }
}