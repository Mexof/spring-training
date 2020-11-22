package ru.mexof.spring.training.webflux.infrastructure.persistense;

import java.util.Objects;

public class Genre {
    private String type;

    public Genre(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(type, genre.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "type='" + type + '\'' +
                '}';
    }
}
