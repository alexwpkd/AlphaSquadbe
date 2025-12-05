package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Administrador;
import com.duoc.AlphaSquad.Modelo.Empleado;
import com.duoc.AlphaSquad.Repositorio.RepAdmin;
import com.duoc.AlphaSquad.Repositorio.RepEmpleado;
import com.duoc.AlphaSquad.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private final RepEmpleado repEmpleado;
    private final RepAdmin repAdmin;

    public EmpleadoService(RepEmpleado repEmpleado, RepAdmin repAdmin) {
        this.repEmpleado = repEmpleado;
        this.repAdmin = repAdmin;
    }

    // ===== CRUD BÁSICO =====

    public List<Empleado> listar() {
        return repEmpleado.findAll();
    }

    public Empleado buscarPorId(Long id) {
        return repEmpleado.findById(id).orElse(null);
    }

    public Empleado crear(Empleado empleado) {

        // ⚠️ Manejo seguro de ADMINISTRADOR
        if (empleado.getAdministrador() != null &&
                empleado.getAdministrador().getIdAdministrador() != null) {

            Long idAdmin = empleado.getAdministrador().getIdAdministrador();

            Administrador admin = repAdmin.findById(idAdmin)
                    .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado: " + idAdmin));

            empleado.setAdministrador(admin);
        } else {
            // Si no viene admin o sin id, puedes dejarlo null o lanzar error según tu negocio
            empleado.setAdministrador(null);
        }

        // ID de Empleado también es AUTO-INCREMENT
        return repEmpleado.save(empleado);
    }

    public Empleado actualizar(Long id, Empleado nuevo) {
        return repEmpleado.findById(id).map(actual -> {

            actual.setNombre(nuevo.getNombre());
            actual.setApellido(nuevo.getApellido());
            actual.setRut(nuevo.getRut());
            actual.setCorreo(nuevo.getCorreo());
            actual.setPassword(nuevo.getPassword());

            // ⚠️ Manejo de ADMINISTRADOR en actualización
            if (nuevo.getAdministrador() != null &&
                    nuevo.getAdministrador().getIdAdministrador() != null) {

                Long idAdmin = nuevo.getAdministrador().getIdAdministrador();
                Administrador admin = repAdmin.findById(idAdmin)
                        .orElseThrow(() -> new ResourceNotFoundException("Administrador no encontrado: " + idAdmin));
                actual.setAdministrador(admin);
            } else {
                actual.setAdministrador(null);
            }

            return repEmpleado.save(actual);
        }).orElse(null);
    }

    public void eliminar(Long id) {
        repEmpleado.deleteById(id);
    }

    // ===== BÚSQUEDAS ESPECÍFICAS =====

    public Optional<Empleado> buscarPorCorreo(String correo) {
        return repEmpleado.findByCorreo(correo);
    }

    public Optional<Empleado> buscarPorRut(String rut) {
        return repEmpleado.findByRut(rut);
    }
}
