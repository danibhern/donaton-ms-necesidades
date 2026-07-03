package com.example.ms_necesidades.repository;

import com.example.ms_necesidades.enums.*;
import com.example.ms_necesidades.model.Necesidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NecesidadRepository extends JpaRepository<Necesidad, Long> {
    List<Necesidad> findByEstado(EstadoNecesidad estado);
    List<Necesidad> findByRegion(String region);
    List<Necesidad> findByTipoRecurso(TipoRecurso tipoRecurso);
    List<Necesidad> findByReportadoPorId(Long usuarioId);
}