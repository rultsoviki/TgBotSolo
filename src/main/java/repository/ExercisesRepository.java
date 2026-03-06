package repository;

import domain.Exercises;

public class ExercisesRepository extends BaseRepository<Exercises, Long> {

    public ExercisesRepository() {
        super(Exercises.class);
    }
}
