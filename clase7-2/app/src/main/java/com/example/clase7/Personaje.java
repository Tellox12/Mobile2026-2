package com.example.clase7;

public class Personaje {

    String name;
    String desc;
    String photo;
    int attack;
    int def;

    public Personaje(String name, String desc, String photo, int attack, int def) {
        this.name = name;
        this.desc = desc;
        this.photo = photo;
        this.attack = attack;
        this.def = def;
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

    public int getAttack() {
        return attack;
    }

    public void setAtack(int attack) {
        this.attack = attack;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }
}
