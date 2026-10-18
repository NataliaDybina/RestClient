package org.example.restclient.controller;

import org.example.restclient.model.Post;
import org.example.restclient.model.PostRequest;
import org.example.restclient.service.PostsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostsController {

    private final PostsService postsService;

    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postsService.getAllPosts();
    }

    @PostMapping
    public Post createPost(@RequestBody PostRequest post) {
        return postsService.createPost(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable int id) {
        postsService.deletePost(id);
    }

    @PutMapping("/{id}")
    public Post updatePost(@RequestBody Post post, @PathVariable int id) {
        return postsService.updatePost(post, id);
    }
}
