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
        new Book(title: BOOK_TITLE, authors: [author], number: BOOK_INSTANCE_SIZE).save()
    }

    void testReleaseEmpty() {
        controller.release()
        assertStatus(400)
    }

    void testRelease() {
        def book = Book.findByTitle(BOOK_TITLE)
        def reader = Reader.findAll().get(0)
        for(int i = 0; i < BOOK_INSTANCE_SIZE; i++){
            takeBook(BOOK_TITLE, reader.id)
        }

        for(int i = 0; i < BOOK_INSTANCE_SIZE; i++){
            releaseBook(BOOK_TITLE, reader.id)
            assertStatus(200)
            assertEquals(BOOK_INSTANCE_SIZE - i - 1, book.readers.size())
            assertEquals(BOOK_INSTANCE_SIZE - i - 1, reader.books.size())
        }
        releaseBook(BOOK_TITLE, reader.id)
        assertStatus(400)
    }

    void testTakeEmpty(){
        controller.take()
        assertStatus(400)
    }

    void testTake(){
        def reader = Reader.findAll().get(0)
        def book = Book.findByTitle(BOOK_TITLE)
        for(int i = 0; i < BOOK_INSTANCE_SIZE; i++){
            takeBook(BOOK_TITLE, reader.id)
            assertStatus(200)
            assertEquals(i + 1, reader.books.size())
            assertEquals(i + 1, book.readers.size())
        }
        takeBook(BOOK_TITLE, reader.id)
        assertStatus(400)
    }

    private void takeBook(String title, Long readerId){
        controller.params.putAll([title : title, readerId: readerId])
        controller.take()
        controller.params.clear()
    }

    private void releaseBook(String title, Long readerId){
        controller.params.putAll([title : title, readerId: readerId])
        controller.release()
        controller.params.clear()
    }

    private void assertStatus(int status){
        assertEquals(status , controller.response.status)
    }
}
