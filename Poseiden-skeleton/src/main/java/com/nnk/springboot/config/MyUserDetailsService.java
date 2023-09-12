package com.nnk.springboot.config;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    //J'utilise useRepository plutôt que UserService pour régler un problème de boucle de Beans

    /*Quand l'utilisateur entre son adresse mail, Spring va créer une instance de UserDetail à partir des informations
     présente dans la BDD et vérifier que le mot de passe saisi par l'utilisateur
      correspond bien au mot de passe de UserDetail*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        // TODO: modifier la méthode en fin de projet en fonction des besoins de role:

        //Si besoin d'utiliser plusieurs roles, à supprimer
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        //

        //+ remplacer authorities par "getAuthorities(user.getRoles())"
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

    //Methode qui va servir à convertir une liste de String en liste de GrantedAuthority.
    //Nécessaire pour créer un UserDetails user si un user peut avoir plusieurs roles.
    private static List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}