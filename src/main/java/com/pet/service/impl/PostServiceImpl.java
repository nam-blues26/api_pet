package com.pet.service.impl;

import com.pet.dto.request.PostRequest;
import com.pet.entity.Post;
import com.pet.entity.Product;
import com.pet.exception.NotFoundException;
import com.pet.repository.IPostRepository;
import com.pet.service.IPostService;
import com.pet.service.base.IMessageService;
import com.pet.utils.ImageUtil;
import com.pet.utils.MapperUtils;
import com.pet.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.pet.constant.MessageConstant.NOTFOUND_POST_EXCEPTION;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IMessageService messageService;
    @Override
    public Post addPost(PostRequest request) {
        Post post = MapperUtils.dtoToEntity(request, Post.class);
        String slug = UrlUtil.generateSlug(request.getPostTitle());
        post.setSlug(slug);
        try {
            String filename = ImageUtil.storeFile(request.getFiles());
            post.setImage(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(String slug,PostRequest request) {
        Post post = postRepository.findPostBySlug(slug).orElseThrow(
                ()-> new NotFoundException(messageService.getMessage(NOTFOUND_POST_EXCEPTION)));
        post.setPostDescription(request.getPostDescription());
        post.setPostTitle(request.getPostTitle());
        try {
            if (request.getFiles() != null) {
                String filename = ImageUtil.storeFile(request.getFiles());
                post.setImage(filename);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return postRepository.save(post);
    }

    @Override
    public Boolean deletePostById(Long id) {
        postRepository.deleteById(id);
        return true;
    }

    @Override
    public Post getPostBySlug(String slug) {
        return postRepository.findPostBySlug(slug).orElseThrow(()-> new NotFoundException(messageService.getMessage(NOTFOUND_POST_EXCEPTION)));
    }

    @Override
    public List<Post> getAll() {
        return  postRepository.findByOrderByIdDesc();
    }
}
