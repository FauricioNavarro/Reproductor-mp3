package com.example.fauricio.reproducotor_mp3;

/**
 * Created by fauricio on 12/03/18.
 */

public class Item {
    private int image;
    private String titulo;
    private String contenido;

    public Item(int image, String titulo, String contenido) {
        this.image = image;
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
