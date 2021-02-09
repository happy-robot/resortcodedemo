package kz.kaps.resort.entrypoints.html;

import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.exception.UserAlreadyExistsException;
import kz.kaps.resort.core.usecase.user.CreateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mobile.device.Device;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class SignUpPageEndPoint {

    private CreateUserUseCase createUserUseCase;
    private PasswordEncoder passwordEncoder;
    private MessageSource messageSource;

    @Autowired
    public SignUpPageEndPoint(CreateUserUseCase createUserUseCase, PasswordEncoder passwordEncoder,
                              MessageSource messageSource){
        this.createUserUseCase = createUserUseCase;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    @GetMapping("/sign-up")
    public String signUpPage(Model model){

        model.addAttribute("signUpForm", new SignUpForm());
        return "signUp";
    }

    @PostMapping("/sign-up")
    public String signUpProcess(@ModelAttribute("signUpForm") @Valid SignUpForm signUpForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) return "signUp";

        User user = signUpForm.toUser(passwordEncoder);
        try {
            createUserUseCase.createUser(user);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            String errMng = messageSource.getMessage("signup.form.phone.err.already.exists", null, Locale.US);
            bindingResult.rejectValue("username", "error.username", errMng);
            return "signUp";
        }
        return "redirect:/sign-up/success";
    }

    @GetMapping("/sign-up/success")
    public String signUpSuccessPage(Device device, Model model){
        String viewName = "signUpSuccess";
        if (device.isMobile()) {
            viewName = "mobile/signUpSuccess";
        }
        return viewName;
    }
}
