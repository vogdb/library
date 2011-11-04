package ru.asanin.library

class Rubric {

    String description
    static hasMany = [books: Book]
    static constraints = {
    }
}
