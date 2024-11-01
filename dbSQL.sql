/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  lorenababic
 * Created: Sep 3, 2024
 */

CREATE DATABASE FILM 
GO
USE FILM
GO

CREATE TABLE Film 
(
    IDFilm int primary key identity,
    Title nvarchar(100),
    Duration int,
    PublishedDate nvarchar(50),
    Description nvarchar(800),
    PicturePath nvarchar(1000)
)
GO

CREATE TABLE Person 
(
    IDPerson int primary key identity,
    Name nvarchar(50),
    Role nvarchar(10)
)

CREATE TABLE UserFilm
(
    IDUser int PRIMARY KEY IDENTITY,
    Username NVARCHAR(50),
    Password NVARCHAR(30),
    Role nvarchar(10)
) 

INSERT INTO UserFilm values ('admin', 'adminpass', 'ADMIN');
INSERT INTO UserFilm values ('korisnik', 'korisnikpass', 'USER');
GO

select * from UserFilm
delete from UserFilm where IDUser = 2
GO

CREATE TABLE FilmPerson
(
    IDFilmPerson int primary key identity,
    FilmID int,
    PersonID int, 
    RoleInFilm nvarchar(50),
    FOREIGN KEY (FilmID) REFERENCES Film(IDFilm),
    FOREIGN KEY (PersonID) REFERENCES Person(IDPerson)
);
GO

CREATE PROCEDURE createPerson
    @Name NVARCHAR(50),
    @Role NVARCHAR(10),
    @IDPerson int OUTPUT
as 
BEGIN
   insert into Person VALUES (@Name, @Role)
   set @IDPerson = SCOPE_IDENTITY()
END    
GO

CREATE PROCEDURE updatePerson
    @Name NVARCHAR(50),
    @Role NVARCHAR(10),
    @IDPerson INT
AS
BEGIN
    UPDATE Person 
    SET Name = @Name, Role = @Role
    WHERE IDPerson = @IDPerson
END
GO

CREATE PROCEDURE deletePerson
    @IDPerson INT
AS
BEGIN
    DELETE FROM Person WHERE IDPerson = @IDPerson
END
GO

CREATE PROCEDURE selectPerson
    @IDPerson INT
as 
BEGIN
   SELECT * FROM Person where IDPerson = @IDPerson
END
GO

CREATE PROCEDURE selectPeople
AS
BEGIN
  SELECT * from Person
END
GO

CREATE PROCEDURE createFilm 
    @Title nvarchar(100),
    @Duration int,
    @PublishedDate nvarchar(50),
    @Description nvarchar(800),
    @PicturePath nvarchar(1000),
    @IDFilm int OUTPUT
AS
BEGIN
    INSERT into Film values (@Title, @Duration, @PublishedDate, @Description, @PicturePath)
    set @IDFilm = SCOPE_IDENTITY()
END
GO

CREATE PROCEDURE updateFilm
    @Title nvarchar(100),
    @Duration int,
    @PublishedDate nvarchar(50),
    @Description nvarchar(800),
    @PicturePath nvarchar(1000),
    @IDFilm int OUTPUT
AS
BEGIN
    update Film SET 
        Title = @Title,
        Duration = @Duration,
        PublishedDate = @PublishedDate,
        Description = @Description,
        PicturePath = @PicturePath
    where IDFilm = @IDFIlm
END
go

CREATE PROCEDURE deleteFilm
    @IDFilm int
AS
BEGIN
    Delete from Film where IDFilm = @IDFilm
END
GO

CREATE PROCEDURE selectFilm 
    @IDFilm INT
AS
BEGIN
    SELECT * FROM Film WHERE IDFilm = @IDFilm
END
GO


CREATE PROCEDURE selectFilms
AS
BEGIN
    SELECT * FROM Film
END
GO

CREATE PROCEDURE createUser
    @Username nvarchar(50),
    @Password nvarchar(30),
    @Role nvarchar(10),
    @IDUser int OUTPUT
AS
BEGIN
    INSERT INTO UserFilm values(@Username, @Password, @Role)
    SET @IDUser = SCOPE_IDENTITY()
END
GO

CREATE PROCEDURE selectUser
    @Username NVARCHAR(50),
    @Password NVARCHAR(30)
AS
BEGIN
    SELECT IDUser , Role FROM UserFilm 
    WHERE Username = @Username and Password = @Password
END
go

CREATE PROCEDURE findPerson 
    @Name nvarchar(50),
    @Role nvarchar(10)
AS
BEGIN
    SELECT p.IDPerson from Person as p 
    WHERE Name = @Name and Role = @Role
END
go


create PROCEDURE findFilm
    @Title nvarchar(100)
AS
BEGIN
    SELECT f.IDFilm from Film as f
    WHERE Title = @Title
END
go

CREATE PROCEDURE selectActors
as
BEGIN
    SELECT * from Person where Role = 'ACTOR'
END
go

CREATE PROCEDURE selectDirectors
AS
BEGIN 
    SELECT * from Person where Role = 'DIRECTOR'
END
go

CREATE PROCEDURE createPersonInFilm
	@PersonID int,
	@FilmID int,
	@RoleInFilm nvarchar(10),
	@IDFilmPerson int output
AS 
BEGIN 
	INSERT INTO FilmPerson VALUES(@PersonID, @FilmID, @RoleInFilm)
	SET @IDFilmPerson = SCOPE_IDENTITY()
END
GO

CREATE PROCEDURE selectPersonInFilm
	@FilmID INT
AS 
BEGIN
	SELECT
		p.IDPerson, p.Name, p.Role
	FROM Person as p
	JOIN FilmPerson as fp on p.IDPerson = fp.PersonID
	WHERE fp.FilmID = @FilmID
END
GO

CREATE PROCEDURE deletePersonInFilm
	@FilmID INT 
AS 
BEGIN 
	DELETE FROM FilmPerson	
    WHERE FilmId = @FilmID
END
GO

CREATE PROCEDURE clearAllData
as
BEGIN
    DELETE FROM FilmPerson
    DELETE FROM Person 
    DELETE FROM Film

    drop table FilmPerson
    drop table Person
    drop table Film
    
    CREATE TABLE Film 
    (
        IDFilm int primary key identity,
        Title nvarchar(100),
        Duration int,
        PublishedDate nvarchar(50),
        Description nvarchar(800),
        PicturePath nvarchar(500)
    )

    CREATE TABLE Person 
    (
        IDPerson int primary key identity,
        Name nvarchar(50),
        Role nvarchar(10)
    )

    CREATE TABLE FilmPerson
    (
        IDFilmPerson int primary key identity,
        FilmID int,
        PersonID int, 
        RoleFilm nvarchar(10),
        FOREIGN KEY (FilmID) REFERENCES Film(IDFilm),
        FOREIGN KEY (PersonID) REFERENCES Person(IDPerson)
    );
END
go


CREATE PROCEDURE maxFilmId 
as
BEGIN
    SELECT MAX(IDFilm) from Film;
END
GO

CREATE PROCEDURE maxPersonId
AS
BEGIN
    SELECT MAX(IDPerson) from Person;
END
go