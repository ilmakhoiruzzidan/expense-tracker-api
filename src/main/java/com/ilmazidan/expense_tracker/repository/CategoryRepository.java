package com.ilmazidan.expense_tracker.repository;

import com.ilmazidan.expense_tracker.entity.Category;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "INSERT INTO m_category (id, category, description) " +
                    "VALUES (" +
                    ":#{#category.id}," +
                    ":#{#category.category.name()}," +
                    ":#{#category.description})"
    )
    void saveCategory(Category category);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM m_category"
    )
    Page<Category> findAll(Specification<Category> categorySpecification, Pageable pageable);

    @NonNull
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM m_category WHERE id = :id"
    )
    Optional<Category> findById(@NonNull String id);

    @Modifying
    @Transactional
    @Query(
            nativeQuery = true,
            value = "DELETE FROM m_category WHERE id = :id"
    )
    void deleteById(@NonNull String id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE m_category " +
                    "SET category = :#{#category.category.name()}, description = :#{#category.description} " +
                    "WHERE id = :#{#category.id}"
    )
    void updateById(Category category);

}
