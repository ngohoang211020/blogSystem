package com.blogsystem.entity.task;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "scheduled_task", schema = "scheduled")
public class ScheduledTaskEntity {
    @Id
    private String name;

    private int type;

    private OffsetDateTime startAt;

    private OffsetDateTime endedAt;

    private boolean completed;

    private boolean success;

    private String stackTrace;
}
