package com.example.xjapan.karaoke2.usecase.initialization;

import com.example.xjapan.karaoke2.infra.db.dao.UserDAO;

/**
 * Created by jmatsu on 2016/02/27.
 */
public class CheckRegisteredUserUseCase {
    private final UserDAO dao = UserDAO.get();

    public boolean apply() {
        return dao.findFirst() != null;
    }
}
