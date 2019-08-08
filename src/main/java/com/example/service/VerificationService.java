package com.example.service;

import com.example.entity.Role;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

//класс сервис с бизнес логикой, помечен аннотацией @Service для внедрения в классы контроллеры
@Service
public class VerificationService {

    private UserService userService;
    private RoleService roleService;
    private Validator validator;

    // сюда внедряются бины для получения объектов через которые получаем доступы к методам для взаимодействия с БД
    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // метод с логикой регистрации юзера
    public List<String> verificationRegistration(User user) {
        // получаем сообщения при неправильном вводе пароля или имени
        List<String> messages = new ArrayList<>();
        Set<ConstraintViolation<User>> constraintViolationSet = validator.validate(user);
        for(ConstraintViolation<User> constr : constraintViolationSet) {
            messages.add(constr.getMessage());
        }
        // если сообщение о неправильном вводе есть, возвращаем их
        if (messages.size() > 0) return messages;

        // возвращаем сообщение о неудаче в случае если юзер с таким именем уже существует
        if (userService.getUserByUsername(user.getUsername()) != null) {
            messages.add("fail");
            return messages;
        }
        // возвращаем сообщение о неудаче в случае неправильного подтверждения пароля
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            messages.add("fail");
            return messages;
        }
        // если все данные введены верно, добавляем юзера в БД
        userService.addUser(user);
        // возвращаем пустой список сообщений
        return messages;
    }

    // метод с логикой логинизации юзера
    public Map<Role, Boolean> verificationLogin(String username, String password, HttpSession httpSession) {
        // получаем юзера который хочет зайти на свою страницу
        User user = userService.getUserByUsername(username);
        Map<Role, Boolean> roleAndVerification = new HashMap<>();
        // если юзера не существует возвращаем пустую карту
        if (user == null) {
            roleAndVerification.put(null, false);
            return roleAndVerification;
        }
        // если пароль не верный - возвращаем пустую карту
        if (!password.equals(user.getPassword())) {
            roleAndVerification.put(null, false);
            return roleAndVerification;
        }
        // если у юзера роль администратора, возвращаем карту с ролью и подтверждением что веоификация пройдена
        for (Role role : user.getRoles()) {
            if (role.getId().equals(2L)) {
                roleAndVerification.put(role, true);
                // в http сессию добавляем аттрибут юзер
                httpSession.setAttribute("user", user);
                return  roleAndVerification;
            }
        }
        // если роль - юзер и верификация пройдена добавляем юзера в в http сессию и возвращаем карту с ролью и подтверждением
        roleAndVerification.put(roleService.getRoleUserById(1L), true);
        httpSession.setAttribute("user", user);
        return roleAndVerification;
    }
}
