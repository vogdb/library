package ru.asanin.library

class BookController {

    def index = { redirect(action: "take")}

    def take = {

        String title = params.title
        String readerId = params.readerId
        if (title && readerId) {
            boolean success = false
            for (bookInst in Book.findByTitle(title).instances) {
                if (bookInst.reader == null) {
                    bookInst.reader = Reader.get(Long.valueOf(readerId))
                    bookInst.save()
                    success = true
                    render "OK"
                    break
                }
            }
            if (!success)
                error()
        } else {
            error()
        }
    }

    def release = {
        String code = params.code
        if (code) {
            def bookInst = BookInstance.findByCode(code)
            def reader = bookInst.reader
            if (reader) {
                reader.removeFromBooks(bookInst)
                reader.save()
                render "OK"
            }else{
                error()
            }
        } else {
            error()
        }

    }

    private void error() {
        render "Error"
        response.status = 400
    }
}
