# Autolab(website for booking experimental course)

>  A web application of booking experimental course for SJTU students

The autolab project aims to build a web application of booking experimental course for SJTU students. Student logins in the system with Jaccount (the identity of SJTU student) to book experimental course. This web application includes following functions:

> 1. Student can book experimental course, it supports book and cancel book. Each course has max limitation student's number.

> 2. Student can query the grade for each cource they booked.

> 3. Teacher can create/edit/delete/query a cource (including setting max student number, time and classroom for course......)

> 4. Teacher can publish students' grade and import AttendanceSheet into website.

## core technology

We mainly use Spring MVC framework to construct website back end. We integrate Spring Data JPA to persistent data into MYSQL, use Spring security to realize oauth2.0. The front end is mainly use HTML + AngularJS.
