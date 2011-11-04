package ru.asanin.library

class Document {

    String description
    static hasMany = [instances: DocumentInstance]
    static constraints = {
    }
}
