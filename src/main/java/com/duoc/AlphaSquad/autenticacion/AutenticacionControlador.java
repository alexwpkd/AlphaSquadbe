package com.duoc.AlphaSquad.autenticacion;

import com.duoc.AlphaSquad.Modelo.Administrador;
import com.duoc.AlphaSquad.Modelo.Cliente;
import com.duoc.AlphaSquad.Modelo.Empleado;
import com.duoc.AlphaSquad.Servicio.AdminService;
import com.duoc.AlphaSquad.Servicio.ClienteService;
import com.duoc.AlphaSquad.Servicio.EmpleadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AutenticacionControlador {

    private final AutenticacionServicio authService;
    private final ClienteService clienteService;
    private final EmpleadoService empleadoService;
    private final AdminService adminService;

    public AutenticacionControlador(AutenticacionServicio authService,
                                    ClienteService clienteService,
                                    EmpleadoService empleadoService,
                                    AdminService adminService) {
        this.authService = authService;
        this.clienteService = clienteService;
        this.empleadoService = empleadoService;
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody InicioSesionSolicitud request) {

        InicioSesionResponsivo resp = authService.login(request);

        return (resp != null)
                ? ResponseEntity.ok(resp)
                : ResponseEntity.status(401).body("Credenciales incorrectas");
    }

    @PostMapping("/registro/cliente")
    public ResponseEntity<?> registroCliente(@RequestBody Cliente cliente) {

        if (!ValidacionPassword.esValido(cliente.getPassword()))
            return ResponseEntity.badRequest().body("Password inválida");

        return ResponseEntity.ok(clienteService.crear(cliente));
    }

    @PostMapping("/registro/empleado")
    public ResponseEntity<?> registroEmpleado(@RequestBody Empleado empleado) {

        if (!ValidacionPassword.esValido(empleado.getPassword()))
            return ResponseEntity.badRequest().body("Password inválida");

        return ResponseEntity.ok(empleadoService.crear(empleado));
    }

    @PostMapping("/registro/admin")
    public ResponseEntity<?> registroAdmin(@RequestBody Administrador admin) {

        if (!ValidacionPassword.esValido(admin.getPassword()))
            return ResponseEntity.badRequest().body("Password inválida");

        return ResponseEntity.ok(adminService.crear(admin));
    }
}
