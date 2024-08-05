package com.bookstore.website.repository;

import com.bookstore.website.entity.Books;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Books, UUID> {


    // Define custom query methods if needed, for example:
    @Query(value = "SELECT b.id AS id, b.name AS bookname, b.path AS filepath, " +
            "b.image_path AS imagepath, b.likes AS likes, " +
            "CASE WHEN ul.bookid IS NOT NULL THEN TRUE ELSE FALSE END AS liked " +
            "FROM books AS b " +
            "LEFT JOIN user_likes AS ul ON b.id = ul.bookid AND ul.userid = ?1", nativeQuery = true)
     List<Object[]> findByUserId(UUID userId);


     @Query(value="SELECT id, name, likes from books",nativeQuery=true)
     List<Object[]> findAllBooks();

     @Query(value="SELECT likes FROM books WHERE id = ?1",nativeQuery = true)
     String getLikes(UUID bookId);

    @Modifying
    @Transactional
    @Query(value="UPDATE books  SET likes = :likes WHERE id = :bookId",nativeQuery = true)
    Integer updateLikes(UUID bookId, String likes);

    @Modifying
    @Transactional
    @Query("UPDATE Books b SET" +
            " b.name = COALESCE(:name, b.name)," +
            " b.image_path = COALESCE(:imagePath, b.image_path)," +
            " b.path = COALESCE(:pdfPath, b.path)" +
            " WHERE b.id = :id")
    Integer updateBook(UUID id, String name, String imagePath, String pdfPath);
}
