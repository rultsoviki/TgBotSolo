package repository;

import domain.Users;

public class UserDao extends BaseDao<Users,Long>{

    public UserDao() {
        super(Users.class);
    }
}
