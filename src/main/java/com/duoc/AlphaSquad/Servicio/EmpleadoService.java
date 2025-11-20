package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Empleado;
import com.duoc.AlphaSquad.Repositorio.RepAdmin;
import com.duoc.AlphaSquad.Repositorio.RepEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private RepEmpleado repEmpleado;

    @Autowired
    private RepAdmin repAdmin;

    public List<Empleado> listar() {
        return repEmpleado.findAll();
    }

    public Empleado buscarPorId(Long id) {
        return repEmpleado.findById(id).orElse(null);
    }

    public Empleado crear(Empleado empleado) {

        repAdmin.findById(empleado.getAdministrador().getIdAdministrador()).orElse(null);

        return repEmpleado.save(empleado);
    }

    public Empleado actualizar(Long id, Empleado empleado) {
        Empleado existente = buscarPorId(id);
        if (existente != null) {

            existente.setNombre(empleado.getNombre());
            existente.setApellido(empleado.getApellido());
            existente.setCorreo(empleado.getCorreo());
            existente.setRut(empleado.getRut());
            existente.setPassword(empleado.getPassword());

            return repEmpleado.save(existente);
        }
        return null;
    }

    public Optional<Empleado> buscarPorCorreo(String correo) {
        return repEmpleado.findByCorreo(correo);
    }

    public Optional<Empleado> buscarPorRut(String rut) {
        return repEmpleado.findByRut(rut);
    }

    public void eliminar(Long id) {
        repEmpleado.deleteById(id);
    }
}
