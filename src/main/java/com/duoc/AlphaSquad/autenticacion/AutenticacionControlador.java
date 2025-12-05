package com.duoc.AlphaSquad.autenticacion;

import com.duoc.AlphaSquad.Modelo.Administrador;
import com.duoc.AlphaSquad.Modelo.Cliente;
import com.duoc.AlphaSquad.Modelo.Empleado;
import com.duoc.AlphaSquad.Servicio.AdminService;
import com.duoc.AlphaSquad.Servicio.ClienteService;
import com.duoc.AlphaSquad.Servicio.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
// @CrossOrigin("*")  // No es necesario si ya usas WebConfig global
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
    public ResponseEntity<?> registroCliente(@Valid @RequestBody Cliente cliente) {
        try {
            if (!ValidacionPassword.esValido(cliente.getPassword())) {
                return ResponseEntity.badRequest()
                        .body("Password inválida: debe tener al menos 8 caracteres, una mayúscula y un número.");
            }

            Cliente creado = clienteService.crear(cliente);
            return ResponseEntity.ok(creado);

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Error de datos: RUT o correo ya registrados, o comuna inválida.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body("Error interno al registrar cliente: " + e.getMessage());
        }
    }

    @PostMapping("/registro/empleado")
    public ResponseEntity<?> registroEmpleado(@Valid @RequestBody Empleado empleado) {
        try {
            if (!ValidacionPassword.esValido(empleado.getPassword())) {
                return ResponseEntity.badRequest()
                        .body("Password inválida: debe tener al menos 8 caracteres, una mayúscula y un número.");
            }

            Empleado creado = empleadoService.crear(empleado);
            return ResponseEntity.ok(creado);

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Error de datos: RUT o correo ya registrados, o administrador inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body("Error interno al registrar empleado: " + e.getMessage());
        }
    }

    @PostMapping("/registro/admin")
    public ResponseEntity<?> registroAdmin(@Valid @RequestBody Administrador admin) {
        try {
            if (!ValidacionPassword.esValido(admin.getPassword())) {
                return ResponseEntity.badRequest()
                        .body("Password inválida: debe tener al menos 8 caracteres, una mayúscula y un número.");
            }

            Administrador creado = adminService.crear(admin);
            return ResponseEntity.ok(creado);

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Error de datos: RUT o correo ya registrados.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body("Error interno al registrar admin: " + e.getMessage());
        }
    }
}
