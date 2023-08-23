package com.example.database.repository;

import com.example.database.dto.Citizen;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizensRepo extends CrudRepository<Citizen, Long> {
}
