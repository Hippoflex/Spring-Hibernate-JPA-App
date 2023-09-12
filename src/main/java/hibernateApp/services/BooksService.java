package hibernateApp.services;

import hibernateApp.models.Book;
import hibernateApp.models.Person;
import hibernateApp.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findByBookName(String bookName){
        return booksRepository.findByBookName(bookName);
    }

    public List<Book> findByOwner(Person owner){
        return booksRepository.findByOwner(owner);
    }

}
