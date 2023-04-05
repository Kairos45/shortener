package com.tvacher.shortener.repository;

import com.tvacher.shortener.model.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    @Query("SELECT u FROM Url u WHERE u.longUrl = :longUrl")
    Url findUrlByLongUrl(String longUrl);

    @Query("SELECT u FROM Url u WHERE u.shortUrl = :shortUrl")
    Url findUrlByShortUrl(String shortUrl);
}
