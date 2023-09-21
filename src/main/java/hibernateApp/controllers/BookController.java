package hibernateApp.controllers;

import hibernateApp.models.Book;
import hibernateApp.models.Person;
import hibernateApp.services.BooksService;
import hibernateApp.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;

    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "100") int size,
                        @RequestParam(value = "sort", defaultValue = "year") String sort
    ) {
        model.addAttribute("books", booksService.findAll(PageRequest.of(page, size, Sort.by(sort))));
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("books", booksService.findOne(id));
        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book, Model model) {
        List<Person> personList = (peopleService.findAll());
        model.addAttribute("users", personList);
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";

        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        List<Person> personList = (peopleService.findAll());
        model.addAttribute("users", personList);
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "book/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchPage(Model model) {
        List<Book> allBooks = booksService.findAll();
        model.addAttribute("books", allBooks);
        return "book/search";
    }

    @PostMapping("/search")
    public String searchBooks(@RequestParam("prefix") String prefix, Model model) {
        List<Book> searchResults = booksService.findByBookNameStartingWith(prefix);
        model.addAttribute("books", searchResults);
        return "book/search";
    }
}
