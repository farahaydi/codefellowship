package com.example.CodeFellowship.Repositories;

import com.example.CodeFellowship.Models.ApplicationUser;
import com.example.CodeFellowship.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

//public interface PostRepository extends JpaRepository<Post,Long> {
//    List<Post> findByUserId(ApplicationUser applicationUser);
//    List<Post> findByUserInOrderByCreatedAtDesc(List<ApplicationUser> users);
//
//}
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(ApplicationUser applicationUser);

    List<Post> findByUserIdInOrderByCreatedAtDesc(List<ApplicationUser> users);

}