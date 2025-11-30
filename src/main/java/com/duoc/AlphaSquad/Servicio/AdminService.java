package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Administrador;
import com.duoc.AlphaSquad.Repositorio.RepAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private RepAdmin repAdmin;

    public List<Administrador> listar() {
        return repAdmin.findAll();
    }

    public Administrador buscarPorId(Long id) {
        return repAdmin.findById(id).orElse(null);
    }

    public Administrador crear(Administrador admin) {
        //FORZAR que sea nuevo registro
        admin.setIdAdministrador(null);
        return repAdmin.save(admin);
    }

    public Administrador actualizar(Long id, Administrador admin) {
        Administrador existente = buscarPorId(id);
        if (existente != null) {
            existente.setNombre(admin.getNombre());
            existente.setApellido(admin.getApellido());
            existente.setRut(admin.getRut());
            existente.setCorreo(admin.getCorreo());
            existente.setPassword(admin.getPassword());
            return repAdmin.save(existente);
        }
        return null;
    }

    public void eliminar(Long id) {
        repAdmin.deleteById(id);
    }
}
