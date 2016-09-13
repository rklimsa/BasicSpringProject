/**
 *
 */
package test.spring.web.task;

import java.util.ArrayList;
import java.util.Objects;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import test.spring.web.model.Task;
import test.spring.web.session.SessionBuilder;

/**
 * @author Rico Klimsa
 *
 */
@RestController
public class TaskController {

	private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private InternalTaskCache internalTaskCache;

	@Autowired
	private SessionBuilder sessionBuilder;

	@RequestMapping(method = RequestMethod.POST, path = "/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void save(@RequestBody final Task task) {
		this.internalTaskCache.taskCache.put(task.getTaskId(), task);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/save_permanently", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void savePermanently(@RequestBody final Task task) {
		final Session session = this.sessionBuilder.openSession();
		final Transaction transaction = session.beginTransaction();
		transaction.begin();
		try {
			session.persist(task);
			transaction.commit();
		} catch (final Exception e) {
			LOG.error("Error occured during transaction.", e);
			transaction.rollback();
			if (!transaction.wasRolledBack()) {
				LOG.error("Rollback failed.");
			}
		}
		session.close();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/find_permanently/{id}")
	public Task findPermanently(@PathVariable(value = "id") final int id) {
		final Session session = this.sessionBuilder.openSession();
		Task task = null;
		try {
			task = (Task) session.get(Task.class, id);
		} catch (final Exception e) {
			LOG.error("Error occured during transaction.", e);
		}
		if (Objects.isNull(task)) {
			LOG.warn("Task is null.");
		}
		session.close();
		return task;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
	public Task find(@PathVariable(value = "id") final int id) {
		return this.internalTaskCache.taskCache.get(id);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findAll")
	public Task[] findAll() {
		return new ArrayList<>(this.internalTaskCache.taskCache.values())
				.toArray(new Task[this.internalTaskCache.taskCache.size()]);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
	public void delete(@PathVariable(value = "id") final int id) {
		this.internalTaskCache.taskCache.remove(id);
	}
}
