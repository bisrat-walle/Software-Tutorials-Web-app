package com.groupproject.softwaretu.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;

    @NotNull(message = "Project title cannot be null")
    @Column(length = 30, nullable = false)
    @Size(min = 5, message  = "Title must be at least 5 characters long ")
    private String title;

    @NotNull(message = "Project content cannot be null")
    @Column(length = 1000, nullable = false)
    @Size(min = 10, message  = "Problem statement must be at least 100 characters long ")
    private String problemStatement;
}
