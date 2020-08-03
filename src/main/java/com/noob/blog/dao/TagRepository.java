package com.noob.blog.dao;

import com.noob.blog.po.Tag;
import com.noob.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

    @Query(" select t from Tag t ")
    List<Tag> findTop(Pageable pageable);
}
