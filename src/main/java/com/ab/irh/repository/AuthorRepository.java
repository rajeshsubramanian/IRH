package com.ab.irh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ab.irh.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
