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

    
    @Column(length = 50)
    private String title;

    
    @Column(length = 1000)
    private String problemStatement;
}
