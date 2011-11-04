package ru.asanin.library

class DocumentInstance {

    String details
    //Document type
    static belongsTo = [reader: Reader, document: Document]
    static constraints = {
    }
}
