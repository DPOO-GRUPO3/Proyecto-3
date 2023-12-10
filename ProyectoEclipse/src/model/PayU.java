package model;

import java.io.FileWriter;
import java.io.IOException;

public class PayU extends Pago {

	public PayU(String numero, String codigo) {
		super(numero, codigo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void CargarDatos() throws IOException {
		String numero= super.getNumero();
		
		FileWriter fichero = new FileWriter("data/PayU.txt",true);
		fichero.write(numero +";APROBADA\n");
		fichero.close();
		
	}
	
	

}
