package com.ab.irh.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ab.irh.model.Author;

@Repository
@Transactional
public class AuthorDAO {

	@Autowired
	private SessionFactory _sessionFactory;

	private Session getSession() {
		return _sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<Author> getAll() {
		return getSession().createCriteria(Author.class).list();
	}
}
