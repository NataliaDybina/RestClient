package org.example.restclient.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class PostsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @AfterEach
    void tearDown() {
        mockRestServiceServer.verify();
    }

    @Test
    void getAllPosts() throws Exception {
        mockRestServiceServer.expect(requestTo("https://jsonplaceholder.typicode.com/posts"))
                .andRespond(withSuccess(
                        """
                                [
                                {
                                "userId": 1,
                                                                          "id": 1,
                                                                          "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                                                                          "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et "
                                                }    
                                                ]           
                                """, MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/post"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                                [\s
                                                {  \s
                                                  "userId": 1,
                                                  "id": 1,
                                                  "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                                                  "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et "
                                              }
                                              ]
                        \s"""));
    }

    @Test
    void createPost() throws Exception {
        mockRestServiceServer.expect(requestTo("https://jsonplaceholder.typicode.com/posts"))
                .andRespond(withSuccess(
                        """
                                {
                                  "title": "foo",
                                  "body": "bar",
                                  "userId": 1,
                                  "id": 101
                                }
                                """, MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "foo",
                                  "body": "bar",
                                  "userId": 1,
                                  "id": 101
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                          "title": "foo",
                          "body": "bar",
                          "userId": 1,
                          "id": 101
                        }
                        """));

    }

    @Test
    void deletePost() throws Exception {
        mockRestServiceServer.expect(requestTo("https://jsonplaceholder.typicode.com/posts/1"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/post/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updatePost() throws Exception {
        mockRestServiceServer.expect(requestTo("https://jsonplaceholder.typicode.com/posts/1"))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().json("""
                        {
                            "title": "foo",
                            "body": "bar",
                            "userId": 1,
                            "id": 101
                        }
                        """))
                .andRespond(withSuccess(
                        """
                                {
                                  "title": "foo",
                                  "body": "bar",
                                  "userId": 1,
                                  "id": 101
                                }
                                """, MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/post/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                          "title": "foo",
                                          "body": "bar",
                                          "userId": 1,
                                          "id": 101
                                        }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                                                          "title": "foo",
                                                          "body": "bar",
                                                          "userId": 1,
                                                          "id": 101
                                                        }
                        """));
    }

}