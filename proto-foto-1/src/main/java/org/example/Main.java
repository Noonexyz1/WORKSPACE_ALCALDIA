package org.example;

import org.example.application.imp.AdministradorImp;
import org.example.application.port.AdministradorPersist;
import org.example.application.usecase.AdministradorService;
import org.example.domain.model.Usuario;
import org.example.infrastructure.memory.PersistenceMemory;

public class Main {
    public static void main(String[] args) {
        Usuario usuario = new Usuario("Diego", "adsfasdf");

        AdministradorPersist adminPersist = new PersistenceMemory();
        AdministradorService administradorService = new AdministradorImp(adminPersist);
        administradorService.crearUsuario(usuario);

        adminPersist.listarUsuarios().forEach(Usuario::toString);
    }
}
