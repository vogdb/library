package ru.asanin.library

class Reader {

    String fio
    static hasMany = [books: Book]
    static hasOne = [document: DocumentInstance]
    static constraints = {
    }
}
