package com.prueba.saas.controller;

import com.prueba.saas.model.Incidencia;
import com.prueba.saas.model.Incidencia.Criticidad;
import com.prueba.saas.model.Incidencia.Estado;
import com.prueba.saas.services.IncidenciaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IncidenciaController {
    private final IncidenciaService servicio;

    public IncidenciaController(IncidenciaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/")
    public String inicio(Model modelo) {
        modelo.addAttribute("incidencias", servicio.listar());
        modelo.addAttribute("criticidades", Criticidad.values());
        modelo.addAttribute("estados", Estado.values());
        return "index";
    }

    @PostMapping("/incidencias")
    public String registrar(
            @RequestParam("titulo") String titulo,
            @RequestParam("integracion") String integracion,
            @RequestParam("impacto") String impacto,
            @RequestParam("criticidad") Criticidad criticidad,
            RedirectAttributes mensajes) {
        titulo = titulo.strip();
        integracion = integracion.strip();
        impacto = impacto.strip();

        if (titulo.isEmpty() || titulo.length() > 120
                || integracion.isEmpty() || integracion.length() > 120
                || impacto.isEmpty() || impacto.length() > 1000) {
            mensajes.addFlashAttribute("error", "Completa el título, la integración y el impacto dentro de los límites del formulario.");
            mensajes.addFlashAttribute("titulo", titulo);
            mensajes.addFlashAttribute("integracion", integracion);
            mensajes.addFlashAttribute("impacto", impacto);
            mensajes.addFlashAttribute("criticidadElegida", criticidad);
            return "redirect:/";
        }

        Incidencia incidencia = servicio.registrar(titulo, integracion, impacto, criticidad);
        mensajes.addFlashAttribute("mensaje", "Incidencia #" + incidencia.getId() + " registrada correctamente.");
        return "redirect:/#resultado";
    }

    @PostMapping("/incidencias/{id}/estado")
    public String cambiarEstado(@PathVariable("id") long id, @RequestParam("estado") Estado estado,
                                RedirectAttributes mensajes) {
        servicio.cambiarEstado(id, estado);
        mensajes.addFlashAttribute("mensaje", "Estado de la incidencia #" + id + " actualizado.");
        return "redirect:/#resultado";
    }

    @PostMapping("/incidencias/{id}/eliminar")
    public String eliminar(@PathVariable("id") long id, RedirectAttributes mensajes) {
        servicio.eliminar(id);
        mensajes.addFlashAttribute("mensaje", "Incidencia #" + id + " eliminada.");
        return "redirect:/#resultado";
    }
}
