package ru.asanin.library

class BookControllerTests extends GroovyTestCase {

    static final String BOOK_TITLE = "Prostokvashino"
    static final short BOOK_INSTANCE_SIZE = 2

    def controller = new BookController()

    @Override
    protected void setUp() {
        super.setUp()
        //make sure that we always have some test data. We can always use Bootstrap instead.
        def doc = new Document(description: "mustache").save()
        new Reader(fio: "uncle Fedor", document: new DocumentInstance(document: doc, details: "some")).save()
        def author = new Author(fio: "Eduard Uspenskiy").save()
        def book = new Book(title: BOOK_TITLE, authors: [author])
        for(int i = 0; i < BOOK_INSTANCE_SIZE; i++){
            book.addToInstances(new BookInstance(code: i.toString()))
        }
        book.save()
    }

    void testReleaseEmpty() {
        def origBooks = BookInstance.findAllByReaderIsNull()
        controller.release()
        assertEquals(400 , controller.response.status)
        assertEquals(origBooks , BookInstance.findAllByReaderIsNull())
    }

    void testRelease() {
        for(int i = 0; i < BOOK_INSTANCE_SIZE; i++){
            takeBook(BOOK_TITLE, Reader.findAll().get(0).id)
        }
        def bookInstanceList = BookInstance.list()
        for(int i = 0; i < BOOK_INSTANCE_SIZE; i++){
            releaseBook(bookInstanceList.get(i).code)
            assertStatus(200)
            assertEquals(i + 1, BookInstance.findAllByReaderIsNull().size())
        }
        releaseBook(bookInstanceList.get(0).code)
        assertStatus(400)
        assertEquals(BOOK_INSTANCE_SIZE, BookInstance.findAllByReaderIsNull().size())
    }

    void testTakeEmpty(){
        def origBooks = BookInstance.findAllByReaderIsNull()
        controller.take()
        assertStatus(400)
        assertEquals(origBooks , BookInstance.findAllByReaderIsNull())
    }

    void testTake(){
        Long readerId = Reader.findAll().get(0).id
        for(int i = 0; i < BOOK_INSTANCE_SIZE; i++){
            takeBook(BOOK_TITLE, readerId)
            assertStatus(200)
            assertEquals(BOOK_INSTANCE_SIZE - i - 1, BookInstance.findAllByReaderIsNull().size())
        }
        takeBook(BOOK_TITLE, readerId)
        assertStatus(400)
        assertEquals(0, BookInstance.findAllByReaderIsNull().size())
    }

    private void takeBook(String title, Long readerId){
        controller.params.putAll([title : title, readerId: readerId])
        controller.take()
        controller.params.clear()
    }

    private void releaseBook(String code){
        controller.params.put("code", code)
        controller.release()
        controller.params.clear()
    }

    private void assertStatus(int status){
        assertEquals(status , controller.response.status)
    }
}
