package com.duoc.AlphaSquad.config;

import com.duoc.AlphaSquad.Modelo.Producto;
import com.duoc.AlphaSquad.Repositorio.RepProducto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RepProducto repo;

    public DataInitializer(RepProducto repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        if (repo.count() > 0) {
            System.out.println("Productos ya existen, no se cargará data inicial.");
            return;
        }

        System.out.println("Cargando productos iniciales...");

        repo.save(new Producto(null, "Replica Specna Arms CORE C03 Half tan",
                "SP-CORE-C03-TAN", 114500, true, 8,
                "Fusil AEG CORE C03 half tan.",
                "arma_primaria", "fusil",
                "products/product-img-1"));

        repo.save(new Producto(null, "KRYTAC FN P90 SMG Alpine Custom",
                "KRY-P90-ALPINE", 909990, true, 5,
                "Subfusil compacto edición Alpine.",
                "arma_primaria", "subfusil",
                "products/product-img-2"));

        repo.save(new Producto(null, "KRYTAC KRISS VECTOR GBB",
                "KRY-KRISS-VECTOR-GBB", 649990, true, 4,
                "KRISS VECTOR con sistema GBB.",
                "arma_primaria", "subfusil",
                "products/product-img-3"));

        repo.save(new Producto(null, "Replica Specna Arms Daniel Defense C19 Half tan",
                "SP-CORE-C19-DD-TAN", 156000, true, 6,
                "Fusil CORE C19 half tan.",
                "arma_primaria", "fusil",
                "products/product-img-4"));

        repo.save(new Producto(null, "Replica Specna Arms C13 CORE",
                "SP-CORE-C13", 159900, true, 9,
                "Fusil CORE C13, relación precio/rendimiento.",
                "arma_primaria", "fusil",
                "products/product-img-5"));

        repo.save(new Producto(null, "EMG F-1 Firearms Ultimate CQB UDR-15 AEG",
                "EMG-F1-UDR15-AEG", 399900, true, 3,
                "AR15 AEG estilo F-1.",
                "arma_primaria", "fusil",
                "products/product-img-6"));

        // ---- SECUNDARIAS ----
        repo.save(new Producto(null, "Tokyo Marui Hi-Capa 5.1 GBB",
                "TM-HICAPA-51-GBB", 260000, true, 10,
                "Pistola GBB de alto desempeño.",
                "arma_secundaria", "pistola",
                "products/product-img-7"));

        repo.save(new Producto(null, "WE Glock 17 GBB",
                "WE-G17-GBB", 220000, true, 12,
                "Clásica G17 con blowback.",
                "arma_secundaria", "pistola",
                "products/product-img-8"));

        repo.save(new Producto(null, "KJW M9 Full Metal GBB",
                "KJW-M9-GBB", 210000, true, 7,
                "M9 full metal con buen retroceso.",
                "arma_secundaria", "pistola",
                "products/product-img-9"));

        // ---- MUNICIÓN ----
        repo.save(new Producto(null, "BBs 6mm 0.25g (Bolsa 4.000)",
                "BBS-025-4000", 18000, true, 50,
                "0.25g estabilidad media distancia.",
                "municion", "bbs",
                "products/product-img-10"));

        repo.save(new Producto(null, "Cápsulas CO2 12g (Pack x10)",
                "CO2-12G-10PK", 8000, true, 60,
                "Cápsulas estándar para pistolas CO2.",
                "municion", "co2",
                "products/product-img-11"));

        // ---- ACCESORIOS ----
        repo.save(new Producto(null, "Mira Red Dot 1x20",
                "ACC-REDDOT-1X20", 35000, true, 15,
                "Óptica tipo red dot.",
                "accesorios", "optica",
                "products/product-img-12"));

        repo.save(new Producto(null, "Empuñadura táctica M-LOK",
                "ACC-GRIP-MLOK", 15000, true, 25,
                "Mejora el control del rifle.",
                "accesorios", "agarres",
                "products/product-img-13"));

        repo.save(new Producto(null, "Correa táctica 2 puntos",
                "ACC-SLING-2PT", 17000, true, 30,
                "Correa ajustable táctica.",
                "accesorios", "correas",
                "products/product-img-14"));

        repo.save(new Producto(null, "Linterna táctica 800 lm con montura",
                "ACC-FLASH-800LM", 28000, true, 18,
                "Linterna táctica de alta potencia.",
                "accesorios", "iluminacion",
                "products/product-img-15"));

        System.out.println("Productos iniciales cargados.");
    }
}
