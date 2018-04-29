package neu.edu.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import neu.edu.entity.Person;

@Repository
public class PersonDaoImplementation implements PersonDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional
	public Person createPerson(Person person) {

		Session session = sessionFactory.getCurrentSession();
		session.persist(person);
		return person;
	}

	@Override
	@Transactional
	public void deletePersonAccount(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(person);
	}

	@Override
	public List<Person> getPerson(int personId) {

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Person where personId=:id");
		query.setInteger("id", personId);

		List<Person> person = query.list();
		// System.out.println("person in dao is : "+person);
		try {
			session.close();
		} catch (Exception ex) {
			return null;
		}
		return person;
	}

	@Override
	public String getEmailId(int personId) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Person where personId=:id");
		query.setInteger("id", personId);

		List<Person> person = query.list();
		if(person.size()>0){
			return person.get(0).getEmailid();
		}
		try {
			session.close();
		} catch (Exception ex) {
			return null;
		}
		return null;
	}

}
