package com.ilmazidan.expense_tracker.repository;

import com.ilmazidan.expense_tracker.entity.UserAccount;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(
            nativeQuery = true,
            value = "INSERT INTO m_user_account (id, username, password, role)" +
                    "VALUES (" +
                    ":#{#userAccount.id}," +
                    ":#{#userAccount.username}," +
                    ":#{#userAccount.password}," +
                    ":#{#userAccount.role.name()}" +
                    ")"
    )
    void saveUserAccount(UserAccount userAccount);

    @Query(
           nativeQuery = true,
           value = "SELECT * FROM m_user_account WHERE id = :id"
    )
    Optional<UserAccount> findUserById(String id);

    @Query(
            nativeQuery = true,
            value = "SELECT EXISTS (SELECT 1 FROM m_user_account WHERE username = :username)"
    )
    boolean existsByUsername(String username);


    @NonNull
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM m_user_account"
    )
    List<UserAccount> findAll();

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM m_user_account WHERE username = :username"
    )
    Optional<UserAccount> findByUsername(String username);

}


