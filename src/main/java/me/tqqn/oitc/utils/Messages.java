package me.tqqn.oitc.utils;

public enum Messages {

    NO_PERMISSION(Color.translateColor("&cYou have currently no permission to use this command."),"", "", ""),
    GAME_START_COUNTDOWN(Color.translateColor("&cGame is starting in &f%%SECONDS%%"),"%%SECONDS%%", "", ""),
    GAME_START(Color.translateColor("&cGame started!"),"", "", ""),
    PLAYER_JOIN(Color.translateColor("&f%%JOINED_PLAYER_COUNT%%&c/&f%%PLAYERS_IN_ARENA_COUNT%% &f%%PLAYER%% &6has joined the game."),"%%JOINED_PLAYER_COUNT%%", "%%PLAYERS_IN_ARENA_COUNT%%", "%%PLAYER%%"),
    PLAYER_LEAVE(Color.translateColor("&f%%PLAYER%% &6has left the game."),"%%PLAYER%%", "", ""),
    GAME_END_COUNTDOWN(Color.translateColor("&cGame is ending in &f%%SECONDS%%"),"%%SECONDS%%", "", ""),
    KICK_PLAYER_ON_ENDGAME(Color.translateColor("&cGame is restarting!"),"", "", ""),
    GLOBAL_PLAYER_KILL_MESSAGE(Color.translateColor("&f%%DEATH_PLAYER%% &6has been shot by &f%%KILLER%%"), "%%DEATH_PLAYER%%","%%KILLER%%", ""),
    WIN_MESSAGE(Color.translateColor("&f%%WINNER%% &6has won the game!"), "%%WINNER%%","",""),
    GAME_END_COUNTDOWN_MESSAGE(Color.translateColor("&cGame resets in &f%%SECONDS%%&f!"), "%%SECONDS%%","", ""),
    GAME_END_MESSAGE(Color.translateColor("&cGame has ended!"),"","", ""),
    KICK_PLAYER_WHEN_ARENA_FULL(Color.translateColor("&cThis game is full."),"", "", ""),
    SETUP_MENU_NAME(Color.translateColor("&cSetup-Menu"), "", "", ""),
    OPEN_SETUP_MENU(Color.translateColor("&cYou opened the &lSetup Menu &r&c."),"","",""),
    CLOSE_SETUP_MENU(Color.translateColor("&cYou closed the &lSetup Menu &r&c."),"","",""),
    ENTER_SETUP_MODE(Color.translateColor("&cYou entered the setup mode."), "", "", ""),
    EXIT_SETUP_MODE(Color.translateColor("&cYou exited the setup mode."), "", "", ""),
    KICK_PLAYER_WHEN_SETUP_MODE(Color.translateColor("&cServer is in setup mode."), "", "", ""),
    SAVE_SETUP_MODE(Color.translateColor("&cYou saved the selected locations."),"", "", ""),
    SELECT_LOBBY_LOCATION_SETUP_MODE(Color.translateColor("&cSelected lobby location."), "", "", ""),
    SELECT_ARENA_LOCATION_SETUP_MODE(Color.translateColor("&cSelected arena location."), "", "", ""),
    NO_LOBBY_LOCATION_SETUP_MODE(Color.translateColor("&cLobby location is null. Please select a location."),"", "", ""),
    REMOVE_SETUP_ITEMS_FROM_PLAYER(Color.translateColor("&cRemoved setup items from &f&l%%PLAYER%%"), "%%PLAYER%%","",""),
    NO_MODE_SELECTED(Color.translateColor("&cNo selected mode found in config."), "","",""),
    GIVE_ITEM(Color.translateColor("&cYou have received the One In Chamber items."),"", "", "");


    private final String message;
    private final String placeholder1;
    private final String placeholder2;
    private final String placeholder3;

    Messages(String message, String placeholder1, String placeholder2, String placeholder3) {
        this.message = message;
        this.placeholder1 = placeholder1;
        this.placeholder2 = placeholder2;
        this.placeholder3 = placeholder3;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(String replacePlaceHolderArgs1) {
        return message.replace(this.placeholder1, replacePlaceHolderArgs1);
    }
    public String getMessage(String replacePlaceHolderArgs1, String replacePlaceHolderArgs2) {
        return message.replace(this.placeholder1, replacePlaceHolderArgs1).replace(this.placeholder2, replacePlaceHolderArgs2);
    }

    public String getMessage(String replacePlaceHolderArgs1, String replacePlaceHolderArgs2, String replacePlaceHolderArgs3) {
        return message.replace(this.placeholder1, replacePlaceHolderArgs1).replace(this.placeholder2, replacePlaceHolderArgs2).replace(this.placeholder3, replacePlaceHolderArgs3);
    }
}
