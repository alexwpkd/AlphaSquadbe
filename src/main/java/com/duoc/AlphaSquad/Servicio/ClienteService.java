package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Cliente;
import com.duoc.AlphaSquad.Modelo.Comuna;
import com.duoc.AlphaSquad.Repositorio.RepCliente;
import com.duoc.AlphaSquad.Repositorio.RepComuna;
import com.duoc.AlphaSquad.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final RepCliente repCliente;
    private final RepComuna repComuna;

    public ClienteService(RepCliente repCliente, RepComuna repComuna) {
        this.repCliente = repCliente;
        this.repComuna = repComuna;
    }

    // ===== CRUD BÁSICO =====

    public List<Cliente> listar() {
        return repCliente.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return repCliente.findById(id).orElse(null);
    }

    public Cliente crear(Cliente cliente) {

        // ⚠️ Manejo seguro de COMUNA
        if (cliente.getComuna() != null && cliente.getComuna().getIdComuna() != null) {
            Long idComuna = cliente.getComuna().getIdComuna();

            Comuna comuna = repComuna.findById(idComuna)
                    .orElseThrow(() -> new ResourceNotFoundException("Comuna no encontrada: " + idComuna));

            cliente.setComuna(comuna);
        } else {
            // Si no viene comuna o viene sin ID, NO llames a findById(null)
            cliente.setComuna(null);
        }

        // El ID del cliente es AUTO-INCREMENT (no se setea desde el front)
        return repCliente.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente nuevo) {
        return repCliente.findById(id).map(actual -> {

            actual.setNombre(nuevo.getNombre());
            actual.setApellidos(nuevo.getApellidos());
            actual.setRut(nuevo.getRut());
            actual.setCorreo(nuevo.getCorreo());
            actual.setPassword(nuevo.getPassword());
            actual.setDireccion(nuevo.getDireccion());

            // ⚠️ Manejo seguro de COMUNA también en actualización
            if (nuevo.getComuna() != null && nuevo.getComuna().getIdComuna() != null) {
                Long idComuna = nuevo.getComuna().getIdComuna();
                Comuna comuna = repComuna.findById(idComuna)
                        .orElseThrow(() -> new ResourceNotFoundException("Comuna no encontrada: " + idComuna));
                actual.setComuna(comuna);
            } else {
                actual.setComuna(null);
            }

            return repCliente.save(actual);
        }).orElse(null);
    }

    public void eliminar(Long id) {
        repCliente.deleteById(id);
    }

    // ===== BÚSQUEDAS ESPECÍFICAS =====

    public Optional<Cliente> buscarPorCorreo(String correo) {
        return repCliente.findByCorreo(correo);
    }

    public Optional<Cliente> buscarPorRut(String rut) {
        return repCliente.findByRut(rut);
    }
}
