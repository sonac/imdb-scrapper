# IMDB Scrapper

## Description

IMDB scrapper is a small library implemented on top of https://github.com/ruippeixotog/scala-scraper that abstracts parsing of 
https://www.imdb.com. It implements interfaces for names and movies
and info regarding them.

## Usage

Library has 3 main interfaces to interact through. Its `Movie`,
`Person` and `MovieSearch`. For example to run search use:
```
val godfather = MovieSearch("the", "godfather").getAllMovies.head
/* 
Movie(Der Pate (1972) aka "The Godfather", https://www.imdb.com/title/tt0068646/, https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkE
yXkFqcGdeQXVyNzkwMjQ5NzM@.jpg)
*/
godfather.cast.take(n)
/* 
List(Person(Marlon Brando, https://www.imdb.com/name/nm0000008/, https://m.media-amazon.com/images/M/MV5BMTg3MDYyMDE5OF5BMl5BanBnXkFtZTcwNjgyNTEzNA@@.jpg), Person(Al Pacino,
 https://www.imdb.com/name/nm0000199/, https://m.media-amazon.com/images/M/MV5BMTQzMzg1ODAyNl5BMl5BanBnXkFtZTYwMjAxODQ1.jpg), Person(James Caan, https://www.imdb.com/name/nm
0001001/, https://m.media-amazon.com/images/M/MV5BMTI5NjkyNDQ3NV5BMl5BanBnXkFtZTcwNjY5NTQ0Mw@@.jpg), Person(Richard S. Castellano, https://www.imdb.com/name/nm0144710/, http
s://m.media-amazon.com/images/M/MV5BMjI2MzA3MjQ5N15BMl5BanBnXkFtZTcwMzY5NDYwOA@@.jpg), Person(Robert Duvall, https://www.imdb.com/name/nm0000380/, https://m.media-amazon.com
/images/M/MV5BMjk1MjA2Mjc2MF5BMl5BanBnXkFtZTcwOTE4MTUwMg@@.jpg))
*/
```

