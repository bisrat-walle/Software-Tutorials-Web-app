package com.groupproject.softwaretu.enrollement;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import com.groupproject.softwaretu.tutorial.Tutorial;
import com.groupproject.softwaretu.security.User;
import lombok.Data;

@Entity
@Data
public class Enrollement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long enrollementId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "tutorial_id")
    private Tutorial tutorial;

    private LocalDateTime enrolledAt;
	
	
	
    @Column(length = 35, nullable = true)
    @Size(min = 5, message  = "problem statement must be at least 5 characters long ")
    private String githubLink;
}
