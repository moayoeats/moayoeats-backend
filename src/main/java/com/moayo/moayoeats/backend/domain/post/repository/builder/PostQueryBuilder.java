package com.moayo.moayoeats.backend.domain.post.repository.builder;

import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.locationtech.jts.geom.Point;
import java.util.HashMap;
import java.util.Map;

public class PostQueryBuilder {

    private final StringBuilder jpql;
    @PersistenceContext
    private final EntityManager entityManager;
    private final Map<String, Object> parameters = new HashMap<>();

    public PostQueryBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.jpql = new StringBuilder("SELECT p FROM Post p WHERE 1=1");
    }

    public PostQueryBuilder withStatus(PostStatusEnum status) {
        if (status != null) {
            jpql.append(" AND p.postStatus = :status");
            parameters.put("status", status);
        }
        return this;
    }

    public PostQueryBuilder withKeyword(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            jpql.append(" AND p.store LIKE :keyword");
            parameters.put("keyword", "%" + keyword + "%");
        }
        return this;
    }

    public PostQueryBuilder withCuisine(String cuisine) {
        if (cuisine != null && !cuisine.trim().isEmpty()) {
            jpql.append(" AND p.cuisine = :cuisine");
            parameters.put("cuisine", cuisine);
        }
        return this;
    }

    public PostQueryBuilder orderByDistance(User user) {
        Point userLocation = user.getLocation();
        if (userLocation != null) {
            jpql.append(" AND p.location IS NOT NULL ORDER BY distance(p.location, :userLocation) ASC");
            parameters.put("userLocation", userLocation);
            return andOrderByDeadline();
        }
        return orderByDeadline();
    }

    public PostQueryBuilder andOrderByDeadline() {
        jpql.append(", p.deadline DESC");
        return this;
    }

    public PostQueryBuilder orderByDeadline() {
        jpql.append(" ORDER BY p.deadline DESC");
        return this;
    }

    public TypedQuery<Post> build() {
        TypedQuery<Post> query = entityManager.createQuery(jpql.toString(), Post.class);
        parameters.forEach(query::setParameter);
        return query;
    }
}
