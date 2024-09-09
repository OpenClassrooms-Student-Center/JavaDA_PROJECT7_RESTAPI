package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.RepositoryDefinition;
@RepositoryDefinition(domainClass = Rating.class, idClass = Integer.class)
public interface RatingRepository {
    Rating save(Rating rating);
    List<Rating> findAll();
    Optional<Rating> findByRatingid(int id);
    void deleteByRatingid(int rating);
}
