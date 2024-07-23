package com.moayo.moayoeats.backend.domain.post.repository;

import com.moayo.moayoeats.backend.domain.post.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;
    private final int PAGE_SIZE = 5;

    @Override
    public List<Post> getPosts(TypedQuery<Post> query, int page) {
        int offset = page * PAGE_SIZE;

        query.setFirstResult(offset);
        query.setMaxResults(PAGE_SIZE);

        return query.getResultList();
    }
}
