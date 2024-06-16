package com.moayo.moayoeats.backend.domain.post.repository;

import com.moayo.moayoeats.backend.domain.post.entity.Post;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface PostCustomRepository {

    List<Post> getPosts(TypedQuery<Post> query, int page);

}
