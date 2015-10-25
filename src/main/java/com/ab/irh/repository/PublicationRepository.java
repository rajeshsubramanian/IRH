package com.ab.irh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ab.irh.model.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

}
