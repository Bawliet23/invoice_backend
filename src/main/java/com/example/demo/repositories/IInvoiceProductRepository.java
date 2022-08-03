package com.example.demo.repositories;

import com.example.demo.entities.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {
}
