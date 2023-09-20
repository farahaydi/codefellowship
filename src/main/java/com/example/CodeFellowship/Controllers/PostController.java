package com.example.CodeFellowship.Controllers;
import com.example.CodeFellowship.Models.ApplicationUser;
import com.example.CodeFellowship.Models.Post;
import com.example.CodeFellowship.Repositories.ApplicationUserRepository;
import com.example.CodeFellowship.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
@Controller
public class PostController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/create-Post")
    public RedirectView createPost(Principal p, @RequestParam String body) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser  = applicationUserRepository.findByUsername(username);

            Post post = new Post();
            post.setBody(body);
            post.setUserId(applicationUser);
            post.setCreatedAt(LocalDate.now());

            postRepository.save(post);
        }
        return new RedirectView("/posts");
    }

    @GetMapping("/posts")
    public String viewPosts(Principal p, Model model) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

            List<Post> posts = postRepository.findByUserId(applicationUser);
            model.addAttribute("user", applicationUser);
            model.addAttribute("posts", posts);
        }
        return "userinfo";
    }
}
