package pruebasCargaDatos;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;

import controller.BaseDatos;
import model.Tarjeta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
import java.io.*;

public class tarjetaTest {
    private static final String TEST_FILE_PATH = "data/tarjetas.txt";
    private static final String BACKUP_FILE_PATH = "data/backuptarjetas.txt";

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
    public void testTarjetaToFile() throws IOException {
    	BaseDatos datos =new BaseDatos();
    	Tarjeta tar1=new Tarjeta("42", "238");
    	Tarjeta tar2=new Tarjeta("43","555");
    	tar2.bloquear();
    	datos.getMapaTarjetas().put("42", tar1);
    	datos.getMapaTarjetas().put("43", tar2);
    	
    	datos.actualizarArchivoTarjetas();
    	
    	//Assert
    	String expectedText="42;238;0\n"+"43;555;1\n";
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






