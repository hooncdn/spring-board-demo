package com.example.springboard.repository;

import com.example.springboard.domain.Post;
import com.example.springboard.domain.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PostRepositoryImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {

    public PostRepositoryImpl() {
        super(Post.class);
    }

    @Override
    public Page<Post> findAllByTitle(String title, Pageable pageable) {

        JPQLQuery<Post> query = findAllByTitleQuery(title, pageable);

        QueryResults<Post> queryResults = query.fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    private JPQLQuery<Post> findAllByTitleQuery(String title, Pageable pageable) {
        QPost post = QPost.post;

        JPQLQuery<Post> query = from(post);

        BooleanBuilder bb = new BooleanBuilder();
        bb.and(post.title.contains(title)).or(post.author.contains(title));

        if (pageable != null) {
            query.limit(pageable.getPageSize());
            query.offset(pageable.getOffset());
        }

        OrderSpecifier<Integer> orderId = post.views.desc();

        return query.distinct().where(bb).orderBy(orderId);

    }
}
