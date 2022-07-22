package com.example.onlineShopApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public void checkAndAddUser(String username, String pasword) throws UserException{
        //findByUSername -> daca exista, arunc o exceptie
        //daca nu exista, o sa-l inserez in bd
        User user = userDao.findByUsername(username);
        if (user == null){
            if (pasword.length()<3){
                throw new UserException("parola are < 3 caractere");
            }
            User userToInsert = new User();
            userToInsert.setUsername(username);
            userToInsert.setPassword(pasword);
            userDao.save(userToInsert);
        } else {
            throw new UserException("utilizatorul exista deja");
        }
    }

    public void checkAndLoginUser(String username, String password) throws UserException{
        User user = userDao.findByUsername(username);
        if (user == null){
            throw new UserException("userul nu a putut fi autentificat");
        }
        if (!user.getPassword().equals(password)){
            throw new UserException("userul nu a putut fi autentificat");
        }
    }

    public int getUserId(String username){
        User user = userDao.findByUsername(username);
        return user.getId();
    }
}
