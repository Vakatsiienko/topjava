package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * Created by Iaroslav on 3/7/2017.
 */
public class TimeRule implements TestRule {
    private static final Logger LOG = LoggerFactory.getLogger(TimeRule.class);
    private StopWatch stopWatch = new StopWatch();
    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                stopWatch.start(description.getMethodName());
                base.evaluate();
                stopWatch.stop();
                LOG.debug("{} class, {} test, evaluation time millis - {}", description.getClassName(), description.getMethodName(), stopWatch.getLastTaskTimeMillis());
            }
        };
    }
}
