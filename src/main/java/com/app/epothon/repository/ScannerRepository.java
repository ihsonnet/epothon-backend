package com.app.epothon.repository;

import com.app.epothon.model.ScannerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScannerRepository extends JpaRepository<ScannerModel, String> {
}
