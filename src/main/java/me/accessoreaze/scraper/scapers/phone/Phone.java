package me.accessoreaze.scraper.scapers.phone;

public class Phone {

    private String name, model;

    public Phone(String name, String model) {
        this.name = name;
        this.model = model;
        System.out.println(name + ", " + model);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
