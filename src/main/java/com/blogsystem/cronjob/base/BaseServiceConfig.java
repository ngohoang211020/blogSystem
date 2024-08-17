package com.blogsystem.cronjob.base;

import com.blogsystem.cronjob.dao.CronDao;

import java.util.Map;

public abstract class BaseServiceConfig {
  protected final Map<String, String> configMap;

  protected BaseServiceConfig(CronDao cronDao) {
    this.configMap = cronDao.load();
    this.parse();
  }

  /**
   * Parse configMap into service-specific DTO
   */
  protected abstract void parse();
}
