package ru.asanin.library

class Author {

    String fio
    static hasMany = [books: Book]
    static constraints = {
    }
}
