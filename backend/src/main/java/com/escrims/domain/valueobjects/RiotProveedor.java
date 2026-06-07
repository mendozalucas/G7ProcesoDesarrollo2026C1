package com.escrims.domain.valueobjects;

public class RiotProveedor implements OAuthProveedor {

    @Override public String getNombre()      { return "Riot"; }
    @Override public String getAuthUrlBase() { return "https://auth.riotgames.com"; }
}
