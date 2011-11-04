package ru.asanin.library

class Reader {

    String fio
    static hasMany = [books: BookInstance]
    static hasOne = [document: DocumentInstance]
    static constraints = {
    }
}
