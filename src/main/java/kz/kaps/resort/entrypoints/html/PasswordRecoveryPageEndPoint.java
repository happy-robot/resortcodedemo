package kz.kaps.resort.entrypoints.html;

import kz.kaps.resort.core.usecase.exception.TokenExpiredException;
import kz.kaps.resort.core.usecase.exception.TokenNotFoundException;
import kz.kaps.resort.core.usecase.exception.UserNotFoundException;
import kz.kaps.resort.core.usecase.user.ResetPasswordWithSMSTokenUseCase;
import kz.kaps.resort.core.usecase.user.SendPasswordRecoverySMSTokenUseCase;
import kz.kaps.resort.core.usecase.utils.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class PasswordRecoveryPageEndPoint {

    private SendPasswordRecoverySMSTokenUseCase sendPasswordRecoverySMSTokenUseCase;
    private ResetPasswordWithSMSTokenUseCase resetPasswordWithSMSTokenUseCase;
    private MessageSource messageSource;

    @Autowired
    public PasswordRecoveryPageEndPoint(
            SendPasswordRecoverySMSTokenUseCase sendPasswordRecoverySMSTokenUseCase,
            ResetPasswordWithSMSTokenUseCase resetPasswordWithSMSTokenUseCase,
            MessageSource messageSource
    ){
        this.sendPasswordRecoverySMSTokenUseCase = sendPasswordRecoverySMSTokenUseCase;
        this.resetPasswordWithSMSTokenUseCase = resetPasswordWithSMSTokenUseCase;
        this.messageSource = messageSource;
    }

    @GetMapping("/password-recovery/username")
    public String passwordRecoveryUsernamePage(Model model){

        model.addAttribute("usernameForm", new UsernameForm());
        return "passwordRecoveryUsername";
    }

    @PostMapping("/password-recovery/username")
    public String getUsernameAndSendSMSToken(@ModelAttribute("usernameForm") @Valid UsernameForm usernameForm,
                                     BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs){
        if(bindingResult.hasErrors()) return "passwordRecoveryUsername";

        try {
            sendPasswordRecoverySMSTokenUseCase.sendPasswordRecoverySMSToken(usernameForm.getUsername());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            String errMng = messageSource.getMessage("validator.phonenumber.exists.in.db.constraint.message", null, LocaleUtils.getLocale());
            bindingResult.rejectValue("username", "error.username", errMng);
            return "passwordRecoveryUsername";
        }
        redirectAttrs.addAttribute("username", usernameForm.getUsername());//.addFlashAttribute("username", usernameForm.getUsername());
        return "redirect:/password-recovery/sms-token";
    }


    @GetMapping("/password-recovery/sms-token")
    public String passwordRecoverySMSTokenPage(
            @RequestParam(value = "username", required = true) String username,
            Model model
    ){
        PasswordRecoveryForm passwordRecoveryForm = new PasswordRecoveryForm();
        passwordRecoveryForm.setUsername(username);
        model.addAttribute("passwordRecoveryForm", passwordRecoveryForm);
        return "passwordRecoverySMSToken";
    }

    @PostMapping("/password-recovery/sms-token")
    public String doPasswordRecoverySMSToken(
            @ModelAttribute("passwordRecoveryForm") @Valid PasswordRecoveryForm passwordRecoveryForm,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs){

        if(bindingResult.hasErrors()) return "passwordRecoverySMSToken";

        String username = passwordRecoveryForm.getUsername();
        String newPassword = passwordRecoveryForm.getPassword();
        String SMSToken = passwordRecoveryForm.getToken();

        try {
            resetPasswordWithSMSTokenUseCase.resetPasswordWithSMSTokenUseCase(username, newPassword, SMSToken);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            String errMng = messageSource.getMessage("validator.phonenumber.exists.in.db.constraint.message", null, Locale.US);
            bindingResult.rejectValue("username", "error.username", errMng);
            return "passwordRecoverySMSToken";
        } catch (TokenNotFoundException e) {
            e.printStackTrace();
            String errMng = messageSource.getMessage("password-recovery.validatod.token.not.match", null, Locale.US);
            bindingResult.rejectValue("token", "error.token", errMng);
            return "passwordRecoverySMSToken";
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            String errMng = messageSource.getMessage("password-recovery.validatod.token.expired", null, Locale.US);
            bindingResult.rejectValue("token", "error.token", errMng);
            return "passwordRecoverySMSToken";
        }

        return "redirect:/password-recovery/success";
    }

    @GetMapping("/password-recovery/success")
    public String passwordRecoverySuccess(){
        return "passwordRecoverySuccess";
    }

}
