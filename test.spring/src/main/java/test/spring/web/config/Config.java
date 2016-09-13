package test.spring.web.config;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import test.spring.web.session.SessionBuilder;
import test.spring.web.task.InternalTaskCache;

@ComponentScan
@Configuration
public class Config {

	private InternalTaskCache internalTaskCache;
	private SessionBuilder sessionBuilder;

	@Bean
	public InternalTaskCache internalTaskCache() {
		if (Objects.isNull(this.internalTaskCache)) {
			this.internalTaskCache = new InternalTaskCache();
		}
		return this.internalTaskCache;
	}

	@Bean
	public SessionBuilder sessionBuilder() {
		if (Objects.isNull(this.sessionBuilder)) {
			this.sessionBuilder = new SessionBuilder();
		}
		return this.sessionBuilder;
	}

}
