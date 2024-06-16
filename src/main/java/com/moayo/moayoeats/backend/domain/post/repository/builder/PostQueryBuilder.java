package com.moayo.moayoeats.backend.domain.post.repository.builder;

import com.moayo.moayoeats.backend.domain.post.entity.CategoryEnum;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.locationtech.jts.geom.Point;

public class PostQueryBuilder {

    private StringBuilder jpql;
    @PersistenceContext
    private EntityManager entityManager;
    private TypedQuery<Post> query;
    private PostStatusEnum status;
    private CategoryEnum category;
    private String keyword;
    private String cuisine;
    private Point userLocation;

    public PostQueryBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.jpql = new StringBuilder("SELECT p FROM Post p WHERE 1=1");
    }

    public PostQueryBuilder withStatus(PostStatusEnum status) {
        this.status = status;
        if (status != null) {
            jpql.append(" AND p.postStatus = :status");
        }
        return this;
    }

    public PostQueryBuilder withCategory(CategoryEnum category) {
        this.category = category;
        if (category != null) {
            jpql.append(" AND p.category = :category");
        }
        return this;
    }

    public PostQueryBuilder withKeyword(String keyword) {
        this.keyword = keyword;
        if (keyword != null && !keyword.trim().isEmpty()) {
            jpql.append(" AND p.store LIKE :keyword");
        }
        return this;
    }

    public PostQueryBuilder withCuisine(String cuisine) {
        this.cuisine = cuisine;
        if (cuisine != null && !cuisine.trim().isEmpty()) {
            jpql.append(" AND p.cuisine = :cuisine");
        }
        return this;
    }

    public PostQueryBuilder orderByDistance(User user) {
        this.userLocation = user.getLocation();
        if (userLocation != null) {
            jpql.append(" AND p.location IS NOT NULL ORDER BY distance(p.location, :userLocation) ASC");
            return AndOrderByDeadline();
        }
        return orderByDeadline();
    }

    public PostQueryBuilder AndOrderByDeadline() {
        jpql.append(", p.deadline DESC");
        return this;
    }

    public PostQueryBuilder orderByDeadline() {
        jpql.append(" ORDER BY p.deadline DESC");
        return this;
    }

    public TypedQuery<Post> build() {
        query = entityManager.createQuery(jpql.toString(), Post.class);

        if (status != null) {
            query.setParameter("status", status);
        }
        if (category != null) {
            query.setParameter("category", category);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        if (cuisine != null && !cuisine.trim().isEmpty()) {
            query.setParameter("cuisine", cuisine);
        }
        if (userLocation != null) {
            query.setParameter("userLocation", userLocation);
        }

        return query;
    }
}
