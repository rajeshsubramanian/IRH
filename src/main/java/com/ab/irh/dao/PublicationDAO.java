package com.ab.irh.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ab.irh.model.Publication;

@Repository
@Transactional
public class PublicationDAO {

	@Autowired
	private SessionFactory _sessionFactory;

	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}

	public Publication save(Publication publication) {
		getSession().save(publication);
		return publication;
	}

	@SuppressWarnings("unchecked")
	public List<Publication> getAll() {
		return getSession().createCriteria(Publication.class).list();
	}
}
