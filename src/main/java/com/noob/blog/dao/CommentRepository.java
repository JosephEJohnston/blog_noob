package com.noob.blog.dao;

import com.noob.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 父类为空，是最顶级的评论
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
