package com.ilmazidan.expense_tracker.repository;

import com.ilmazidan.expense_tracker.entity.PaymentMethod;
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
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {
    @Transactional
    @Modifying
    @Query(
            nativeQuery = true,
            value = "INSERT INTO m_payment_method (id, payment_method, description) " +
                    "VALUES (" +
                    ":#{#paymentMethod.id}, " +
                    ":#{#paymentMethod.paymentMethodType.name()}," +
                    ":#{#paymentMethod.description})"
    )
    void savePaymentMethod(PaymentMethod paymentMethod);

    @NonNull
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM m_payment_method"
    )
    Page<PaymentMethod> findAll(@NonNull Pageable pageable);

    @NonNull
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM m_payment_method WHERE id = :id"
    )
    Optional<PaymentMethod> findById(@NonNull String id);


    @Modifying
    @Query(
            nativeQuery = true,
            value = "DELETE FROM m_payment_method WHERE id = :id"
    )
    void deleteById(@NonNull String id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE m_payment_method " +
                    "SET description =  :#{#paymentMethod.description}," +
                    "payment_method =  :#{#paymentMethod.paymentMethodType.name()} " +
                    "WHERE id = :#{#paymentMethod.id}"
    )
    void updateById(PaymentMethod paymentMethod);

}
