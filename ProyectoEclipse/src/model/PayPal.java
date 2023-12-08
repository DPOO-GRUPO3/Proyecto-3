package model;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PayPal extends Pago{

	private static final Logger logger = Logger.getLogger(PayPal.class.getName());
	
	public PayPal(String numero, String codigo) {
		super(numero, codigo);
		// TODO Auto-generated constructor stub
	}
	

    static {
        // Configurar el archivo de registro (log)
        try {
            FileHandler fileHandler = new FileHandler("data/PayPal.txt");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	

	@Override
	public void CargarDatos() {
		String numero= super.getNumero();
		
		logger.info(numero + ";" + "APROBADA");
		
	}
	
}
