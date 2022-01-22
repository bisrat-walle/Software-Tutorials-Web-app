package com.groupproject.softwaretu.project;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @NotNull
    @Column(length = 30, nullable = false)
    @Size(max = 30, message  = "title must be at least 5 characters long ")
    private String title;

    @NotNull
    @Column(length = 1000, nullable = false)
    @Size(min = 5, message  = "problem statement must be at least 500 characters long ")
    private String problemStatement;

	
}
