package com.prototipo.infrastructure.persistence.db.adapter;

import com.prototipo.application.port.out.CredencialAbstract;
import com.prototipo.domain.model.Credencial;
import com.prototipo.infrastructure.persistence.db.entity.CredencialEntity;
import com.prototipo.infrastructure.persistence.db.repository.CredencialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CredencialImpl implements CredencialAbstract, UserDetailsService {

    @Autowired
    private CredencialRepository credencialRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Credencial guardarCredencialAbstract(Credencial nuevaCred) {
        nuevaCred.setPass(passwordEncoder.encode(nuevaCred.getPass()));
        CredencialEntity credencialEntity = modelMapper.map(nuevaCred, CredencialEntity.class);
        CredencialEntity credencialEntityResp = credencialRepository.save(credencialEntity);
        return modelMapper.map(credencialEntityResp, Credencial.class);
    }

    @Override
    public Credencial encontrarCredencial(String ci, String pass) {
        CredencialEntity credEnty = credencialRepository.encontrarCredencial(ci, pass);
        return modelMapper.map(credEnty, Credencial.class);
    }

    @Override
    public Credencial encontrarCredencialPorUsuarioId(Long idUsuario) {
        CredencialEntity credencialEntity = credencialRepository
                .encontrarCredencialPorUsuarioId(idUsuario);
        return (credencialEntity != null)?
                modelMapper.map(credencialEntity, Credencial.class):
                null;
    }

    @Override
    public Credencial encontrarCredencialByCi(String ci) {
        CredencialEntity credEnty = credencialRepository.encontrarCredPorCi(ci);
        return modelMapper.map(credEnty, Credencial.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        CredencialEntity credencialEntity = credencialRepository.encontrarCredPorCi(username);
        // Si no se encuentra el usuario, lanzar una excepción
        if (credencialEntity == null) {
            throw new UsernameNotFoundException("Usuario con CI " + username + " no encontrado");
        }

        // Convertir tu UserEntity a UserDetails
        return User.withUsername(credencialEntity.getCi())
                .password(credencialEntity.getPass())
                //.roles("USER") // Puedes omitir los roles si no los necesitas
                .build();
    }
}
