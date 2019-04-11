# MovieTrack
A Java CRUD application that allows users to send commands from a client to a server running a MySQL database. 
Users can add, update or remove movies in a database as well as keep track of the movies they have watched themselves.
They can even ask the server to reccomend them a movie based off their watched history. 

## Getting Started

To get started you must open the java project or files and compile them. 
You then run the server and the client located in the business logic.
You can then enter one of the many commands in the client input to get a response from the server.

### Commands

```
GetAllMovies -> Returns All movies in the database
```

```
GetMovieById (ID) -> Returns the movie object associated with that movie id from the database.
```

```
GetMovieByTitle (Title) -> Returns the movie object associated with that title from the database.
```

```
GetMoviesByDirector (Director) -> Returns a list of movie objects associated with that director from the database.
```

```
addMovie, (title,genre,director,runtime,plot,location,poster,rating,format,year,starring,copies,barcode,user_rating) -> Adds a movie object to the database
```

```
deleteMovie (ID) -> Deletes the movie object associated with that movie from the database.
```

```
updateMovie, (title,genre,director, id(of movie being updated) -> updates the title,genre and director of the movie associated with that id in the database.
```

```
watchMovie (username,id) -> This will add the username and id of the movie watched to the watched table in the dtabase which allows the users
```

```
recommendMovie (username) -> This will return a list of Movies directed by the users most watched director that they have not seen yet.
```

## Built With

* Java 8
* MySQL
* JSON

## Versioning

The latest version of this can be seen here - https://github.com/EoinReid/MovieTrack
