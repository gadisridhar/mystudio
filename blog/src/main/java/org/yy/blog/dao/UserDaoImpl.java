package org.yy.blog.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.transaction.annotation.Transactional;
import org.yy.blog.model.User;

public class UserDaoImpl implements UserDao {

	private EntityManagerFactory emFactory;

	public void setEntityManagerFactory(EntityManagerFactory emFactory) {
		this.emFactory = emFactory;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return emFactory;
	}

	/* (non-Javadoc)
	 * @see org.yy.blog.dao.UserDao#get(java.lang.Long)
	 */
	public User get(Long id) {

		return emFactory.createEntityManager().getReference(User.class, id);
	}

	/* (non-Javadoc)
	 * @see org.yy.blog.dao.UserDao#findById(java.lang.Long)
	 */
	public User findById(Long id) {

		return  emFactory.createEntityManager().find(User.class, id);
	}

	/* (non-Javadoc)
	 * @see org.yy.blog.dao.UserDao#merge(org.yy.blog.model.User)
	 */
	public User merge(User user) {
		return  emFactory.createEntityManager().merge(user);
	}

	/* (non-Javadoc)
	 * @see org.yy.blog.dao.UserDao#create(org.yy.blog.model.User)
	 */
	@Transactional
	public void create(User user) {
		 emFactory.createEntityManager().persist(user);
	}

	/* (non-Javadoc)
	 * @see org.yy.blog.dao.UserDao#delete(org.yy.blog.model.User)
	 */
	@Transactional
	public void delete(User user) {
		emFactory.createEntityManager().remove(user);
	}
}
