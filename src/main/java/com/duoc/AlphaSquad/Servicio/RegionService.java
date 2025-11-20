package com.duoc.AlphaSquad.Servicio;

import com.duoc.AlphaSquad.Modelo.Region;
import com.duoc.AlphaSquad.Repositorio.RepRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RepRegion repRegion;

    public List<Region> listar() {
        return repRegion.findAll();
    }

    public Region buscarPorId(Long id) {
        return repRegion.findById(id).orElse(null);
    }

    public Region crear(Region region) {
        return repRegion.save(region);
    }

    public Region actualizar(Long id, Region region) {
        Region existente = buscarPorId(id);
        if (existente != null) {

            existente.setNombre(region.getNombre());

            return repRegion.save(existente);
        }
        return null;
    }

    public void eliminar(Long id) {
        repRegion.deleteById(id);
    }
}
