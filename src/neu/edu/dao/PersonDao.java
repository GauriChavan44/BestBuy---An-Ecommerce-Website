package neu.edu.dao;

import java.util.List;

import neu.edu.entity.Person;

public interface PersonDao {

	public Person createPerson(Person person);

	public List<Person> getPerson(int personId);

	public void deletePersonAccount(Person person);

	public String getEmailId(int personId);
}
