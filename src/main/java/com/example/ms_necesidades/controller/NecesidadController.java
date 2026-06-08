package com.example.ms_necesidades.controller;

import com.example.ms_necesidades.dto.NecesidadRequestDTO;
import com.example.ms_necesidades.dto.NecesidadResponseDTO;
import com.example.ms_necesidades.enums.EstadoNecesidad;
import com.example.ms_necesidades.service.NecesidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/necesidades")
@RequiredArgsConstructor
@Tag(name = "Necesidades", description = "Gestión de necesidades reportadas en terreno")
public class NecesidadController {

    private final NecesidadService necesidadService;

    @Operation(summary = "Reportar nueva necesidad")
    @ApiResponse(responseCode = "201",description = "Necesidad registrada exitosamente")
    @ApiResponse(responseCode = "400",description = "Datos invalidos")
    @PostMapping
    public ResponseEntity<NecesidadResponseDTO>crear(@Valid @RequestBody NecesidadRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(necesidadService.crear(dto));

    }

    @Operation(summary = "Listar todas las necesidades")
    @ApiResponse(responseCode = "200", description = "Lista de necesidades")
    @GetMapping
    public ResponseEntity<List<NecesidadResponseDTO>> listar() {
        return ResponseEntity.ok(necesidadService.listarTodas());
    }

    @Operation(summary = "Obtener necesidad por id")
    @ApiResponse(responseCode = "200",description = "Necesidad encontrada")
    @ApiResponse(responseCode = "404",description = "necesidad no encotrada")
    @GetMapping("/{id}")
        public ResponseEntity<NecesidadResponseDTO>obtener(@PathVariable Long id){
        return ResponseEntity.ok(necesidadService.obtenerPorId(id));
    }

    @Operation(summary = "Filtrar necesidad por estado")
    @ApiResponse(responseCode = "200",description = "lista filtrada por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<NecesidadResponseDTO>>porEstado(@PathVariable EstadoNecesidad estado){
        return ResponseEntity.ok(necesidadService.listarPorEstado(estado));
    }

    @Operation(summary = "Filtrar necesidad por region")
    @ApiResponse(responseCode = "200",description = "Lista por necesidad region")
    @ApiResponse(responseCode = "404",description = "Necesidad no encontrada")
    @GetMapping("/region/{region}")
    public ResponseEntity<List<NecesidadResponseDTO>> porRegion(@PathVariable String region){
        return ResponseEntity.ok(necesidadService.listarPorRegion(region));
    }

    @Operation(summary = "Actualizar estado de necesidad")
    @ApiResponse(responseCode = "200", description = "Estado actualidad correctamente ")
    @ApiResponse(responseCode = "404",description = "Necesidad no encontrada")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<NecesidadResponseDTO>actualizarEstado(@PathVariable Long id,@RequestParam EstadoNecesidad nuevoEstado){
        return ResponseEntity.ok(necesidadService.actualizarEstado(id,nuevoEstado));
    }

}
