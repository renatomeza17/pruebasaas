package com.prueba.saas.services;

import com.prueba.saas.model.Incidencia;
import com.prueba.saas.model.Incidencia.Criticidad;
import com.prueba.saas.model.Incidencia.Estado;
import com.prueba.saas.repository.IncidenciaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IncidenciaService {
    private final IncidenciaRepository repositorio;

    public IncidenciaService(IncidenciaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<Incidencia> listar() {
        return repositorio.findAllByOrderByCreadaEnDescIdDesc();
    }

    public Incidencia registrar(String titulo, String integracion, String impacto, Criticidad criticidad) {
        return repositorio.save(new Incidencia(titulo, integracion, impacto, criticidad));
    }

    @Transactional
    public void cambiarEstado(long id, Estado estado) {
        repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incidencia no encontrada"))
                .cambiarEstado(estado);
    }

    public void eliminar(long id) {
        repositorio.deleteById(id);
    }
}
