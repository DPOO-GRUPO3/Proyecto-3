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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.BaseDatos;
import model.Alquiler;
import model.Cliente;
import model.Licencia;
import model.Reserva;
import model.Tarjeta;
import model.Temporada;

public class TemporadaTest {
    private static final String TEST_FILE_PATH = "data/temporadas.txt";
    private static final String BACKUP_FILE_PATH = "data/backr.txt";

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
    public void testAlquilerToFile() throws IOException {
    	BaseDatos datos =new BaseDatos();
    	datos.descargarTodoslosDatos();
    	
    	Temporada t1=datos.getMapaTemporadas().get("29");
    	Temporada t2=datos.getMapaTemporadas().get("30");
    	datos.getMapaTemporadas().clear();
    	
    	datos.getMapaTemporadas().put("29", t1);
    	datos.getMapaTemporadas().put("30", t2);
    	datos.actualizarArchivoTemporadas();
    	
    	
    	//Assert
    	String expectedText="29;2023-12-31T00:00;2025-12-31T23:59:59;2000.0;familiares\n"+
    			"30;2023-12-31T00:00;2025-12-31T23:59:59;7000.0;todoterreno\n";
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
