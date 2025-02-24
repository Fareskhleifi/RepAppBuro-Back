package com.projet.RepAppBuro.Services;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {



    private final JavaMailSender mailSender;

    @Async
    public void sendRepairCompletionEmail(String toEmail, String clientName, String deviceDetails) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("your_email");
            helper.setTo(toEmail);
            helper.setSubject("Votre appareil est prêt !");

            String htmlContent = String.format("""
            <html lang='fr'>
            <head>
                <meta charset='UTF-8'>
                <meta name='viewport' content='width=device-width, initial-scale=1.0'>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background-color: #fff;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                    }
                    .header {
                        background-color: #0F52BA;
                        color: #fff;
                        text-align: center;
                        padding: 20px;
                        font-size: 1.5em;
                        font-weight: bold;
                    }
                    .content {
                        padding: 20px;
                        color: #333;
                        line-height: 1.6;
                    }
                    .content p {
                        margin-bottom: 15px;
                    }
                    .cta {
                        text-align: center;
                        margin-top: 20px;
                    }
                    .cta a {
                        text-decoration: none;
                        padding: 10px 20px;
                        background-color: #0F52BA;
                        color: #fff;
                        border-radius: 5px;
                        font-weight: bold;
                        transition: background-color 0.3s;
                    }
                    .cta a:hover {
                        background-color: #083E8D;
                    }
                    .footer {
                        text-align: center;
                        font-size: 0.9em;
                        color: #888;
                        padding: 10px 20px;
                        background-color: #f1f1f1;
                    }
                    .footer a {
                        color: #4CAF50;
                        text-decoration: none;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">Réparation Terminée</div>
                    <div class="content">
                        <p>Bonjour <strong>%s</strong>,</p>
                        <p>Nous avons le plaisir de vous informer que la réparation de votre appareil <strong>%s</strong> a été terminée avec succès.</p>
                        <p>Vous pouvez venir le récupérer à vfotre convenance. Si vous avez des questions, n'hésitez pas à nous contacter.</p>
                        <div class="cta">
                            <a href="mailto:your_email.com">Contactez-nous</a>
                        </div>
                    </div>
                    <div class="footer">
                        RepAppBuro - <a href="mailto:your_email.com">Contactez-nous</a><br>
                        © 2024 RepAppBuro, Tous droits réservés.
                    </div>
                </div>
            </body>
            </html>
        """, clientName, deviceDetails);

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendRepairIrreparableEmail(String toEmail, String clientName, String deviceDetails) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("your_email.com");
            helper.setTo(toEmail);
            helper.setSubject("Votre appareil est irréparable");

            String htmlContent = String.format("""
            <html lang='fr'>
            <head>
                <meta charset='UTF-8'>
                <meta name='viewport' content='width=device-width, initial-scale=1.0'>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background-color: #fff;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                    }
                    .header {
                        background-color: #D9534F; /* Red to indicate an issue */
                        color: #fff;
                        text-align: center;
                        padding: 20px;
                        font-size: 1.5em;
                        font-weight: bold;
                    }
                    .content {
                        padding: 20px;
                        color: #333;
                        line-height: 1.6;
                    }
                    .content p {
                        margin-bottom: 15px;
                    }
                    .cta {
                        text-align: center;
                        margin-top: 20px;
                    }
                    .cta a {
                        text-decoration: none;
                        padding: 10px 20px;
                        background-color: #D9534F; /* Consistent with the header */
                        color: #fff;
                        border-radius: 5px;
                        font-weight: bold;
                        transition: background-color 0.3s;
                    }
                    .cta a:hover {
                        background-color: #B52D28;
                    }
                    .footer {
                        text-align: center;
                        font-size: 0.9em;
                        color: #888;
                        padding: 10px 20px;
                        background-color: #f1f1f1;
                    }
                    .footer a {
                        color: #4CAF50;
                        text-decoration: none;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">Réparation Impossible</div>
                    <div class="content">
                        <p>Bonjour <strong>%s</strong>,</p>
                        <p>Nous sommes désolés de vous informer que la réparation de votre appareil <strong>%s</strong> est malheureusement irréparable.</p>
                        <p>Vous pouvez venir récupérer votre appareil à tout moment. Si vous avez des questions ou souhaitez plus de détails, n'hésitez pas à nous contacter.</p>
                        <div class="cta">
                            <a href="mailto:your_email.com">Contactez-nous</a>
                        </div>
                    </div>
                    <div class="footer">
                        RepAppBuro - <a href="mailto:your_email.com">Contactez-nous</a><br>
                        © 2024 RepAppBuro, Tous droits réservés.
                    </div>
                </div>
            </body>
            </html>
        """, clientName, deviceDetails);

            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
