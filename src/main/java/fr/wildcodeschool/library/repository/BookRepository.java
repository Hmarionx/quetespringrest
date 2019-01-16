package fr.wildcodeschool.library.repository;

import fr.wildcodeschool.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1% OR b.description LIKE %?1%")
    List<Book> findByTitleOrDescriptionContaining(String keyword);
}


