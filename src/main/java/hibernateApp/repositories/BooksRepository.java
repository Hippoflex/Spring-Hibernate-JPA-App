package hibernateApp.repositories;

import hibernateApp.models.Book;
import hibernateApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookName(String bookName);

    List<Book> findByOwner(Person person);
}
