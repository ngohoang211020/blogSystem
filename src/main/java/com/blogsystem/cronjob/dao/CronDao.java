package com.blogsystem.cronjob.dao;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CronDao {
  private final EntityManager entityManager;
  private static final String GET_SCHEDULES = "SELECT key,value FROM BLOGSYSTEM.SCHEDULER_CONFIG WHERE status > 0";

    public CronDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Map<String, String> load() {
    var configMap = new HashMap<String, String>();
    var result = entityManager.createNativeQuery(GET_SCHEDULES).getResultList();
    for (var config : result) {
      var configPair = (Object[]) config;
      configMap.put((String) configPair[0], (String) configPair[1]);
    }
    return configMap;
  }
}
