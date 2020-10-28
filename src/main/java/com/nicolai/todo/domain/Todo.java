package com.nicolai.todo.domain;

import java.util.Date;

public class Todo {
    private int id;
    private String todo;
    private Date frist;
    private boolean done;
    private String username;

    public Todo(int id, String todo, Date frist, boolean done, String username) {
        this.id = id;
        this.todo = todo;
        this.frist = frist;
        this.done = done;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Date getFrist() {
        return frist;
    }

    public void setFrist(Date frist) {
        this.frist = frist;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
