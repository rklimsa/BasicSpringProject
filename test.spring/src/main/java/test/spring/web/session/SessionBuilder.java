package test.spring.web.session;

import java.util.Objects;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class SessionBuilder {

	private SessionFactory sessionFactory;

	public SessionBuilder() {
		createSessionFactory();
	}

	private void createSessionFactory() {
		if (Objects.isNull(this.sessionFactory)) {
			this.sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		}
	}

	public Session openSession() {
		if (Objects.isNull(this.sessionFactory)) {
			throw new IllegalStateException("SessionFactory must not be null.");
		}
		return this.sessionFactory.openSession();
	}

}
