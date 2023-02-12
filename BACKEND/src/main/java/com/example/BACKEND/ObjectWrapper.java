package com.example.BACKEND;

public class ObjectWrapper <S,T>{

    private Integer status;
    private S info;
    private T object;

    public ObjectWrapper() {
    }

    public ObjectWrapper(Integer status, S info, T object) {
        this.status = status;
        this.info = info;
        this.object = object;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public S getInfo() {
        return info;
    }

    public void setInfo(S info) {
        this.info = info;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
