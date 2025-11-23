package com.coding.securityApp.securityApplication.service;

import com.coding.securityApp.securityApplication.dto.postDTO;

import java.util.List;

public interface postService {
    List<postDTO> getAllPosts();

    postDTO createNewPost(postDTO inputPost);

    postDTO getById(Long id);

    postDTO updatePost(postDTO inputPost, Long id);
}
