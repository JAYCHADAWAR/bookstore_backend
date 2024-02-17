package com.bookstore.website.controller;

import com.bookstore.website.dto.BookDto;
import com.bookstore.website.entity.Books;
import com.bookstore.website.model.SetLikeRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.bookstore.website.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class BookController {

    private static final String UPLOAD_DIR = "uploads";
    @Autowired
    private BookService bookService;

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @GetMapping("/getbooks")
    public ResponseEntity<?> getBooks(@RequestParam("id") UUID userId) {
//        UUID userId = UUID.fromString(request.getHeader("id"));
        log.info(String.valueOf(userId));
        if (userId != null) {

            List<BookDto> books = bookService.getBooksByUserId(userId);
            return ResponseEntity.ok().body(Map.of("books", books));
        } else {
            // User is not authenticated
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
    }

    @GetMapping("/getallbooks")
    public ResponseEntity<?> getAllBooks() {
        List<Books> books = bookService.getAllBooks();
        return ResponseEntity.ok().body(Map.of("books", books));
    }

    @PostMapping("/setlike")
    public ResponseEntity<?> setLike(@RequestBody SetLikeRequest request) {
        try {
            UUID bookId = request.getBookId();
            boolean liked = request.isLiked();
            bookService.setLike(bookId, liked);
            return ResponseEntity.status(HttpStatus.OK).body("Book like status updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating book like status");
        }
    }

    @PostMapping("/uploadbook")
    public ResponseEntity<?> uploadBook(
            @RequestPart(name = "thumbnail") MultipartFile image,
            @RequestPart(name = "pdf_file") MultipartFile pdf,
            @RequestPart(name = "name") String name) { // Assuming you have a way to get the current user
        if (true) {
            if (image == null || pdf == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image and PDF file are required");
            }
            try {
                String imageOriginalName = StringUtils.cleanPath(image.getOriginalFilename());
                String pdfOriginalName = StringUtils.cleanPath(pdf.getOriginalFilename());
                UUID id = UUID.randomUUID();

                // Create upload directory if it doesn't exist
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Save files to the upload directory
                String imageFilePath = UPLOAD_DIR + File.separator + imageOriginalName;
                String pdfFilePath = UPLOAD_DIR + File.separator + pdfOriginalName;
                saveFile(image, imageFilePath);
                saveFile(pdf, pdfFilePath);

                // Save book information to the database
                bookService.saveBook(id, name, imageOriginalName, pdfOriginalName);

                return ResponseEntity.status(HttpStatus.OK).body("Book was successfully uploaded");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload book");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin not authenticated");
        }
    }

    @PatchMapping("/editbook")
    public ResponseEntity<?> editBook(
            @RequestPart(name = "thumbnail") MultipartFile image,
            @RequestPart(name = "pdf_file") MultipartFile pdf,
            @RequestPart(name = "name") String name,
            @RequestPart(name = "id") UUID id) {
        try {
            String imageOriginalName=null;
            String pdfOriginalName=null;
            // Save thumbnail file if uploaded
            if (image != null) {
                 imageOriginalName = StringUtils.cleanPath(image.getOriginalFilename());
                String imageFilePath = UPLOAD_DIR + File.separator + imageOriginalName;
                saveFile(image, imageFilePath);
            }

            if (pdf != null) {
                 pdfOriginalName = StringUtils.cleanPath(pdf.getOriginalFilename());
                String pdfFilePath = UPLOAD_DIR + File.separator + pdfOriginalName;
                saveFile(pdf, pdfFilePath);
            }

            // Update the database
            bookService.updateBook(id, name, imageOriginalName, pdfOriginalName);

            return ResponseEntity.ok().body("Book updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update book");
        }
    }


    @DeleteMapping("/deletebook")
    public ResponseEntity<?> deleteBook( @RequestBody Map<String, String> requestBody) {
        try {
            String idString = requestBody.get("id");
            UUID id = UUID.fromString(idString);

            boolean deleted = bookService.deleteBook(id);
            if (deleted) {
                return ResponseEntity.ok().body("Book deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting book");
        }
    }

    // Method to save file
    private void saveFile(MultipartFile file, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());
    }




}
