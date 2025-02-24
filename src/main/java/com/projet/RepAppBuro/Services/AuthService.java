package com.projet.RepAppBuro.Services;

import com.projet.RepAppBuro.DTO.ChangePasswordDto;
import com.projet.RepAppBuro.DTO.ReqRes;
import com.projet.RepAppBuro.DTO.UserDetailsDto;
import com.projet.RepAppBuro.Entity.RSC;
import com.projet.RepAppBuro.Entity.Role;
import com.projet.RepAppBuro.Entity.Technicien;
import com.projet.RepAppBuro.Entity.Utilisateur;
import com.projet.RepAppBuro.Repository.UtilisateurRepository;
import com.projet.RepAppBuro.Utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UtilisateurService utilisateurService;

    public ReqRes signUpTechnicien(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            Technicien utilisateur = new Technicien();
            utilisateur.setNom(registrationRequest.getNom());
            utilisateur.setPrenom(registrationRequest.getPrenom());
            utilisateur.setEmail(registrationRequest.getEmail());
            utilisateur.setMotDePasse(passwordEncoder.encode(registrationRequest.getPrenom()+"123"));
            utilisateur.setTelephone(registrationRequest.getTelephone());
            utilisateur.setRole(Role.TECHNICIEN);
            utilisateur.setSpecialisation(registrationRequest.getSpecialisation());
            utilisateur.setExperience(registrationRequest.getExperience());
            Utilisateur savedUser = utilisateurRepository.save(utilisateur);
            if (savedUser.getId() > 0) {
                resp.setMessage("Technicien Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signUpRsc(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            RSC utilisateur = new RSC();
            utilisateur.setNom(registrationRequest.getNom());
            utilisateur.setPrenom(registrationRequest.getPrenom());
            utilisateur.setEmail(registrationRequest.getEmail());
            utilisateur.setMotDePasse(passwordEncoder.encode(registrationRequest.getPrenom()+"123"));
            utilisateur.setTelephone(registrationRequest.getTelephone());
            utilisateur.setRole(Role.RSC);
            utilisateur.setFonction(registrationRequest.getFonction());
            utilisateur.setExperience(registrationRequest.getExperience());
            Utilisateur savedUser = utilisateurRepository.save(utilisateur);
            if (savedUser.getId() > 0) {
                resp.setMessage("RSC Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(ReqRes signinRequest) {
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            Utilisateur user = utilisateurRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole().toString());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setIdUser(user.getId());
            response.setMessage("Successfully Signed In");
            response.setNom(user.getNom()+" "+user.getPrenom());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError("error in the signin process");
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest) {
        ReqRes response = new ReqRes();
        try {
            String email = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            Utilisateur user = utilisateurRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
                var jwt = jwtUtils.generateToken(user);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public Utilisateur changePassword(ChangePasswordDto details) {
        Utilisateur user = utilisateurService.getUtilisateurById(details.getUserId());
        if (!passwordEncoder.matches(details.getOldPassword(), user.getMotDePasse())) {
            throw new IllegalArgumentException("L'ancien mot de passe est incorrect.");
        }
        if (passwordEncoder.matches(details.getNewPassword(), user.getMotDePasse())) {
            throw new IllegalArgumentException("Le nouveau mot de passe ne peut pas être identique à l'ancien.");
        }
        String hashedNewPassword = passwordEncoder.encode(details.getNewPassword());
        user.setMotDePasse(hashedNewPassword);
        return utilisateurRepository.save(user);
    }

    public void updateUserDetails(UserDetailsDto details) {
        Utilisateur user = utilisateurService.getUtilisateurById(details.getUserId());
        user.setNom(details.getNom());
        user.setPrenom(details.getPrenom());
        user.setEmail(details.getEmail());
        user.setTelephone(details.getTelephone());
        utilisateurRepository.save(user);
    }


}
