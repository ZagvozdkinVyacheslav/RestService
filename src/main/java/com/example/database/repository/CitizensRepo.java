package com.example.database.repository;

import com.example.database.dto.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitizensRepo extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findById(Long aLong);
    @Query("FROM Citizen WHERE last_name = ?1 AND first_name = ?2 AND middle_name = ?3 AND birth_date = ?4")
    List<Citizen>listCitizensByParam(String lastName, String firstName, String middleName, String birthDate);

    @Query(value = "SELECT c FROM Citizen c WHERE(:last_name is null or c.last_name = :last_name) " +
            "AND (:first_name is null or c.first_name = :first_name) " +
            "AND (:middle_name is null or c.middle_name = :middle_name) " +
            "AND (:birth_date is null or c.birth_date = :birth_date)"
    )
    List<Citizen> findListOfCitizensByOptionalParams(@Param("last_name")String lastName,
                                                     @Param("first_name")String firstName,
                                                     @Param("middle_name")String middleName,
                                                     @Param("birth_date")String birthDate
    );
}
