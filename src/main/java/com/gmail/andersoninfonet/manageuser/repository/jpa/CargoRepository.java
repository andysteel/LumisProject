package com.gmail.andersoninfonet.manageuser.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmail.andersoninfonet.manageuser.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

}
