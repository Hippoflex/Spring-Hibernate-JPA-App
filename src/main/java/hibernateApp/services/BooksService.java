package hibernateApp.services;

import hibernateApp.models.Book;
import hibernateApp.models.Person;
import hibernateApp.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findByBookName(String bookName) {
        return booksRepository.findByBookName(bookName);
    }

    public List<Book> findByOwner(Person owner) {
        return booksRepository.findByOwner(owner);
    }

    public List<Book> findByBookNameStartingWith(String prefix) {
        return booksRepository.findByBookNameStartingWith(prefix);
    }


    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Page<Book> findAll(Pageable pageable) {
        return booksRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));
    }


    public Book findOne(int id) {
        Optional<Book> optionalBook = booksRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public boolean checkDeprecatedBook(Book book) {
        Date whenPicked = book.getPickedAt();
        Date nowTime = new Date();
        return nowTime.toInstant().isBefore(whenPicked.toInstant().plusMillis(86400000 * 10));
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Book book) {
        if (book.getOwner().getId() == 0) {
            book.setOwner(null);
        }

        if (book.getOwner() != null) {
            book.setPickedAt(new Date());
        }

        book.setId(id);
        booksRepository.save(book);
    }

}
