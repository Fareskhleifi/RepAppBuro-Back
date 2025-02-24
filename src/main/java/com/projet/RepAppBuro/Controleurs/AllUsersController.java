package com.projet.RepAppBuro.Controleurs;

import com.projet.RepAppBuro.DTO.ChangePasswordDto;
import com.projet.RepAppBuro.DTO.UserDetailsDto;
import com.projet.RepAppBuro.Entity.Utilisateur;
import com.projet.RepAppBuro.Services.AuthService;
import com.projet.RepAppBuro.Services.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/AllUsers")
@RequiredArgsConstructor
public class AllUsersController {

    private final AuthService authService;
    private final UtilisateurService utilisateurService;

    @PostMapping("/changerMotDePasse")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto details) {
        try {
            authService.changePassword(details);
            return ResponseEntity.ok("Mot de passe changé avec succès !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne. Veuillez réessayer.");
        }
    }

    @PostMapping("/updateUserDetails")
    public ResponseEntity<String> updateUserDetails(@RequestBody UserDetailsDto details) {
        try {
            authService.updateUserDetails(details);
            return ResponseEntity.ok("Les informations ont été mises à jour avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne. Veuillez réessayer.");
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.getUtilisateurById(id));
    }
}
