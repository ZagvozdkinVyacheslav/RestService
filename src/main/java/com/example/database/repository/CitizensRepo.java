package com.example.database.repository;

import com.example.database.dto.Citizen;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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

    @Modifying
    @Transactional
    @Query("UPDATE Citizen c SET  c.last_name = ?2 where c.id = ?1")
    int updateCitizenLastNameById(Long id,String lastName);
    @Modifying
    @Transactional
    @Query("UPDATE Citizen c SET  c.first_name = ?2 where c.id = ?1")
    int updateCitizenFirstNameById(Long id,String firstName);
    @Modifying
    @Transactional
    @Query("UPDATE Citizen c SET  c.middle_name = ?2 where c.id = ?1")
    int updateCitizenMiddleNameById(Long id,String middleName);
    @Modifying
    @Transactional
    @Query("UPDATE Citizen c SET  c.birth_date = ?2 where c.id = ?1")
    int updateCitizenBirthDateById(Long id,String birthDate);
    @Modifying
    @Transactional
    @Query("UPDATE Citizen c SET  c.phone = ?2 where c.id = ?1")
    int updateCitizenPhoneById(Long id,String phone);
    @Modifying
    @Transactional
    @Query("UPDATE Citizen c SET  c.extra_phone = ?2 where c.id = ?1")
    int updateCitizenExtraPhoneById(Long id,String extraPhone);
    @Modifying
    @Transactional
    @Query("UPDATE Citizen c SET  c.dul_serie = ?2 where c.id = ?1")
    int updateCitizenDulSerieById(Long id,Integer dulSerie);
    @Modifying
    @Transactional
    @Query("UPDATE Citizen c SET  c.dul_number = ?2 where c.id = ?1")
    int updateCitizenDulNumberById(Long id,Integer dulNumber);
}
