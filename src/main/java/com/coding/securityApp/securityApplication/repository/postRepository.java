package com.coding.securityApp.securityApplication.repository;

import com.coding.securityApp.securityApplication.entities.postEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface postRepository extends JpaRepository<postEntity,Long> {
}
