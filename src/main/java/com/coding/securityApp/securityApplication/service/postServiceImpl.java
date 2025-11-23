package com.coding.securityApp.securityApplication.service;


import com.coding.securityApp.securityApplication.dto.postDTO;
import com.coding.securityApp.securityApplication.entities.postEntity;
import com.coding.securityApp.securityApplication.exceptions.ResourceNotFound;
import com.coding.securityApp.securityApplication.repository.postRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class postServiceImpl implements  postService{

    private final postRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<postDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity ->modelMapper.map(postEntity, postDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public postDTO createNewPost(postDTO inputPost) {
        postEntity postEntity=modelMapper.map(inputPost, postEntity.class);//converting to postEntity
        return modelMapper.map(postRepository.save(postEntity), postDTO.class);
    }

    @Override
    public postDTO getById(Long id) {
        postEntity postEntity=postRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFound("Post with Id not fount"));

        return modelMapper.map(postEntity, postDTO.class);
    }

    @Override
    public postDTO updatePost(postDTO inputPost, Long id) {
        postEntity olderPost=postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Post with Id not found"));
        inputPost.setId(id);
        modelMapper.map(inputPost,olderPost);
        postEntity savedEntity=postRepository.save(olderPost);
        return modelMapper.map(savedEntity, postDTO.class);
    }
}
