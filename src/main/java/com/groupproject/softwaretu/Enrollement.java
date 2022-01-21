package com.groupproject.softwaretu;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.validation.constraints.Size;

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
	
	
	
    @Column(length = 5, nullable = true)
    @Size(min = 5, message  = "problem statement must be at least 500 characters long ")
    private String githubLink;  
	
	
	
}
