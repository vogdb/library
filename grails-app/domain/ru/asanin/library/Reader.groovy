package ru.asanin.library

class Reader {

    String fio
    List books
    static hasMany = [books: Book]
    static hasOne = [document: DocumentInstance]
    static constraints = {
    }
}
