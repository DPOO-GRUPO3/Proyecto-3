package pruebasCargaDatos;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.BaseDatos;
import model.Empleado;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class TestEmpleados { 

    // Rutas de archivos para copia y restauración
    private static final String TEST_FILE_EMPLEADOS = "data/empleados.txt";
    private static final String BACKUP_FILE_EMPLEADOS = "data/empleadosBackup.txt";

    // Realizar copia de seguridad del archivo de prueba antes de las pruebas
    @BeforeAll
    public static void realizarCopiaDeSeguridad() throws IOException {
        Path archivoPrueba = Paths.get(TEST_FILE_EMPLEADOS);
        Path archivoBackup = Paths.get(BACKUP_FILE_EMPLEADOS);

        // Copiar el archivo de prueba al archivo de copia de seguridad
        Files.copy(archivoPrueba, archivoBackup);
    }

    // Restaurar el archivo original después de las pruebas
    @AfterAll
    public static void cleanup() throws IOException {
        // Restaurar el archivo original después de la prueba
        copyFile(BACKUP_FILE_EMPLEADOS, TEST_FILE_EMPLEADOS);
        // Eliminar el archivo de respaldo
        Files.deleteIfExists(Paths.get(BACKUP_FILE_EMPLEADOS));
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

    @Test
    public void testCargarEmpleadosDesdeArchivo() throws IOException {
        // Crear una instancia de BaseDatos
        BaseDatos baseDatos = new BaseDatos();

        // Descargar los datos desde el archivo de prueba
        baseDatos.descargarTodoslosDatos();

        // Obtener el mapa de empleados de la instancia de BaseDatos
        HashMap<String, Empleado> mapaEmpleados = baseDatos.getMapaEmpleados();

        // Asegurarse de que el mapa de empleados no esté vacío
        assertEquals(false, mapaEmpleados.isEmpty());

        // Obtener el empleado de acuerdo con el ID esperado
        Empleado empleadoEsperado = mapaEmpleados.get("laura"); // Utilizando el nombre de usuario como ID

        // Asegurarse de que el empleado esperado no sea nulo
        assertEquals(true, empleadoEsperado != null);

        // Comparar el empleado cargado con el esperado
        assertEquals("2", empleadoEsperado.getId());
        assertEquals("Laura Hurtado", empleadoEsperado.getNombre());
        assertEquals("laura", empleadoEsperado.getUsuario());
        assertEquals("123", empleadoEsperado.getContrasena());
        assertEquals("@", empleadoEsperado.getEmail());
        assertEquals("sur", empleadoEsperado.getSede().getNombre());
    }
}
