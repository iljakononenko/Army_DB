package org.iljaknk;

public class Soldiers {
    private int id;
    private String first_name;
    private String last_name;
    private String rank;

    public Soldiers(int id, String first_name, String last_name, String rank)
    {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRank() {
        return rank;
    }
}
