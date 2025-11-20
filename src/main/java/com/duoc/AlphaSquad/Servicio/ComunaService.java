package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Comuna;
import com.duoc.AlphaSquad.Repositorio.RepComuna;
import com.duoc.AlphaSquad.Repositorio.RepRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComunaService {

    @Autowired
    private RepComuna repComuna;

    @Autowired
    private RepRegion repRegion;

    public List<Comuna> listar() {
        return repComuna.findAll();
    }

    public Comuna buscarPorId(Long id) {
        return repComuna.findById(id).orElse(null);
    }

    public Comuna crear(Comuna comuna) {

        if (comuna.getRegion() != null) {
            repRegion.findById(comuna.getRegion().getIdRegion()).orElse(null);
        }

        return repComuna.save(comuna);
    }

    public Comuna actualizar(Long id, Comuna comuna) {
        Comuna existente = buscarPorId(id);
        if (existente != null) {

            existente.setNombre(comuna.getNombre());

            if (comuna.getRegion() != null) {
                repRegion.findById(comuna.getRegion().getIdRegion()).orElse(null);
                existente.setRegion(comuna.getRegion());
            }

            return repComuna.save(existente);
        }
        return null;
    }

    public void eliminar(Long id) {
        repComuna.deleteById(id);
    }
}
