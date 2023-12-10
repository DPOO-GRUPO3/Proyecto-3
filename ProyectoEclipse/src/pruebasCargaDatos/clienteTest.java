package pruebasCargaDatos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.BaseDatos;
import model.Cliente;
import model.Licencia;
import model.Tarjeta;

public class clienteTest {
    private static final String TEST_FILE_PATH = "data/clientes.txt";
    private static final String BACKUP_FILE_PATH = "data/backc.txt";

    @BeforeAll
    public static void setup() throws IOException {
        // Hacer una copia del archivo original antes de la prueba
        copyFile(TEST_FILE_PATH, BACKUP_FILE_PATH);
    }

    @AfterAll
    public static void cleanup() throws IOException {
        // Restaurar el archivo original después de la prueba
        copyFile(BACKUP_FILE_PATH, TEST_FILE_PATH);
        // Eliminar el archivo de respaldo
        new File(BACKUP_FILE_PATH).delete();
    }

    @Test
    public void testLicenciaToFile() throws IOException {
    	BaseDatos datos =new BaseDatos();
    	Licencia lic1= new Licencia("1","USA","10/26","www.mariosanchez");
    	Licencia lic2= new Licencia("2","Peru","9/26","www.josue");
    	datos.getMapaLicencias().put("1", lic1);
    	datos.getMapaLicencias().put("2", lic2);
    	
    	Tarjeta tar1=new Tarjeta("42", "238");
    	Tarjeta tar2=new Tarjeta("43","555");
    	tar2.bloquear();
    	datos.getMapaTarjetas().put("42", tar1);
    	datos.getMapaTarjetas().put("43", tar2);
    	
    	Cliente user1 =new Cliente("mario23","dpoo","Mario","@uniandes",
    			"mymar","www.mariobros");
    	
    	Cliente user2 =new Cliente("josue23","dpoo","Josue","@uniandes",
    			"Peru","www.monitor");
    	
    	user1.setLicencia(lic1);
    	user1.setTarjeta(tar1);
    	user2.setLicencia(lic2);
    	user2.setTarjeta(tar2);
    	
    	datos.getMapaClientes().put("mario23", user1);
    	datos.getMapaClientes().put("josue23", user2);
    	
    	datos.actualizarArchivoClientes();
    	
    	//Assert
    	String expectedText="mario23;dpoo;@uniandes;mymar;Mario;www.mariobros;1;42\n"
    			+"josue23;dpoo;@uniandes;Peru;Josue;www.monitor;2;43\n";
    	String fileContent=readFile(TEST_FILE_PATH);
    	assertEquals(expectedText, fileContent);
    }

    private static void copyFile(String sourcePath, String destinationPath) throws IOException {
        File source = new File(sourcePath);
        File destination = new File(destinationPath);

        // Copiar el archivo
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }


    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}