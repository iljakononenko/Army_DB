package org.iljaknk;

public class Soldiers {
    private int id;
    private String first_name;
    private String last_name;
    private String rank;
    private String armour;
    private String weapon;
    private String salary;

    private int hours;
    private int squad;

    public Soldiers(int id, String first_name, String last_name, String rank, String armour, String weapon)
    {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.rank = rank;
        this.armour = armour;
        this.weapon = weapon;
    }

    public Soldiers(int id, String first_name, String last_name, int hours, String salary)
    {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.hours = hours;
        this.salary = salary;
    }

    public Soldiers(int id, int squad, String first_name, String last_name)
    {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.squad = squad;
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

    public int getHours() {
        return hours;
    }

    public int getSquad() {
        return squad;
    }

    public String getArmour() {
        return armour;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getSalary() {
        return salary;
    }
}
