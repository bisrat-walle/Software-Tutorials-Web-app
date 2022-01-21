package com.groupproject.softwaretu;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;

@Entity
@Data
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tutorialId;

    @NotNull
    @Column(length = 50, nullable = false)
    @Size(min = 5, message  = "title must be at least 5 characters long ")
    private String title;

    @NotNull
    @Column(length = 10000, nullable = false)
    @Size(min = 5, message  = "tutorial content must be at least 500 characters long ")
    private String content;

    @OneToMany(mappedBy = "tutorial")
    Set<Enrollement> enrollements;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "projectId")
    private Project project;
}
