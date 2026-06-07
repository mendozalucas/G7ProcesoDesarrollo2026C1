package com.escrims.domain.valueobjects;

public class DiscordProveedor implements OAuthProveedor {

    @Override public String getNombre()      { return "Discord"; }
    @Override public String getAuthUrlBase() { return "https://discord.com/api/oauth2/authorize"; }
}
