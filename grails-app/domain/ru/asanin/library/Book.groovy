package ru.asanin.library

class Book {

    String title
    //number of books in library
    int number
    static hasMany = [authors: Author, rubrics: Rubric, readers: Reader]
    static belongsTo = [Author, Rubric]
    static constraints = {
    }
}
