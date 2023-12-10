package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.sampled.TargetDataLine;

public class Sire extends Pago {

	public Sire(String numero, String codigo) {
		super(numero, codigo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void CargarDatos() throws IOException {
		
		String numero= super.getNumero();
		
		FileWriter fichero = new FileWriter("data/Sire.txt");
		fichero.write(numero +";APROBADA\n");
		fichero.close();
		
	}

}
