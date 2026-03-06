package repository;

import domain.Workouts;

public class WorkoutsRepository extends BaseRepository<Workouts, Long> {

    public WorkoutsRepository() {
        super(Workouts.class);
    }

}
