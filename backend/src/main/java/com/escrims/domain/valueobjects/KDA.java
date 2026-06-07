package com.escrims.domain.valueobjects;

import java.util.Objects;

public final class KDA {

    private final int kills;
    private final int deaths;
    private final int assists;

    public KDA(int kills, int deaths, int assists) {
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
    }

    public double calcularRatio() {
        if (deaths == 0) return kills + assists;
        return (double) (kills + assists) / deaths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KDA)) return false;
        KDA kda = (KDA) o;
        return kills == kda.kills && deaths == kda.deaths && assists == kda.assists;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kills, deaths, assists);
    }

    public int getKills() { return kills; }
    public int getDeaths() { return deaths; }
    public int getAssists() { return assists; }

    @Override
    public String toString() {
        return kills + "/" + deaths + "/" + assists;
    }
}
