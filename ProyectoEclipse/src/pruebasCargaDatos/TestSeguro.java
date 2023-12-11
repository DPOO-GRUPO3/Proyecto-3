package pruebasCargaDatos;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import controller.BaseDatos;
import model.Seguro;

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

public class TestSeguro {

    // Rutas de archivos para copia y restauración
    private static final String TEST_FILE_SEGUROS = "data/seguros.txt";
    private static final String BACKUP_FILE_SEGUROS = "data/segurosBackup.txt";

    // Realizar copia de seguridad del archivo de prueba antes de las pruebas
    @BeforeAll
    public static void realizarCopiaDeSeguridad() throws IOException {
        Path archivoPrueba = Paths.get(TEST_FILE_SEGUROS);
        Path archivoBackup = Paths.get(BACKUP_FILE_SEGUROS);

        // Copiar el archivo de prueba al archivo de copia de seguridad
        Files.copy(archivoPrueba, archivoBackup);
    }

    // Restaurar el archivo de prueba después de las pruebas
    @AfterAll
    public static void restaurarCopiaDeSeguridad() throws IOException {
        Path archivoPrueba = Paths.get(TEST_FILE_SEGUROS);
        Path archivoBackup = Paths.get(BACKUP_FILE_SEGUROS);

        // Restaurar el archivo de copia de seguridad al archivo de prueba
        Files.copy(archivoBackup, archivoPrueba, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
    
    @AfterAll
    public static void cleanup() throws IOException {
        // Restaurar el archivo original después de la prueba
        copyFile(BACKUP_FILE_SEGUROS, TEST_FILE_SEGUROS);
        // Eliminar el archivo de respaldo
        new File(BACKUP_FILE_SEGUROS).delete();
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
    public void testCargarSegurosDesdeArchivo() throws IOException {
        // Crear una instancia de BaseDatos
        BaseDatos baseDatos = new BaseDatos();

        // Descargar los datos desde el archivo de prueba
        baseDatos.descargarTodoslosDatos();

        // Obtener el mapa de seguros de la instancia de BaseDatos
        HashMap<String, Seguro> mapaSeguros = baseDatos.getMapaSeguros();

        // Asegurarse de que el mapa de seguros no esté vacío
        assertEquals(false, mapaSeguros.isEmpty());

        // Obtener el seguro de acuerdo con el nombre esperado
        Seguro seguroEsperado = mapaSeguros.get("2");

        // Asegurarse de que el seguro esperado no sea nulo
        assertEquals(true, seguroEsperado != null);

        // Comparar el seguro cargado con el esperado
        assertEquals("perdida", seguroEsperado.getNombre());
        assertEquals("1", seguroEsperado.getId());
        assertEquals(700.0, seguroEsperado.getPrecio());
    }
}
