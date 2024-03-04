package org.openschool.repository;

import org.openschool.entity.Category;
import org.openschool.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);


    @Query(value = "SELECT p.* FROM product p INNER JOIN category c ON p.category_id = c.id WHERE c.name = :categoryName",
            countQuery = "SELECT COUNT(p.id) FROM product p INNER JOIN category c ON p.category_id = c.id WHERE c.name = :categoryName",
            nativeQuery = true)
    Page<Product> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);



}
