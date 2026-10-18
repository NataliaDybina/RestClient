package org.example.restclient.service;

import org.example.restclient.model.Post;
import org.example.restclient.model.PostRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PostsService {

    private final RestClient restClient;

    public PostsService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://jsonplaceholder.typicode.com/posts")
                .build();
    }

    public List<Post> getAllPosts() {
        return restClient.get()
                .retrieve()
                .body(new ParameterizedTypeReference<List<Post>>() {
                });
    }

    public Post createPost(PostRequest postRequest) {
        return restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(postRequest)
                .retrieve()
                .body(Post.class);
    }

    public void deletePost(int id) {
        restClient.delete().uri("/{id}", id).retrieve().toBodilessEntity();
    }

    public Post updatePost(Post post, int id) {
        return restClient.put()
                .uri("/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(post)
                .retrieve()
                .body(Post.class);
    }
}
