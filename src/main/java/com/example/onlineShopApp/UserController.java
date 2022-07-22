package com.example.onlineShopApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    UserSession userSession;

    @GetMapping("/register")
    public ModelAndView register(){
        return new ModelAndView("register");
    }

    @PostMapping("/register-action")
    public ModelAndView registerAction(
            @RequestParam("username") String username,
            @RequestParam("password1") String password1,
            @RequestParam("password2") String password2
            ){
        ModelAndView modelAndView = new ModelAndView("register");
        if (!password1.equals(password2)){
            modelAndView.addObject("message", "Parolele nu sunt identice");
            return modelAndView;
        }
        try {
            userService.checkAndAddUser(username, password1);
        }catch (UserException e){
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        }
        return new ModelAndView("redirect:/index.html");
    }

    @PostMapping("/login-action")
    public ModelAndView loginAction(@RequestParam("username") String username,
                                    @RequestParam("password") String password){
        //verificam daca exista in bd, si daca da, verificam parola
        ModelAndView modelAndView = new ModelAndView("index");
        try{
            userService.checkAndLoginUser(username, password);
        }catch (UserException e){
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        }
        userSession.setId(userService.getUserId(username));
        return new ModelAndView("redirect:/dashboard");
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard(){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        if (userSession.getId() == 0) {
            return new ModelAndView("index");
        }
        modelAndView.addObject("products", productService.getAllProducts());
        modelAndView.addObject("shoppingCartSize", userSession.getCartSize());

        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(){
        userSession.setId(0);
        return new ModelAndView("index");
    }











}
