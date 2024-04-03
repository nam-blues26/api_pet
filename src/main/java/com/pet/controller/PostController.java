package com.pet.controller;

import com.pet.dto.request.PostRequest;
import com.pet.dto.request.ProductRequest;
import com.pet.dto.response.ProductResponse;
import com.pet.entity.Post;
import com.pet.entity.Product;
import com.pet.service.IPostService;
import com.pet.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("${project.pet.version.v1}/post")
public class PostController {

    @Autowired
    private IPostService postService;

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Post> addPost(@ModelAttribute PostRequest request) {
        return new ResponseEntity<>(postService.addPost(request), HttpStatus.CREATED);
    }

    @GetMapping("/get/{slug}")
    public ResponseEntity<Post> getPostBySlug(@PathVariable String slug) {
        return new ResponseEntity<>(postService.getPostBySlug(slug), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Post>> getAll() {
        return new ResponseEntity<>(postService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/update/{slug}")
    public ResponseEntity<Post> updatePost(@PathVariable String slug, @ModelAttribute PostRequest request) {
        return new ResponseEntity<>(postService.updatePost(slug, request), HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long id) {
        return new ResponseEntity<>(postService.deletePostById(id), HttpStatus.OK);
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/"+imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
                //return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
