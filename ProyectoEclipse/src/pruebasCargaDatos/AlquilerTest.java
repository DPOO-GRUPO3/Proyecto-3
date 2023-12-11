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

public class AlquilerTest {
    private static final String TEST_FILE_PATH = "data/alquileres.txt";
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
    	datos.getMapaAlquileres().clear();
    	Alquiler.setNumAlqus(0);
    	
		List<Cliente> userList = new ArrayList<Cliente>(datos.getMapaClientes().values());
    	Cliente cli1=datos.getMapaClientes().get("samisanti2011");
    	Cliente cli2=datos.getMapaClientes().get("daniela");
    	
    	
    	
    	Alquiler res1=new Alquiler(cli1,LocalDateTime.parse("2024-02-10T00:00"),
    			LocalDateTime.parse("2024-02-01T00:00"),
    			datos.getMapaSedes().get("sur"),
    			datos.getMapaSedes().get("sur"),
    			datos.getMapaCarros().get("ABC123"));
    	
    	res1.setTarifa(datos.getMapaTemporadas().get("29"));
    	Alquiler res2=new Alquiler(cli2,LocalDateTime.parse("2024-03-10T00:00"),
    			LocalDateTime.parse("2024-03-01T00:00"),
    			datos.getMapaSedes().get("sur"),
    			datos.getMapaSedes().get("sur"),
    			datos.getMapaCarros().get("ABC123"));
    	res2.setTarifa(datos.getMapaTemporadas().get("29"));
    	String id1=res1.getAlquileresId();
    	String id2=res2.getAlquileresId();
    	
    	datos.getMapaAlquileres().put(id1, res1);
    	datos.getMapaAlquileres().put(id2, res2);
    	
    	
    	
    	datos.actualizarArchivoAlquileres();
    	
    	//Assert
    	String expectedText="1;sur;sur;ABC123;2024-02-10T00:00;2024-02-01T00:00;samisanti2011;null;29;null\n"+
    			"2;sur;sur;ABC123;2024-03-10T00:00;2024-03-01T00:00;daniela;null;29;null\n";
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
