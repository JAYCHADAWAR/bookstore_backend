package com.bookstore.website.service;

import com.bookstore.website.controller.BookController;
import com.bookstore.website.dto.BookDto;
import com.bookstore.website.entity.Books;
import com.bookstore.website.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    public List<BookDto> getBooksByUserId(UUID userId) {
        // Implement your logic to retrieve books based on the user ID from the database
        // Example:
        List<Object[]> books= bookRepository.findByUserId(userId);
        List<BookDto> bookDtos = new ArrayList<>();

        for (Object[] result : books) {
            UUID id = (UUID) result[0];
            String bookname = (String) result[1];
            String filepath = (String) result[2];
            String imagepath = (String) result[3];
            String likes = (String) result[4];
            Boolean liked = (Boolean) result[5];

            BookDto bookDto = new BookDto(id, bookname, filepath, imagepath, likes, liked);
            bookDtos.add(bookDto);

        }
        log.info(books.toString());
        return bookDtos;
       // Replace this with actual implementation
    }
    public List<Books> getAllBooks() {
        List<Object[]> result1=bookRepository.findAllBooks();
        List<Books> books = new ArrayList<>();
        log.info(result1.toString());
        for (Object[] result : result1) {
            UUID id = (UUID) result[0];
            String name = (String) result[1];
            String likes = (String) result[2];
            Books book = new Books(id, name, likes);
            books.add(book);

        }
        log.info(books.toString());
        return books;
    }

    @Transactional
    public Boolean setLike(UUID bookId, boolean liked) {
            try {
               String likes=bookRepository.getLikes(bookId);
               int curlikes=Integer.parseInt(likes);
                    if (liked) {
                        curlikes++;
                    } else {
                        curlikes--;
                    }
                  int x=bookRepository.updateLikes(bookId, String.valueOf(curlikes));
                  if(x>0)
                        return true;
                  return false;


            } catch (Exception e) {

                throw e;
            }
    }

    public void saveBook(UUID id, String name, String imageFilePath, String pdfFilePath) {
        Books book = new Books();
        book.setId(id);
        book.setName(name);
        book.setLikes("0");
        book.setImagePath(imageFilePath);
        book.setPath(pdfFilePath);

        bookRepository.save(book);
    }

    public Integer updateBook(UUID id,String name, String imageFilePath, String pdfFilePath){

       int x= bookRepository.updateBook(id,name,imageFilePath,pdfFilePath);
        log.info(String.valueOf(x));
        return x;
    }

    public boolean deleteBook(UUID id) {
        try {
            bookRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }

    }



}
