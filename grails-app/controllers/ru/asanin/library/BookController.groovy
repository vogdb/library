package ru.asanin.library

class BookController {

    def index = { redirect(action: "take")}

    def take = {

        String title = params.title
        String readerId = params.readerId
        if (title && readerId) {
            def book = Book.findByTitle(title)
            if (book.readers?.size() < book.number) {
                Reader.get(Long.valueOf(readerId)).addToBooks(book).save()
            } else {
                error("no books")
            }
        } else {
            error("no params")
        }
    }

    def release = {

        String title = params.title
        String readerId = params.readerId
        if (title && readerId) {
            Reader reader = Reader.get(Long.valueOf(readerId))
            Book book = Book.findByTitle(title)
            if (reader.books?.contains(book)) {
                reader.removeFromBooks(book)
                reader.save()
                render "OK"
            } else {
                error("reader doesn't have such book")
            }
        } else {
            error("no params")
        }
    }

    private void error(String msg) {
        render msg
        response.status = 400
    }
}
