package com.blogsystem.cronjob.base;

import com.blogsystem.cronjob.dao.CronDao;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.blogsystem.cronjob.base.ScheduledTask.TASK_TEST_TASK_1;
import static com.blogsystem.cronjob.base.ScheduledTask.TASK_TEST_TASK_2;

@Getter
@Setter
@Component
@Log4j2
public class ServiceConfig extends BaseServiceConfig {
  private static final String TEST_TASK_1 = "test.task.1";
  private static final String TEST_TASK_2 = "test.task.2";
  private String testTask1;
  private String testTask2;

  public ServiceConfig(CronDao cronDao) {
    super(cronDao);
  }

  @Override
  protected void parse() {
    log.info("Parsing {} service configs", configMap.size());
    log.info("=".repeat(10));
    for (var entry : this.configMap.entrySet()) {
      log.info("{}: {}", entry.getKey(), entry.getValue());
    }
    log.info("=".repeat(10));
    this.setTestTask1(StringUtils.isNotBlank(configMap.get(TEST_TASK_1))
      ? configMap.get(TEST_TASK_1)
      : "* * * ? * *");
    this.setTestTask2(StringUtils.isNotBlank(configMap.get(TEST_TASK_2))
      ? configMap.get(TEST_TASK_2)
      : "* * * ? * *");
  }

  public String getCronExpression(int task) {
    switch (task) {
      case TASK_TEST_TASK_1:
        return this.testTask1;
      case TASK_TEST_TASK_2:
        return this.testTask2;
      default:
        throw new IllegalArgumentException("Unknown scheduled task " + task);
    }
  }
}
