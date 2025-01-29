package com.example.logowanie;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class LanguageManager {
    private final BooleanProperty isEnglish = new SimpleBooleanProperty(true);

    public BooleanProperty isEnglishProperty() {
        return isEnglish;
    }

    public boolean isEnglish() {
        return isEnglish.get();
    }

    public void setLanguage(boolean isEnglish) {
        this.isEnglish.set(isEnglish);
    }

    public String getText(String key) {
        String text;
        switch (key) {
            case "add.title":
                text = isEnglish.get() ? "Title" : "Tytuł";
                break;
            case "add.add":
                text = isEnglish.get() ? "Add" : "Dodaj";
                break;
            case "add.cancel":
                text = isEnglish.get() ? "Cancel" : "Anuluj";
                break;
            case "add.fname":
                text = isEnglish.get() ? "First Name" : "Imię";
                break;
            case "add.lname":
                text = isEnglish.get() ? "Last Name" : "Nazwisko";
                break;
            case "add.pseudo":
                text = isEnglish.get() ? "Nickname" : "Pseudonim";
                break;
            case "add.data":
                text = isEnglish.get() ? "Date (YYYY-MM-DD)" : "Data dodania (YYYY-MM-DD)";
                break;
            case "show.prev":
                text = isEnglish.get() ? "Prev" : "Cofnij";
                break;
            case "show.next":
                text = isEnglish.get() ? "Next" : "Dalej";
                break;
            case "menu.utwory":
                text = isEnglish.get() ? "Songs" : "Utwory";
                break;
            case "menu.regulations":
                text = isEnglish.get() ? "Regulations" : "Regulamin";
                break;
            case "menu.regulations.error":
                text = isEnglish.get() ? "Unable to load regulations file." : "Nie można załadować pliku regulaminu.";
                break;
            case "menu.language":
                text = isEnglish.get() ? "Language" : "Język";
                break;
            case "menu.theme":
                text = isEnglish.get() ? "Theme" : "Motyw";
                break;
            case "menu.biggest.hits":
                text = isEnglish.get() ? "Biggest Hits" : "Największe Hity";
                break;
            case "menu.add.song":
                text = isEnglish.get() ? "Add new Song" : "Dodaj nowy Utwór";
                break;
            case "menu.remove.song":
                text = isEnglish.get() ? "Remove Song" : "Usuń Utwór";
                break;
            case "menu.create.album":
                text = isEnglish.get() ? "Create Album" : "Utwórz Album";
                break;
            case "menu.song.list":
                text = isEnglish.get() ? "Song List" : "Lista Utworów";
                break;
            case "menu.cancel":
                text = isEnglish.get() ? "Close" : "Zamknij";
                break;
            case "menu.select.option":
                text = isEnglish.get() ? "Select an Option" : "Wybierz Opcję";
                break;
            case "menu.enter.song.name":
                text = isEnglish.get() ? "Enter the song name:" : "Wpisz nazwę utworu:";
                break;
            case "menu.enter.song.name.to.remove":
                text = isEnglish.get() ? "Enter the name of the song to remove:" : "Wpisz nazwę utworu do usunięcia:";
                break;
            case "menu.enter.album.name":
                text = isEnglish.get() ? "Enter the album name:" : "Wpisz nazwę albumu:";
                break;
            case "menu.song.added":
                text = isEnglish.get() ? "Song added" : "Utwór dodany";
                break;
            case "menu.song.removed":
                text = isEnglish.get() ? "Song removed" : "Utwór usunięty";
                break;
            case "menu.album.created":
                text = isEnglish.get() ? "Album created" : "Album utworzony";
                break;
            case "menu.song.list.display":
                text = isEnglish.get() ? "Displaying the list of songs" : "Wyświetlanie listy utworów";
                break;
            case "theme.light":
                text = isEnglish.get() ? "Light" : "Jasny";
                break;
            case "theme.dark":
                text = isEnglish.get() ? "Dark" : "Ciemny";
                break;
            default:
                text = key;
        }
        return text;
    }
}