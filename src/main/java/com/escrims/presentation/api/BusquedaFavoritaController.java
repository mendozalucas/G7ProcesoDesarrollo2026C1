package com.escrims.presentation.api;

import com.escrims.application.dto.BusquedaFavoritaDTO;
import com.escrims.application.usecases.ListBusquedasFavoritasUseCase;
import com.escrims.application.usecases.SaveBusquedaFavoritaUseCase;
import com.escrims.application.usecases.ToggleAlertaBusquedaUseCase;
import com.escrims.presentation.api.dto.BusquedaFavoritaRequest;
import com.escrims.presentation.api.mapper.ProfileRequestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios/{usuarioId}/busquedas-favoritas")
public class BusquedaFavoritaController {

    private final ListBusquedasFavoritasUseCase listBusquedasFavoritasUseCase;
    private final SaveBusquedaFavoritaUseCase saveBusquedaFavoritaUseCase;
    private final ToggleAlertaBusquedaUseCase toggleAlertaBusquedaUseCase;

    public BusquedaFavoritaController(ListBusquedasFavoritasUseCase listBusquedasFavoritasUseCase,
                                       SaveBusquedaFavoritaUseCase saveBusquedaFavoritaUseCase,
                                       ToggleAlertaBusquedaUseCase toggleAlertaBusquedaUseCase) {
        this.listBusquedasFavoritasUseCase = listBusquedasFavoritasUseCase;
        this.saveBusquedaFavoritaUseCase = saveBusquedaFavoritaUseCase;
        this.toggleAlertaBusquedaUseCase = toggleAlertaBusquedaUseCase;
    }

    @GetMapping
    public List<BusquedaFavoritaDTO> listar(@PathVariable UUID usuarioId) {
        return listBusquedasFavoritasUseCase.execute(usuarioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusquedaFavoritaDTO guardar(@PathVariable UUID usuarioId,
                                        @RequestBody BusquedaFavoritaRequest request) {
        return saveBusquedaFavoritaUseCase.execute(
                usuarioId, ProfileRequestMapper.toCommand(request));
    }

    @PostMapping("/{busquedaId}/activar-alerta")
    public BusquedaFavoritaDTO activarAlerta(@PathVariable UUID busquedaId) {
        return toggleAlertaBusquedaUseCase.activar(busquedaId);
    }

    @PostMapping("/{busquedaId}/desactivar-alerta")
    public BusquedaFavoritaDTO desactivarAlerta(@PathVariable UUID busquedaId) {
        return toggleAlertaBusquedaUseCase.desactivar(busquedaId);
    }
}
