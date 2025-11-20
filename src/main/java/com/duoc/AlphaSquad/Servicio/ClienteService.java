package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Cliente;
import com.duoc.AlphaSquad.Repositorio.RepCliente;
import com.duoc.AlphaSquad.Repositorio.RepComuna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private RepCliente repCliente;

    @Autowired
    private RepComuna repComuna;

    public List<Cliente> listar() {
        return repCliente.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return repCliente.findById(id).orElse(null);
    }

    public Cliente crear(Cliente cliente) {

        if (cliente.getComuna() != null) {
            repComuna.findById(cliente.getComuna().getIdComuna()).orElse(null);
        }

        return repCliente.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente existente = buscarPorId(id);
        if (existente != null) {

            existente.setNombre(cliente.getNombre());
            existente.setApellidos(cliente.getApellidos());
            existente.setRut(cliente.getRut());
            existente.setCorreo(cliente.getCorreo());
            existente.setPassword(cliente.getPassword());
            existente.setDireccion(cliente.getDireccion());

            if (cliente.getComuna() != null) {
                repComuna.findById(cliente.getComuna().getIdComuna()).orElse(null);
                existente.setComuna(cliente.getComuna());
            }

            return repCliente.save(existente);
        }
        return null;
    }

    public Optional<Cliente> buscarPorCorreo(String correo) {
        return repCliente.findByCorreo(correo);
    }

    public Optional<Cliente> buscarPorRut(String rut) {
        return repCliente.findByRut(rut);
    }

    public void eliminar(Long id) {
        repCliente.deleteById(id);
    }
}

