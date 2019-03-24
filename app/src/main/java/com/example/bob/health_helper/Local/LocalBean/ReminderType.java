package com.example.bob.health_helper.Local.LocalBean;

public class ReminderType {
    private String text;
    private boolean checked;

    public ReminderType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
