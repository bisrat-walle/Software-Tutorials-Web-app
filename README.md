# Software Tutorials - Group Project
<pre>
Our project is a web application that allows users to 
      1. look for tutorials
      2. create tutorials and projects
      2. enroll for their favorite tutorial
      3. submit the project attached to a specific tutorial track
</pre>


## User Types

<pre>
In this project we have three types of users
	1. Clients
	2. Instructors
	3. Admins
</pre>


## Business features in addition to authentication and authorization

<pre>
    1. Tutorial Enrollement for Clients - CRUD operations (Enroll(POST), UnEnroll(DELETE), ViewTutorial(GET)), REENROLL(PUT)
    2. Tutorial Creation for Instructors - CRUD operations (Create(POST), ViewTutorial(GET))
    3. Project Submission - CRUD operations (SubmitProject(POST),  ViewProject(GET))
    4. Project Creation for Instructors -  CRUD operations (CreateProject(POST), ViewProject(GET), 
                        UpdateProject (PUT), DeleteProject(DELETE))
</pre>
      
## Group Members

<pre>
           Name                      ID                 Section
      1. Abel Yohannes           UGR/8254/12              2
      2. Bisrat Walle            UGR/4425/12              3
      3. Samuel Abatneh          UGR/7229/12              1
      4. Sefineh Tesfa           UGR/2844/12              1
      5. Yeabsira Tekuamwork     UGR/9192/12              2

</pre>
 
## Technologies Used

<pre>
	1. Spring Boot - Java
	2. Thymleaf - Templating engine
	3. Bootstrap 5
	4. HTML, CSS, and Javascript
	5. MySQL database as a backend
</pre>


## Remark


<pre>
Since Admins cannot signup for the system,

we have used CommandLine runner to create an admin with 

	1. Username - admin
	2. Password - admin
	
So you can use this user to explore about the admin

</pre>
