package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Compra;
import com.duoc.AlphaSquad.Repositorio.RepAdmin;
import com.duoc.AlphaSquad.Repositorio.RepCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    @Autowired
    private RepCompra repCompra;

    @Autowired
    private RepAdmin repAdmin;

    public List<Compra> listar() {
        return repCompra.findAll();
    }

    public Compra buscarPorId(Long id) {
        return repCompra.findById(id).orElse(null);
    }

    public Compra crear(Compra compra) {

        if (compra.getAdministrador() != null) {
            repAdmin.findById(compra.getAdministrador().getIdAdministrador()).orElse(null);
        }

        return repCompra.save(compra);
    }

    public Compra actualizar(Long id, Compra compra) {
        Compra existente = buscarPorId(id);
        if (existente != null) {

            existente.setEstado(compra.getEstado());
            existente.setFechaCompra(compra.getFechaCompra());
            existente.setTotalCompra(compra.getTotalCompra());

            return repCompra.save(existente);
        }
        return null;
    }

    public void eliminar(Long id) {
        repCompra.deleteById(id);
    }
}
