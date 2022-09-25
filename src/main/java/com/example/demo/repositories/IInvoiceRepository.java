package com.example.demo.repositories;

import com.example.demo.entities.Invoice;
import com.example.demo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice,Long> {

    List<Invoice> findAllByUser(User user);
    Page<Invoice> findAllByUser(User user, Pageable pageable);
}
