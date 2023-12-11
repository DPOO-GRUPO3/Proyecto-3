package pruebasCargaDatos;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import controller.BaseDatos;
import model.Administrador;
import model.Sede;

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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class SedeTest {

    // Rutas de archivos para copia y restauración
    private static final String TEST_FILE_SEDES = "data/sedes.txt";
    private static final String BACKUP_FILE_SEDES = "data/sedesBackup.txt";

    // Realizar copia de seguridad del archivo de prueba antes de las pruebas
    @BeforeAll
    public static void realizarCopiaDeSeguridad() throws IOException {
        Path archivoPrueba = Paths.get(TEST_FILE_SEDES);
        Path archivoBackup = Paths.get(BACKUP_FILE_SEDES);

        // Copiar el archivo de prueba al archivo de copia de seguridad
        Files.copy(archivoPrueba, archivoBackup);
    }
    @AfterAll
    public static void cleanup1() throws IOException {
        // Restaurar el archivo original después de la prueba
        copyFile(BACKUP_FILE_SEDES, TEST_FILE_SEDES);
        // Eliminar el archivo de respaldo
        new File(BACKUP_FILE_SEDES).delete();
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
    public void testCargarSedesDesdeArchivo() throws IOException {
        // Crear una instancia de BaseDatos
        BaseDatos baseDatos = new BaseDatos();

        // Descargar los datos desde el archivo de prueba
        baseDatos.descargarTodoslosDatos();

        // Obtener el mapa de sedes de la instancia de BaseDatos
        HashMap<String, Sede> mapaSedes = baseDatos.getMapaSedes();

        // Asegurarse de que el mapa de sedes no esté vacío
        assertEquals(false, mapaSedes.isEmpty());

        // Obtener la sede de acuerdo con el nombre esperado
        Sede sedeEsperada = mapaSedes.get("sur");

        // Asegurarse de que la sede esperada no sea nula
        assertEquals(true, sedeEsperada != null);

        // Comparar la sede cargada con la esperada
        assertEquals("sur", sedeEsperada.getNombre());
        assertEquals("calle 45", sedeEsperada.getUbicacion());
        assertEquals(LocalTime.parse("00:00"), sedeEsperada.getInicio());
        assertEquals(LocalTime.parse("23:59:59"), sedeEsperada.getFin());
    }
}