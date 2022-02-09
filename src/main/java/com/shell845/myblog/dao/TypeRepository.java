package com.shell845.myblog.dao;

import com.shell845.myblog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);

    // enquiry types by passing in number of size
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
