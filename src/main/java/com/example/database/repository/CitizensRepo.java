package com.example.database.repository;

import com.example.database.dto.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitizensRepo extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findById(Long aLong);
    @Query("FROM Citizen WHERE last_name = ?1 AND first_name = ?2 AND middle_name = ?3 AND birth_date = ?4")
    List<Citizen>listCitizensByParam(String lastName, String firstName, String middleName, String birthDate);
}
