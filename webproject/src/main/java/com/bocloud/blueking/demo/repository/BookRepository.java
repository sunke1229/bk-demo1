/*
 *  Copyright (c) 2017.  Tencent 蓝鲸智云(BlueKing)
 */

package com.bocloud.blueking.demo.repository;

import com.bocloud.blueking.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository //表示是一个DAO
public interface BookRepository extends CrudRepository<Book,Integer>, JpaRepository<Book, Integer>,JpaSpecificationExecutor<Book> {

    Book findBookByName(String name);

    List<Book> findBooksByNameIsLike(String name);

    List<Book> findBooksByIdAndNameIsLike(Integer id, String name);

}
