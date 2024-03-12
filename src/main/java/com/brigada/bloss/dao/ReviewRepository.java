package com.brigada.bloss.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brigada.bloss.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
