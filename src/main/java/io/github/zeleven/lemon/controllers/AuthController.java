package io.github.zeleven.lemon.controllers;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import io.github.zeleven.lemon.entities.User;
import io.github.zeleven.lemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class AuthController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signin")
    public String signin() {
        return "signin";
    }

//    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signupForm(ModelAndView modelAndView) {
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("register");
        return modelAndView;
    }

//    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView signupPost(ModelAndView modelAndView, @Valid User user,
                             BindingResult bindingResult,
                             HttpServletRequest request,
                                   @RequestParam Map requestParams,
                                   RedirectAttributes redirect) {
        User userExists = userService.findByEmail(user.getEmail());
        if (userExists != null) {
            modelAndView.addObject("alreadyRegisteredMessage",
                    "Oops!  There is already a user registered with the email provided.");
            modelAndView.setViewName("register");
            bindingResult.reject("email");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else {
            Zxcvbn passwordCheck = new Zxcvbn();
            Strength strength = passwordCheck.measure((String) requestParams.get("password"));
            if (strength.getScore() < 3) {
                bindingResult.reject("password");
                redirect.addFlashAttribute("errorMessage",
                        "Your password is too weak.  Choose a stronger one.");
                modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
            }
            user.setPassword(bCryptPasswordEncoder.encode((CharSequence) requestParams.get("password")));
            user.setActive(1);

            userService.saveUser(user);
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

//    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView confirmPage(ModelAndView modelAndView,
                                    @RequestParam("token") String token) {
        User user = userService.findByConfirmationToken(token);
        if (user == null) {// No token found in DB
            modelAndView.addObject("invalidToken",
                    "Oops!  This is an invalid confirmation link.");
        } else {// Token found
            modelAndView.addObject("confirmationToken", user.getConfirmationToken());
        }
        modelAndView.setViewName("confirm");
        return modelAndView;
    }

//    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ModelAndView confirmPage(ModelAndView modelAndView,
                                    BindingResult bindingResult,
                                    @RequestParam Map requestParams, RedirectAttributes redirect) {
        modelAndView.setViewName("confirm");
        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure((String) requestParams.get("password"));
        if (strength.getScore() < 3) {
            bindingResult.reject("password");
            redirect.addFlashAttribute("errorMessage",
                    "Your password is too weak.  Choose a stronger one.");
            modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
        }

        User user = userService.findByConfirmationToken((String) requestParams.get("token"));
        user.setPassword(bCryptPasswordEncoder.encode((CharSequence) requestParams.get("password")));
        user.setActive(1);
        userService.saveUser(user);
        modelAndView.addObject("successMessage",
                "Your password has been set!");
        return modelAndView;
    }
}
