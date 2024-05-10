package model;

import java.util.Objects;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" + // имя класса
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status'" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        int hash = 17;

        if (name != null) { // проверяем значение первого поля
            hash += name.hashCode(); // вычисляем хеш первого поля
        }

        hash *= 31;

        if (description != null) { // проверяем значение второго поля
            hash += description.hashCode(); // вычисляем хеш второго поля и общий хеш
        }
        return hash;
    }
}
