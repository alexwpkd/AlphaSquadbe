package com.duoc.AlphaSquad.autenticacion;

import com.duoc.AlphaSquad.Modelo.Administrador;
import com.duoc.AlphaSquad.Modelo.Cliente;
import com.duoc.AlphaSquad.Modelo.Empleado;
import com.duoc.AlphaSquad.Repositorio.RepAdmin;
import com.duoc.AlphaSquad.Repositorio.RepCliente;
import com.duoc.AlphaSquad.Repositorio.RepEmpleado;
import com.duoc.AlphaSquad.seguridad.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacionServicio {

    private final RepAdmin repAdmin;
    private final RepCliente repCliente;
    private final RepEmpleado repEmpleado;
    private final JwtUtil jwtUtil;

    public AutenticacionServicio(RepAdmin repAdmin,
                                 RepCliente repCliente,
                                 RepEmpleado repEmpleado,
                                 JwtUtil jwtUtil) {
        this.repAdmin = repAdmin;
        this.repCliente = repCliente;
        this.repEmpleado = repEmpleado;
        this.jwtUtil = jwtUtil;
    }

    public InicioSesionResponsivo login(InicioSesionSolicitud request) {

        String correo = request.getCorreo();
        String password = request.getPassword();

        // 1) Intentar ADMIN
        Optional<Administrador> adminOpt = repAdmin.findByCorreo(correo);
        if (adminOpt.isPresent()) {
            Administrador admin = adminOpt.get();
            if (passwordCorrecta(password, admin.getPassword())) {
                String rol = "ADMIN";
                String token = jwtUtil.generarToken(correo, rol);

                InicioSesionResponsivo resp = new InicioSesionResponsivo(token, rol);
                resp.setCorreo(correo);
                // Si más adelante quieres idAdministrador, puedes agregarlo aquí
                return resp;
            }
        }

        // 2) Intentar EMPLEADO
        Optional<Empleado> empOpt = repEmpleado.findByCorreo(correo);
        if (empOpt.isPresent()) {
            Empleado emp = empOpt.get();
            if (passwordCorrecta(password, emp.getPassword())) {
                String rol = "EMPLEADO";
                String token = jwtUtil.generarToken(correo, rol);

                InicioSesionResponsivo resp = new InicioSesionResponsivo(token, rol);
                resp.setCorreo(correo);
                return resp;
            }
        }

        // 3) Intentar CLIENTE
        Optional<Cliente> cliOpt = repCliente.findByCorreo(correo);
        if (cliOpt.isPresent()) {
            Cliente cli = cliOpt.get();
            if (passwordCorrecta(password, cli.getPassword())) {
                String rol = "CLIENTE";
                String token = jwtUtil.generarToken(correo, rol);

                InicioSesionResponsivo resp = new InicioSesionResponsivo(token, rol);
                resp.setCorreo(correo);
                resp.setIdCliente(cli.getId());   // 👈 ESTE ES EL QUE LEE REACT
                return resp;
            }
        }

        // Si no coincide con nadie o contraseña incorrecta
        return null;
    }

    private boolean passwordCorrecta(String rawPassword, String storedPassword) {
        // Si en el futuro usas PasswordEncoder, cambia esto a:
        // return passwordEncoder.matches(rawPassword, storedPassword);
        return rawPassword != null && rawPassword.equals(storedPassword);
    }
}
