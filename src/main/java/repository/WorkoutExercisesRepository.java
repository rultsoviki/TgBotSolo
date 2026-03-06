package repository;

import domain.WorkoutExercises;

public class WorkoutExercisesRepository extends BaseRepository<WorkoutExercises, Long> {

    public WorkoutExercisesRepository() {
        super(WorkoutExercises.class);
    }

}
