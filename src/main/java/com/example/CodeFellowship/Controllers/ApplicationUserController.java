package com.example.CodeFellowship.Controllers;
import com.example.CodeFellowship.Models.ApplicationUser;
import com.example.CodeFellowship.Models.Post;
import com.example.CodeFellowship.Repositories.ApplicationUserRepository;
import com.example.CodeFellowship.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private PostRepository postRepository;
    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }
    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup.html";
    }

    @GetMapping("/logout")
    public String getLogoutPage() {
        return "index.html";
    }
    @PostMapping("/signup")
    public RedirectView createUser(String username, String password, String firstName, String lastName, @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth, String bio, String image) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(username);
        String encPass = passwordEncoder.encode(password);
        applicationUser.setPassword(encPass);
        applicationUser.setFirstName(firstName);
        applicationUser.setLastName(lastName);
        applicationUser.setDateOfBirth(dateOfBirth);
        applicationUser.setBio(bio);
        applicationUser.setImage(image);
        if(applicationUser.getImage() == null || applicationUser.getImage().isEmpty()) {
            applicationUser.setImage("https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg");
        }
        applicationUserRepository.save(applicationUser);
        authWithServRequest(username, password);
        return new RedirectView("/");
    }

    public void authWithServRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/")
    public String getHome(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            m.addAttribute("username", username);
            m.addAttribute("profileLink", "/myprofile");
        }
        return "index.html";
    }
    @GetMapping("/myprofile")
    public String viewMyProfile(Principal p, Model model) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

            List<Post> posts = postRepository.findByUserId(applicationUser);
            model.addAttribute("user", applicationUser);
            model.addAttribute("posts", posts);
        }
        return "userinfo";
    }



    @GetMapping("/test")
    public String getTestPage(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            m.addAttribute("username", username);
        }
        return "/test.html";
    }
    @PutMapping("/myprofile")
    public RedirectView editUserInfo(Principal p,Model m, String username,String firstName, String lastName, @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth, String bio,String image, RedirectAttributes redir)
    {
        System.out.println("Received username: " + username);
        if((p!=null) && (p.getName().equals(username))){
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            System.out.println("Found user: " + applicationUser);
            m.addAttribute("id", applicationUser.getId());
            applicationUser.setUsername(username);
            applicationUser.setFirstName(firstName);
            applicationUser.setLastName(lastName);
            applicationUser.setDateOfBirth(dateOfBirth);
            applicationUser.setBio(bio);
            applicationUser.setImage(image);
            applicationUserRepository.save(applicationUser);
            System.out.println("User saved successfully");
        }else {
            redir.addFlashAttribute("errorMessage","You are not authorized to modify another user's information.");
        }
        return new RedirectView("/myprofile/");
    }

    @GetMapping("/userinfo/{id}")
    public String getUserInfo(@PathVariable Long id, Model model) {
        ApplicationUser user = applicationUserRepository.findById(id).orElse(null);

        if (user != null) {
            model.addAttribute("user", user);
            return "userinfo";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/userinfo")
    public String getUserInfoPage(Principal p, Model model) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            model.addAttribute("user", applicationUser);
        }
        return "findUser";
    }

    @ModelAttribute("users")
    public List<ApplicationUser> getUsers(Principal p) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
            List<ApplicationUser> users = applicationUserRepository.findAll();
            users.remove(applicationUser);
            return users;
        }
        return Collections.emptyList();
    }



    @PostMapping("/follow/{id}")
    public RedirectView followUser(Principal p, @PathVariable Long id) {
        ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser userToFollow = applicationUserRepository.findById(id).orElse(null);

        if (userToFollow != null) {
            currentUser.getFollowing().add(userToFollow);
            applicationUserRepository.save(currentUser);
        }

        return new RedirectView("/feed");
    }

    @GetMapping("/feed")
    public String viewFeed(Principal p, Model model) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

            List<Post> feedPosts = postRepository.findByUserIdInOrderByCreatedAtDesc(applicationUser.getFollowing());

            model.addAttribute("feedPosts", feedPosts);
            model.addAttribute("username", username);
        }

        return "feed";
    }






}