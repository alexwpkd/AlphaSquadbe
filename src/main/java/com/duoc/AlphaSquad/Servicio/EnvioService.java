package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Envio;
import com.duoc.AlphaSquad.Repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    @Autowired
    private RepEnvio repEnvio;

    @Autowired
    private RepVenta repVenta;

    @Autowired
    private RepCliente repCliente;

    @Autowired
    private RepEmpleado repEmpleado;

    @Autowired
    private RepComuna repComuna;

    public List<Envio> listar() {
        return repEnvio.findAll();
    }

    public Envio buscarPorId(Long id) {
        return repEnvio.findById(id).orElse(null);
    }

    public Envio crear(Envio envio) {

        repVenta.findById(envio.getVenta().getIdVenta()).orElse(null);

        if (envio.getCliente() != null) {
            repCliente.findById(envio.getCliente().getId()).orElse(null);
        }
        if (envio.getComuna() != null) {
            repComuna.findById(envio.getComuna().getIdComuna()).orElse(null);
        }
        if (envio.getEmpleado() != null) {
            repEmpleado.findById(envio.getEmpleado().getId()).orElse(null);
        }

        return repEnvio.save(envio);
    }

    public Envio actualizar(Long id, Envio envio) {
        Envio existente = buscarPorId(id);
        if (existente != null) {

            existente.setEstado(envio.getEstado());
            existente.setCiudad(envio.getCiudad());
            existente.setCostoEnvio(envio.getCostoEnvio());
            existente.setDireccionEntrega(envio.getDireccionEntrega());
            existente.setFechaEntregaEstimada(envio.getFechaEntregaEstimada());

            return repEnvio.save(existente);
        }
        return null;
    }

    public Optional<Envio> buscarPorVenta(Long idVenta) {
        return repEnvio.findByVenta_IdVenta(idVenta);
    }

    public void eliminar(Long id) {
        repEnvio.deleteById(id);
    }
}
