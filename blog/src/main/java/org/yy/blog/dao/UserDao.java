package org.yy.blog.dao;

import org.yy.blog.model.User;

public interface UserDao {

	public abstract User get(Long id);

	public abstract User findById(Long id);

	public abstract User merge(User user);

	public abstract void create(User user);

	public abstract void delete(User user);

}