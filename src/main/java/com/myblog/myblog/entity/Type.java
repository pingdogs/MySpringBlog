package com.myblog.myblog.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by limi on 2017/10/14.
 */
@Entity
@Table(name = "type")
public class Type implements Serializable{

    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
