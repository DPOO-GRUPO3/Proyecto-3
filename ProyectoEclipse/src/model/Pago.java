package model;

import java.io.IOException;

public abstract class Pago {
private String numero;
private String codigo;
private boolean bloqueada;
private Cliente cliente;


public Pago(String numero,String codigo) {
	this.bloqueada=false;
	this.codigo=codigo;
	this.numero=numero;

}
public void bloquear() {
	bloqueada=true;
}
public void desbloquear() {
	bloqueada =false;
}
public String getCodigo() {
	return codigo;
}
public String getNumero() {
	return numero;
}

public boolean getBloqueo() {
	return bloqueada;
}

// setters
public void setCliente (Cliente cliente)
{
	this.cliente=cliente; 
}

//Metódos abstractos para cargar datos

public abstract void CargarDatos() throws IOException;


}