package com.pet.service;

import com.pet.dto.request.PostRequest;
import com.pet.entity.Post;

import java.util.List;

public interface IPostService {

    Post addPost(PostRequest request);
    Post updatePost(String slug, PostRequest request);
    Boolean deletePostById(Long id);
    Post getPostBySlug(String slug);
    List<Post> getAll();
}
