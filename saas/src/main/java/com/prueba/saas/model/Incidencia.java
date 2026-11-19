package com.prueba.saas.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "incidencias")
public class Incidencia {
    public enum Criticidad {
        BAJA("Baja"), MEDIA("Media"), ALTA("Alta"), CRITICA("Crítica");

        private final String etiqueta;
        Criticidad(String etiqueta) { this.etiqueta = etiqueta; }
        public String getEtiqueta() { return etiqueta; }
    }

    public enum Estado {
        ABIERTA("Abierta"), EN_ATENCION("En atención"), RESUELTA("Resuelta");

        private final String etiqueta;
        Estado(String etiqueta) { this.etiqueta = etiqueta; }
        public String getEtiqueta() { return etiqueta; }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 120)
    private String integracion;

    @Column(nullable = false, length = 1000)
    private String impacto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Criticidad criticidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    @Column(nullable = false, updatable = false)
    private Instant creadaEn;

    protected Incidencia() {}

    public Incidencia(String titulo, String integracion, String impacto, Criticidad criticidad) {
        this.titulo = titulo;
        this.integracion = integracion;
        this.impacto = impacto;
        this.criticidad = criticidad;
        this.estado = Estado.ABIERTA;
        this.creadaEn = Instant.now();
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getIntegracion() { return integracion; }
    public String getImpacto() { return impacto; }
    public Criticidad getCriticidad() { return criticidad; }
    public Estado getEstado() { return estado; }
    public Instant getCreadaEn() { return creadaEn; }

    public void cambiarEstado(Estado estado) {
        this.estado = estado;
    }
}
