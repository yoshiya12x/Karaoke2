package com.example.xjapan.karaoke2.db.dao;

import com.example.xjapan.karaoke2.model.OrmaDatabase;
import com.example.xjapan.karaoke2.model.User;

/**
 * Created by xjapan on 16/03/05.
 */
public class UserDAO {
    private static final UserDAO dao = new UserDAO();

    private OrmaDatabase orma;

    UserDAO() {
    }

    static void inject(OrmaDatabase orma) {
        dao.orma = orma;
    }

    static void leave() {
        dao.orma = null;
    }

    public static UserDAO get() {
        return dao;
    }

    public User findFirst() {
        assert orma != null;

        return orma.selectFromUser().getOrNull(0);
    }

    public long insert(User user) {
        assert user != null;

        return orma.insertIntoUser(user);
    }
}
