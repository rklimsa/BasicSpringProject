package test.spring.web.task;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

import test.spring.web.model.Task;

@Component
public class InternalTaskCache {

	public final ConcurrentMap<Integer, Task> taskCache = new ConcurrentHashMap<>();

}
