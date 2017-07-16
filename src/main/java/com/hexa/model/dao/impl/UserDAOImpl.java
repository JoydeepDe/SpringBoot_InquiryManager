package com.hexa.model.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hexa.model.User;
import com.hexa.model.dao.UserDAO;

public class UserDAOImpl implements UserDAO {
	private SessionFactory sessionFactory;

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> list() {

		// List<User> listUser = (List<User>)
		// sessionFactory.getCurrentSession().createCriteria(User.class).list();
		// Query q = sessionFactory.getCurrentSession().createQuery("from
		// USERS");
		List<User> listUser = (List<User>) sessionFactory.getCurrentSession().createQuery("from User").list();
		
		return listUser;
	}

}
