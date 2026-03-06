package repository;

import domain.Users;

public class UserRepository extends BaseRepository<Users,Long> {

    public UserRepository() {
        super(Users.class);
    }
}
