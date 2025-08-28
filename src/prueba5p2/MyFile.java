/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba5p2;

import java.io.File; 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mayra Bardales
 */

public class MyFile {

    private File file = null;

    public void setFile(String dir) {
        file = new File(dir);
    }

    public void getInfo() {
        if(file.exists()) {
            System.out.println("Sí existe.");
            System.out.println("\n Nombre: " + file.getName());
            System.out.println("\n Path: " + file.getPath());
            System.out.println("\n Absolute path: " + file.getAbsolutePath());
            System.out.println("\n Padre: " + file.getAbsoluteFile().getParentFile().getParent());
            System.out.println("\n Bytes: " + file.length());
            if(file.isFile()) {
                System.out.println("es un archivo.");
            } else if(file.isDirectory()) {
                System.out.println("es un folder.");
                System.out.println("Ultima modificación: " + new Date(file.lastModified()));
            }
        } else{
            System.out.println("no existe.");
        }
    }
    
    public void crearArchivo() throws IOException{
        if(file.createNewFile())
            System.out.println("Creado con éxito.");
        else
            System.out.println("No se pudo crear");
    }
    
    public void crearFolder() throws IOException{
        if(file.mkdirs())
            System.out.println("Folder creado con éxito.");
        else
            System.out.println("No se pudo crear.");
    }
    
    public boolean borrar() throws IOException {
       return file.delete();
    }
    
    public boolean borrarTodo() throws IOException {
        if (file.delete()) {
            return true;
        } else {
            File archivos[] = file.listFiles();
            
            for (File archivo : archivos) {
                archivo.delete();
            }
            return file.delete();
        }
    }  
    private void tree(File dir, String tab) {
        if (dir != null && dir.isDirectory()) {
            System.out.println(tab + dir.getName());
            File[] hijos = dir.listFiles();
            if (hijos != null) {
                for (File child : hijos) {
                    if (!child.isHidden()) {
                        tree(child, tab + "-");
                    }
                }
            }
        }
    }

    void tree(){
        tree(file,"-");
    }
    
    public void MostrarDir() {
        if (file != null && file.isDirectory()) {
            System.out.println("Directorio de: " + file.getAbsolutePath());
            System.out.println();
            System.out.printf("%-20s %-10s %-12s %-20s%n", 
                    "Última Modificación", "Tipo", "Tamaño", "Nombre");
            
            File[] archivos = file.listFiles();
            int contadorArchivos = 0;
            int contadorDirectorios = 0;
            long totalBytes = 0;
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            if (archivos != null) {
                for (File f : archivos) {
                    String fecha = sdf.format(new Date(f.lastModified()));
                    String tipo = f.isDirectory() ? "<DIR>" : "FILE";
                    String tamano = f.isDirectory() ? "-" : formatoTamano(f.length());
                    
                    if (f.isFile()) {
                        contadorArchivos++;
                        totalBytes += f.length();
                    } else {
                        contadorDirectorios++;
                    }
                    
                    System.out.printf("%-20s %-10s %-12s %-20s%n", 
                            fecha, tipo, tamano, f.getName());
                }
            }
            
            System.out.println();
            System.out.println(contadorArchivos + " archivos    " + formatoTamano(totalBytes));
            System.out.println(contadorDirectorios + " directorios " + formatoTamano(file.getFreeSpace()) + " libres");
            
        } else {
            System.out.println("El directorio no existe o no es válido.");
        }
    }
    
    private String formatoTamano(long bytes) {
        if (bytes < 1024)
            return bytes + " B";
        double kb = bytes / 1024.0;
        if (kb < 1024) 
            return String.format("%.1f KB", kb);
        double mb = kb / 1024.0;
        if (mb < 1024) 
            return String.format("%.1f MB", mb);
        double gb = mb / 1024.0;
            return String.format("%.1f GB", gb);
    }
}
