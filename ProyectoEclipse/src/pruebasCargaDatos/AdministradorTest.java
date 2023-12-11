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
import model.Administrador;
import model.Alquiler;
import model.Cliente;
import model.Licencia;
import model.Tarjeta;

public class AdministradorTest {
    private static final String TEST_FILE_PATH = "data/administradores.txt";
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
    public void testAdminToFile() throws IOException {
    	BaseDatos datos =new BaseDatos();
    	datos.descargarTodoslosDatos();
    	datos.getMapaAdministradores().clear();
    	
    	Administrador a1= new Administrador("87", "samiss", "123", "Samuel Pena");
    	Administrador a2= new Administrador("88", "josueMonitor", "123", "Josue Rodriguez");
    	a1.setSede(datos.getMapaSedes().get("norte"));
    	a2.setSede(datos.getMapaSedes().get("norte"));
    	datos.getMapaAdministradores().put("87", a1);
    	datos.getMapaAdministradores().put("88", a2);
    	
    	datos.actualizarArchivoAdministradores();
    	
    	//Assert
    	String expectedText="josueMonitor;123;Josue Rodriguez;88;norte\n"
    			+"samiss;123;Samuel Pena;87;norte\n";
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