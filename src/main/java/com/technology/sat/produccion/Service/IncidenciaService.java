package com.technology.sat.produccion.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.technology.sat.produccion.Class.Incidencia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class IncidenciaService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(IncidenciaService.class);

    @Autowired
    public IncidenciaService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void procesarIncidencia(Incidencia incidencia) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(incidencia.getEmail()); 
        mensaje.setTo("sattechnologyhelpdesk@gmail.com"); // correo de soporte
        mensaje.setSubject("Nueva Incidencia Reportada");

        String cuerpoMensaje = String.format(
                "Nombre: %s\nEmpresa: %s\nEmail: %s\nTeléfono: %s\n\nDescripción de la Incidencia:\n%s",
                incidencia.getNombre(),
                incidencia.getEmpresa(),
                incidencia.getEmail(),
                incidencia.getTelefono(),
                incidencia.getMensaje());

        mensaje.setText(cuerpoMensaje);

        try {
            logger.info("Enviando incidencia de {} a soporte", incidencia.getEmail());
            mailSender.send(mensaje);
            logger.info("Incidencia enviada exitosamente desde {}", incidencia.getEmail());
        } catch (Exception e) {
            logger.error("Error al enviar la incidencia: {}", e.getMessage(), e);
        }
    }
}