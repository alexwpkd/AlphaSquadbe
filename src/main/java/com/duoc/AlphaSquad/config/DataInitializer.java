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
                "https://dojiw2m9tvv09.cloudfront.net/59007/product/X_nw-sa-c03core-tan17889.jpg?75&time=1764640077"));

        repo.save(new Producto(null, "KRYTAC FN P90 SMG Alpine Custom",
                "KRY-P90-ALPINE", 909990, true, 5,
                "Subfusil compacto edición Alpine.",
                "arma_primaria", "subfusil",
                "https://cdn11.bigcommerce.com/s-zzrjmsffom/products/1049/images/5307/FN_P90_AP_Modular_Receiver_with_Handguard_PL__70402_5304__86981.1667933979.1280.1280.jpg?c=2"));

        repo.save(new Producto(null, "KRYTAC KRISS VECTOR GBB",
                "KRY-KRISS-VECTOR-GBB", 649990, true, 4,
                "KRISS VECTOR con sistema GBB.",
                "arma_primaria", "subfusil",
                "https://cdn11.bigcommerce.com/s-zzrjmsffom/products/1079/images/5415/vector_smg_1a__41912.1697822075.1280.1280__29001.1697823662.1280.1280.jpg?c=2"));

        repo.save(new Producto(null, "Replica Specna Arms Daniel Defense C19 Half tan",
                "SP-CORE-C19-DD-TAN", 156000, true, 6,
                "Fusil CORE C19 half tan.",
                "arma_primaria", "fusil",
                "https://dojiw2m9tvv09.cloudfront.net/59007/product/nw-sa-c19coremk18danieldefense-tan18218.jpg"));

        repo.save(new Producto(null, "Replica Specna Arms C13 CORE",
                "SP-CORE-C13", 159900, true, 9,
                "Fusil CORE C13, relación precio/rendimiento.",
                "arma_primaria", "fusil",
                "https://airsoftdefence.com/wp-content/uploads/2023/02/SPECNA-ARMS-SA-C13-CORE_Replicas_24102_1.jpeg"));

        repo.save(new Producto(null, "EMG F-1 Firearms Ultimate CQB UDR-15 AEG",
                "EMG-F1-UDR15-AEG", 399900, true, 3,
                "AR15 AEG estilo F-1.",
                "arma_primaria", "fusil",
                "https://www.evike.com/images/aps-101000-sm.jpg"));

        // ---- SECUNDARIAS ----
        repo.save(new Producto(null, "Tokyo Marui Hi-Capa 5.1 GBB",
                "TM-HICAPA-51-GBB", 260000, true, 10,
                "Pistola GBB de alto desempeño.",
                "arma_secundaria", "pistola",
                "https://i0.wp.com/www.militarygear.cl/wp-content/uploads/2025/03/5.1-GOVERNMENT-1.jpg?fit=1000%2C1000&ssl=1"));

        repo.save(new Producto(null, "WE Glock 17 GBB",
                "WE-G17-GBB", 220000, true, 12,
                "Clásica G17 con blowback.",
                "arma_secundaria", "pistola",
                "https://dojiw2m9tvv09.cloudfront.net/59007/product/X_dscf1105-289685257.jpg?75&time=1764099274"));

        repo.save(new Producto(null, "KJW M9 Full Metal GBB",
                "KJW-M9-GBB", 210000, true, 7,
                "M9 full metal con buen retroceso.",
                "arma_secundaria", "pistola",
                "https://www.tactishop.mx/wp-content/uploads/2022/10/GP_KJW_601_lg.jpg"));

        // ---- MUNICIÓN ----
        repo.save(new Producto(null, "BBs 6mm 0.25g (Bolsa 4.000)",
                "BBS-025-4000", 18000, true, 50,
                "0.25g estabilidad media distancia.",
                "municion", "bbs",
                "https://dojiw2m9tvv09.cloudfront.net/6365/product/M_file_photo_p055327002.jpg?45&time=1756252228"));

        repo.save(new Producto(null, "Cápsulas CO2 12g (Pack x10)",
                "CO2-12G-10PK", 8000, true, 60,
                "Cápsulas estándar para pistolas CO2.",
                "municion", "co2",
                "https://http2.mlstatic.com/D_NQ_NP_673910-MLC51187937967_082022-O.webp"));

        // ---- ACCESORIOS ----
        repo.save(new Producto(null, "Mira Red Dot 1x20",
                "ACC-REDDOT-1X20", 35000, true, 15,
                "Óptica tipo red dot.",
                "accesorios", "optica",
                "https://http2.mlstatic.com/D_NQ_NP_952262-MLC50937890702_072022-O-mira-holografica-miras-1x20-mira-red-dot-mira-profesional.webp"));

        repo.save(new Producto(null, "Empuñadura táctica M-LOK",
                "ACC-GRIP-MLOK", 15000, true, 25,
                "Mejora el control del rifle.",
                "accesorios", "agarres",
                "https://aresmaxima.com/wp-content/uploads/2022/12/MRFM_b_2_tiny.jpg"));

        repo.save(new Producto(null, "Correa táctica 2 puntos",
                "ACC-SLING-2PT", 17000, true, 30,
                "Correa ajustable táctica.",
                "accesorios", "correas",
                "https://acdn-us.mitiendanube.com/stores/001/490/981/products/s1e7021ef77bc4bfca2b968e98dfabef38-9faba2aba912a93b6b17230561056032-1024-1024.webp"));

        repo.save(new Producto(null, "Linterna táctica 800 lm con montura",
                "ACC-FLASH-800LM", 28000, true, 18,
                "Linterna táctica de alta potencia.",
                "accesorios", "iluminacion",
                "https://protecto.cl/wp-content/uploads/2025/11/TAC-1.webp"));

        System.out.println("Productos iniciales cargados.");
    }
}
