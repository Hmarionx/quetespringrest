package fr.wildcodeschool.library.controller;

import fr.wildcodeschool.library.exception.ResourceNotFoundException;
import fr.wildcodeschool.library.model.Book;
import fr.wildcodeschool.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
class BookController {
    @Autowired
    BookRepository bookRepository;

    // Get all Books
    @GetMapping("/books")
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    // Create a new Book
    @PostMapping("/books")
    public Book createBook(@Valid @RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Get a single Book
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable(value = "id") Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    // Update a Book
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable(value = "id") Long id,
                           @Valid @RequestBody Book bookUpdate) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));

        book.setTitle(bookUpdate.getTitle());
        book.setAuthor(bookUpdate.getAuthor());
        book.setDescription(bookUpdate.getDescription());

        return bookRepository.save(book);
    }

    // Delete a Book
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));

        bookRepository.delete(book);

        return ResponseEntity.ok().build();
    }

    //Search a book
    @PostMapping("/search/{keyword}")
    public List<Book> getBookContains(@PathVariable(value = "keyword") String keyword) {

        return bookRepository.findByTitleOrDescriptionContaining(keyword);
    }
}
