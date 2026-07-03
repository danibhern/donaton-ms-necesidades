package com.example.ms_necesidades.factory;


import com.example.ms_necesidades.dto.NecesidadRequestDTO;
import com.example.ms_necesidades.model.Necesidad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NecesidadFactorySelector {
    private final NecesidadMedicaFactory medicaFactory;
    private final NecesidadAlimentoFactory alimentoFactory;
    private final NecesidadRopaFactory ropaFactory;
    private final NecesidadHigieneFactory higieneFactory;
    private final NecesidadOtroFactory otroFactory;
    public Necesidad crear (NecesidadRequestDTO dto){
        NecesidadFactory factory =switch (dto.getTipoRecurso()){
            case INSUMO_MEDICO -> medicaFactory;
            case ALIMENTO_NO_PERECIBLE -> alimentoFactory;
            case ROPA -> ropaFactory;
            case HIGIENE -> higieneFactory;
            case OTRO -> otroFactory;
        };
        return factory.crear(dto);
    }
}
