package com.escrims.domain.valueobjects;

public class SteamProveedor implements OAuthProveedor {

    @Override public String getNombre()      { return "Steam"; }
    @Override public String getAuthUrlBase() { return "https://steamcommunity.com/openid"; }
}
