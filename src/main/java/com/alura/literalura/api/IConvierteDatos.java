package com.alura.literalura.api;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}