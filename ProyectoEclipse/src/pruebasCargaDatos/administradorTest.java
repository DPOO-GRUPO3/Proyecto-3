package pruebasCargaDatos;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;

import controller.BaseDatos;
import controller.ControllerAdministrador;
import model.Administrador;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

public class administradorTest {

    private static final String TEST_FILE_EMPLEADOS = "data/empleados.txt";
    private static final String BACKUP_FILE_EMPLEADOS = "backup/testEmpleadosBackup.txt";

    @BeforeAll
    public static void setup() throws IOException {
        // Hacer una copia del archivo original antes de la prueba
        copyFile(TEST_FILE_EMPLEADOS, BACKUP_FILE_EMPLEADOS);
    }

    @AfterAll
    public static void cleanup() throws IOException {
        // Restaurar el archivo original después de la prueba
        copyFile(BACKUP_FILE_EMPLEADOS, TEST_FILE_EMPLEADOS);
        // Eliminar el archivo de respaldo
        new File(BACKUP_FILE_EMPLEADOS).delete();
    }

    @Test
    public void testEliminarLinea() throws IOException {
        // Crear una instancia de ControllerAdministrador
        ControllerAdministrador controller = new ControllerAdministrador();

        // Crear un archivo de prueba con líneas específicas
        crearArchivoPrueba(TEST_FILE_EMPLEADOS, "2;Laura Hurtado;laura;123;@;sur", "3;Sara Martinez;sara;123;@;norte", "1;Juan Gómez;juan;123;@;centro");

        // Llamar al método para eliminar una línea específica
        controller.eliminarlineaEmpleados(TEST_FILE_EMPLEADOS, "3;Sara Martinez;sara;123;@;norte");

        // Verificar si la línea se eliminó correctamente
        assertFalse(contieneLinea(TEST_FILE_EMPLEADOS, "3;Sara Martinez;sara;123;@;norte"));
    }

    @Test
    public void testAgregarLinea() throws IOException {
        // Crear una instancia de ControllerAdministrador
        ControllerAdministrador administrador = new ControllerAdministrador();

        // Crear un archivo de prueba sin la nueva línea
        eliminarLinea(TEST_FILE_EMPLEADOS, "3;Felipe;felipe;123;@;norte");

        // Llamar al método para agregar una nueva línea
        administrador.agregarLineaEmpleados(TEST_FILE_EMPLEADOS, "3;Felipe;felipe;123;@;norte");

        // Verificar si la nueva línea se agregó correctamente
        assertTrue(contieneLinea(TEST_FILE_EMPLEADOS, "3;Felipe;felipe;123;@;norte"));
    }


    @Test
    public void testEliminarLineaEmpleados() throws IOException {
        testEliminarLineaPorContenido("3;Sara Martinez;sara;123;@;norte", ";norte");
    }

    private void testEliminarLineaPorContenido(String contenidoAEliminar, String identificador) throws IOException {
        // Crear una instancia de ControllerAdministrador
        ControllerAdministrador controller = new ControllerAdministrador();

        // Crear un archivo de prueba con líneas específicas
        crearArchivoPrueba(TEST_FILE_EMPLEADOS, "2;Laura Hurtado;laura;123;@;sur", contenidoAEliminar, "1;Juan Gómez;juan;123;@;centro");

        // Llamar al método para eliminar una línea específica por contenido
        controller.eliminarLineaEmpleados(TEST_FILE_EMPLEADOS, identificador);

        // Verificar si la línea se eliminó correctamente
        assertFalse(contieneLinea(TEST_FILE_EMPLEADOS, contenidoAEliminar));
    }

    // Métodos de utilidad para crear, eliminar y verificar archivos
    private void crearArchivoPrueba(String fileName, String... lines) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String line : lines) {
                writer.write(line + System.getProperty("line.separator"));
            }
        }
    }

    private void eliminarLinea(String fileName, String lineToRemove) throws IOException {
        testEliminarLineaPorContenido(lineToRemove, "");
    }

    private boolean contieneLinea(String fileName, String lineToFind) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(lineToFind)) {
                    return true;
                }
            }
        }
        return false;
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
    
    
    
    
    
    
    /*
     * 
     * */
	
    @Test
    public void testLogInCorrecto() throws IOException {
    	
    	BaseDatos datos = new BaseDatos();
    	datos.descargarTodoslosDatos();

        // Utilizar objetos reales
        ControllerAdministrador administrador = new ControllerAdministrador();
        administrador.setDatos(datos);

        // Preparar datos de prueba
        String usuario = "dav";
        String contrasena = "1234";

        // Ejecutar el código real
        administrador.LogIn(usuario, contrasena);

        // Verificar los resultados
        assertNotNull(administrador.getAdministrador());
    }

    @Test
    public void testLogInIncorrecto() throws IOException {

    	BaseDatos datos = new BaseDatos();
    	//datos.descargarTodoslosDatos();

        // Utilizar objetos reales
        ControllerAdministrador administrador = new ControllerAdministrador();
        administrador.setDatos(datos);

        // Preparar datos de prueba
        String usuario = "klpo";
        String contrasena = "321";

        // Ejecutar el código real
        administrador.LogIn(usuario, contrasena);

        // Verificar los resultados
        assertNull(administrador.getAdministrador());
    }
}