package ru.asanin.library

class BookInstance {

    String code
    static belongsTo = [book: Book, reader: Reader]
    static constraints = {
    }
}
