package com.paradox.projectsp3.Model;

public class SoundModel {
    private int sound_img;
    private String sound_sound,sound_title;
    public SoundModel()
    {
        /////
    }

    public SoundModel(int sound_img,String sound_sound,String sound_title)
    {
        this.sound_img=sound_img;
        this.sound_sound=sound_sound;
        this.sound_title=sound_title;
    }

    public String getSound_sound() {
        return sound_sound;
    }

    public int getSound_img() {
        return sound_img;
    }

    public String getSound_title() {
        return sound_title;
    }

    public void setSound_sound(String sound_sound) {
        this.sound_sound = sound_sound;
    }

    public void setSound_img(int sound_img) {
        this.sound_img = sound_img;
    }

    public void setSound_title(String sound_title) {
        this.sound_title = sound_title;
    }


}
