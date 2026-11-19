package com.prueba.saas.repository;

import com.prueba.saas.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    List<Incidencia> findAllByOrderByCreadaEnDescIdDesc();
}
