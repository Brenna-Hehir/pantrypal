package edu.uga.cs.pantrypal.controller;

import edu.uga.cs.pantrypal.model.*;
import edu.uga.cs.pantrypal.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public static class CommentRequest {
        public Integer userId;
        public Integer recipeId;
        public String content;
    }

    @PostMapping
    public String postComment(@RequestBody CommentRequest request) {
        Optional<User> userOpt = userRepository.findById(request.userId);
        Optional<Recipe> recipeOpt = recipeRepository.findById(request.recipeId);

        if (userOpt.isEmpty() || recipeOpt.isEmpty()) {
            return "Invalid user or recipe ID";
        }

        Comment comment = new Comment();
        comment.setUser(userOpt.get());
        comment.setRecipe(recipeOpt.get());
        comment.setContent(request.content);

        commentRepository.save(comment);
        return "Comment posted";
    }

    @GetMapping("/recipe/{recipeId}")
    public List<Comment> getCommentsForRecipe(@PathVariable Integer recipeId) {
        return commentRepository.findByRecipe_RecipeId(recipeId);
    }
}
