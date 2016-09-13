package test.spring.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Task")
public class Task {

	private int taskId;

	private String name;

	private String due;

	private String category;

	private boolean complete;

	@Id
	public int getTaskId() {
		return this.taskId;
	}

	public void setTaskId(final int taskId) {
		this.taskId = taskId;
	}

	@Column
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column
	public String getDue() {
		return this.due;
	}

	public void setDue(final String due) {
		this.due = due;
	}

	@Column
	public String getCategory() {
		return this.category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	@Column
	public boolean isComplete() {
		return this.complete;
	}

	public void setComplete(final boolean complete) {
		this.complete = complete;
	}

}
