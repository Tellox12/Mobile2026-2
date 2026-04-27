package com.example.clase7;

public class Personaje implements java.io.Serializable {

    String name;
    String desc;
    String photo;
    int ataque;
    int defensa;

    public Personaje(String name, String desc, String photo, int ataque, int defensa) {
        this.name = name;
        this.desc = desc;
        this.photo = photo;
        this.ataque = ataque;
        this.defensa = defensa;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }
}
