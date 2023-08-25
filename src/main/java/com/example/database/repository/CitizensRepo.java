package com.example.database.repository;

import com.example.database.dto.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizensRepo extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findById(Long aLong);
}
