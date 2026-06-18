package com.escrims.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "escrims.matchmaking")
public class MatchmakingProperties {

    /** BY_MMR | BY_LATENCY | BY_HISTORY */
    private String strategy = "BY_MMR";
    private int diferenciaMmrMaxima = 200;
    private int latenciaMaximaMs = 80;
    private double pesoMmr = 0.4;
    private double pesoLatencia = 0.3;
    private double pesoHistorial = 0.3;

    public String getStrategy() { return strategy; }
    public void setStrategy(String strategy) { this.strategy = strategy; }
    public int getDiferenciaMmrMaxima() { return diferenciaMmrMaxima; }
    public void setDiferenciaMmrMaxima(int diferenciaMmrMaxima) { this.diferenciaMmrMaxima = diferenciaMmrMaxima; }
    public int getLatenciaMaximaMs() { return latenciaMaximaMs; }
    public void setLatenciaMaximaMs(int latenciaMaximaMs) { this.latenciaMaximaMs = latenciaMaximaMs; }
    public double getPesoMmr() { return pesoMmr; }
    public void setPesoMmr(double pesoMmr) { this.pesoMmr = pesoMmr; }
    public double getPesoLatencia() { return pesoLatencia; }
    public void setPesoLatencia(double pesoLatencia) { this.pesoLatencia = pesoLatencia; }
    public double getPesoHistorial() { return pesoHistorial; }
    public void setPesoHistorial(double pesoHistorial) { this.pesoHistorial = pesoHistorial; }
}
