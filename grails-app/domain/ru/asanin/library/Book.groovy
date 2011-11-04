package ru.asanin.library

class Book {

    String title
    static hasMany = [authors: Author, rubrics: Rubric, instances: BookInstance]
    static belongsTo = [Author, Rubric]
    static constraints = {
    }
}
