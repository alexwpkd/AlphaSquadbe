package com.duoc.AlphaSquad.autenticacion;

import com.duoc.AlphaSquad.Modelo.Administrador;
import com.duoc.AlphaSquad.Modelo.Cliente;
import com.duoc.AlphaSquad.Modelo.Empleado;
import com.duoc.AlphaSquad.Repositorio.RepAdmin;
import com.duoc.AlphaSquad.Repositorio.RepCliente;
import com.duoc.AlphaSquad.Repositorio.RepEmpleado;
import com.duoc.AlphaSquad.seguridad.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionServicio {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @Autowired
    private RepAdmin repAdmin;

    @Autowired
    private RepCliente repCliente;

    @Autowired
    private RepEmpleado repEmpleado;

    @Autowired
    private JwtUtil jwtUtil;

    public InicioSesionResponsivo login(InicioSesionSolicitud request) {

        String correo = request.getCorreo();
        String pass = request.getPassword();

        // ADMIN
        Administrador admin = repAdmin.findByCorreo(correo).orElse(null);
        if (admin != null && admin.getPassword().equals(pass))
            return new InicioSesionResponsivo(jwtUtil.generarToken(correo, "ADMIN"), "ADMIN");

        // CLIENTE
        Cliente cliente = repCliente.findByCorreo(correo).orElse(null);
        if (cliente != null && cliente.getPassword().equals(pass))
            return new InicioSesionResponsivo(jwtUtil.generarToken(correo, "CLIENTE"), "CLIENTE");

        // EMPLEADO
        Empleado empleado = repEmpleado.findByCorreo(correo).orElse(null);
        if (empleado != null && empleado.getPassword().equals(pass))
            return new InicioSesionResponsivo(jwtUtil.generarToken(correo, "EMPLEADO"), "EMPLEADO");

        return null;
    }
}
