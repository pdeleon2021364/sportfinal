
package org.diegocoyote.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.diegocoyote.db.Conexion;

public class GenerarReporte {
        //Generar Reporte
        public static void mostrarReporte(String nombreReporte, String titulo, Map parametros){
                               
           InputStream reporte = GenerarReporte.class.getResourceAsStream(nombreReporte);
           
            if (!parametros.containsKey("HojaMembretada")) {
                InputStream hoja = GenerarReporte.class.getResourceAsStream("/org/diegocoyote/image/hoja_membretada.jpg");
                if (hoja == null) {
                    System.out.println("No se encontro la imagen de la hoja membretada");
                }
                parametros.put("HojaMembretada", hoja);
            }
            
            
           
           
        try{
            JasperReport reporteMaestro =(JasperReport) JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteMaestro, parametros, Conexion.getInstancia().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso,false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
           //xcc
        
        
           
        }
    
    
    
    
}
