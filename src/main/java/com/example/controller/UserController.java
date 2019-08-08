package com.example.controller;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;

// класс контроллер помечен аннотацией, что бы Spring внедрил его в необходимое место. взаимодействует с отображением и сервисами
@Controller
public class UserController {

    private UserService userService;
    private Validator validator;
    private VerificationService verificationService;

    // внедряем объекты сервисов
    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Autowired
    public void setVerificationService(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // по маппингу "/" переходим на страницу login
    @RequestMapping("/")
    public String home() {
        return "views/login";
    }

    // по данному маппингу возвращаем страницу логин
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "views/login";
    }

    // по данному маппингу с входящими параметрами (приходят с веб страницы) возвращаем либо эту же страницу, либо преходим на страницу юзера
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password, HttpSession httpSession) {
        if (username.equals("") || password.equals("")) {
            return "redirect:/login";
        }
        // проходим верификацию в сервисе верификации
        Map<Role, Boolean> roleAndVerification = verificationService.verificationLogin(username, password, httpSession);
        Boolean ver = null;
        for (Map.Entry<Role, Boolean> pair : roleAndVerification.entrySet()) {
            ver = pair.getValue();
        }
        // сли верификация не пройдена остаемся на этой же странице
        if (!ver) {
            return "redirect:/login";
        } else {
            // если верификация пройдена обнавляем статус пользователя на "залогинен" и добавляем в http сессию
            User user = (User) httpSession.getAttribute("user");
            user.setLoggingIn(true);
            userService.update(user);
            // возвращаем страницу пользователя
            return "redirect:/userPage";
        }
    }

    //возвращаем страницу регистрации
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        return "views/registration";
    }

    // Получаем введенные параметры
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration(@RequestParam String username, @RequestParam String password,
                                     @RequestParam String confirmPassword) {
        ModelAndView modelAndView = new ModelAndView();
        if (username.equals("") || password.equals("") || confirmPassword.equals("")) {
            modelAndView.setViewName("views/registration");
            return modelAndView;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setConfirmPassword(confirmPassword);

        // проверяем правильность ввода данных
        List<String> messages = verificationService.verificationRegistration(user);
        StringBuilder sb = new StringBuilder();
        // если есть сообщения о неправильной регистрации - выводим их на страницу и возвращаем эту же страницу
        if (messages.size() > 0) {
            sb.append("Wrong");
            messages.forEach(m -> sb.append(" " + m));
            sb.append("!");
            modelAndView.addObject("wrong", sb.toString());
            modelAndView.setViewName("views/registration");
            return modelAndView;
        }
        // при верной регистрации переходим на страницу с логином
        modelAndView.addObject("wrong", sb.toString());
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

}