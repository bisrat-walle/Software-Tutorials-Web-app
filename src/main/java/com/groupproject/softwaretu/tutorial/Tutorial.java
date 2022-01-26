package com.groupproject.softwaretu.tutorial;

import com.groupproject.softwaretu.project.Project;
import com.groupproject.softwaretu.enrollement.Enrollement;
import com.groupproject.softwaretu.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Tutorial implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tutorialId;

    @NotNull(message="Tutorial title cannot be empty")
    @Column(length = 1000, nullable = false)
    @Size(min = 5, message  = "Title must be at least 5 characters long ")
    private String title;

    @NotNull(message="Tutorial content cannot be empty")
    @Column(length = 10000, nullable = false)
    @Size(min = 500, message  = "Tutorial content must be at least 500 characters long ")
    private String content;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "tutorial", cascade=CascadeType.ALL)
    Set<Enrollement> enrollements;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "projectId")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "instructor", nullable = true, referencedColumnName="id")
    User instructor;


    private LocalDateTime modifiedAt;

}
