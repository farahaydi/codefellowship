package com.example.CodeFellowship.Controllers;
import com.example.CodeFellowship.Models.ApplicationUser;
import com.example.CodeFellowship.Repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }
    @GetMapping("/signup")
    public String getSignupPage()
    {
        return "signup.html";
    }
    @GetMapping("/logout")
    public String getLogoutPage()
    {
        return "index.html";
    }
//    (String username, String password, String firstName, String lastName, Date dateOfBirth, String bio) {
@PostMapping("/signup")
public RedirectView createUser(String username, String password, String firstName, String lastName, @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth, String bio) {
    ApplicationUser applicationUser = new ApplicationUser();
    applicationUser.setUsername(username);
    String encPass = passwordEncoder.encode(password);
    applicationUser.setPassword(encPass);
    applicationUser.setFirstName(firstName);
    applicationUser.setLastName(lastName);
    applicationUser.setDateOfBirth(dateOfBirth);
    applicationUser.setBio(bio);
    applicationUserRepository.save(applicationUser);
    authWithServRequest(username, password);
    return new RedirectView("/");
}

    public void authWithServRequest(String username, String password)
    {
        try{
            request.login(username,password);
        }catch (ServletException e)
        {
            e.printStackTrace();
        }
    }
@GetMapping("/")
public String getHome(Principal p, Model m) {
    if (p != null) {
        String username = p.getName();
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        m.addAttribute("username", username);
        m.addAttribute("profileLink", "/userinfo");
    }
    return "index.html";
}

    @GetMapping("/userinfo")
    public String viewProfile(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            m.addAttribute("user", applicationUser);
            return "userinfo.html";
        }
        return "redirect:/login";
    }


}
