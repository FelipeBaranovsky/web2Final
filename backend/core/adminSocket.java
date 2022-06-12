package core;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import static core.Util.Sout;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import model.clienteDTO;
import util.Conexion;
import dao.clienteDAO;
/**
  * @author Baranovsky-Yanes
 */
public class adminSocket extends Thread {
  private final Socket conector;
  private String metodoPedido;
  private String httpPedido;
  private final String NEW_LINE = "\r\n";
  private StringBuffer sb;
  private final String APP_NAME = "api";
  private String URL;

  adminSocket(Socket insocket) {
    conector = insocket;
  }

  public void procesarSocket() {
    this.start();
  }

  @Override // sobreescribimos el método run del Thread para procesar el socket

  public void run() { // proceso principal del server
    try {
      // capturamos el flujo de entrada
      // creamos un objeto para manipular el pedido. Necesitamos que tenga acceso al
      // socket
      // se lo inyectamos en el constructor

      InputStream flujoentrada = conector.getInputStream();
      BufferedReader buffer = new BufferedReader(new InputStreamReader(flujoentrada));
      // necesitamos un printStream para enviar archivos
      PrintStream ps = new PrintStream(new BufferedOutputStream(conector.getOutputStream()));
      // procesamos El Stream y se lo pasamos como String a HttpRequest
      String linea = buffer.readLine();
      String header = linea;
      /** Si la cabecera es nula salimos !!! */
      if (header == null)
        return;

      StringTokenizer tokenizer = new StringTokenizer(header);
      metodoPedido = tokenizer.nextToken();
      httpPedido = tokenizer.nextToken();
      httpPedido = header + "\r\n";

      while (buffer.ready()) {
        /** Leemos todo el pedido HTTP hasta el final.... */

        httpPedido += buffer.readLine() + "\r\n";
      }
      System.out.println(httpPedido);
      /*
       * System.out.println("HTTP-METHOD: " + metodoPedido);
       * System.out.println(httpPedido);
       */

      HttpRequest req = new HttpRequest(httpPedido);
      HttpResponse resp = new HttpResponse(conector);

      // AHORA PARA VER SI TODO ESTA OK VAMOS A GENERAR UN ECHO AL WEB BROWSER
      // ANTES QUE NADA SE ENVIA UN ENCABEZADO DE ESTADO Y AUTORIZACION PARA CORS SINO
      // NO FUNCIONA
      // DESDE OTROS SITIOS EXTERNOS, POR EJEMPLO ALGO QUE HAGAMOS CON ANGULARJS Y
      // QUERRAMOS CONSUMIR
      // EXTERNAMENTE
      // capturamos los parametros enviados

      String PaginaInicio;
      System.out.println("Accion: " + req.getAccion());
      System.out.println(req.getMetodo());
      
      // EMPEZAMOS EL ANALISIS DE GET
      
      if(req.getMetodo().trim().equalsIgnoreCase("GET")){  
      
      if (req.getAccion() != null) {
        String hacer = req.getAccion();
        
        // ANALIZAMOS LAS ACCIONES
        
        if (hacer.equalsIgnoreCase("Listar")) {
          clienteDTO cli = new clienteDTO();
          clienteDAO cdao = new clienteDAO();
          List<clienteDTO> listadoClientes;
          listadoClientes = cdao.readAll();
          clienteDTO cliente = new clienteDTO();
          Gson gson = new Gson();
          String listadoJSON = "[";

          for (int t = 0; t < listadoClientes.size(); t++) {
            cliente = (clienteDTO) listadoClientes.get(t);

            listadoJSON += gson.toJson(cliente) + ",";
          }
          listadoJSON = listadoJSON.substring(0, listadoJSON.length() - 1);
          listadoJSON += "]";
          // resp.enviarRespuestaDatos(200, resp.getInitPage("Hola Mundo !!!"));
          Sout(gson.toJson((clienteDTO) cli));
          PaginaInicio = resp.getInitPage(gson.toJson((clienteDTO) cli));
          resp.imprimirSalida(resp.getHeader());
          resp.imprimirSalida(listadoJSON);
        } else { // no piden ninguna accion enviamos un archivo, por defecto es index.html
          if (req.getAccion().equals(" ")) // no pidieron nada enviamos pagina principal
          {
            PaginaInicio = resp.getHeader();
            PaginaInicio += resp.getInitPage("hola desde el servidor IW2");
            resp.imprimirSalida(PaginaInicio);
          } else { // pidieron un archivo, lo enviamos
            req.enviarArchivo("", ps);
          }
        }
      }
      }
        // EMPEZAMOS EL ANALISIS DE POST
      
      if(req.getMetodo().trim().equalsIgnoreCase("POST")){ 
      Sout("estoy en Post");
      if (req.getAccion() != null) {
        String hacer = req.getAccion();
        
        // ANALIZAMOS LAS ACCIONES
        
        if (hacer.equalsIgnoreCase("ListarPO")) {
          /*System.out.println("estoy en Listar del Post");
            clienteDAO cDAO = new clienteDAO();
            String params = req.getParametrosPost();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            
            // extraer el body del params
            String test = params.substring(params.indexOf("form-data; name=") + 11, params.length());
            System.out.println("Test:" + test);

            String test2[] = test.split("name=\"");

            for (int i = 1; i < test2.length; i++) {
              // quitar el primer " que encuentre
              test2[i] = test2[i].substring(test2[i].indexOf("\"") + 1);
              // quitar el guion del final
              test2[i] = test2[i].substring(0, test2[i].indexOf("-"));
            }

            clienteDTO cli = new clienteDTO();
            
            cli.setDni(test2[1]);
            cli.setApellido(test2[2]);
            cli.setActividad(test2[3]);
            cli.setDias(Integer.parseInt(test2[4]));
            cli.setCosto(Float.parseFloat(test2[5]));

          System.out.println(cli.toString());
          cDAO.create(cli);
        */
        clienteDTO lib = new clienteDTO();
        clienteDAO ldao = new clienteDAO();
        List<clienteDTO> listadoLibros;
        listadoLibros = ldao.readAll();
        clienteDTO libro = new clienteDTO();
        Gson gson = new Gson();
        String listadoJSON = "[";

        for (int t = 0; t < listadoLibros.size(); t++) {
          libro = (clienteDTO) listadoLibros.get(t);

          listadoJSON += gson.toJson(libro) + ",";
        }
        listadoJSON = listadoJSON.substring(0, listadoJSON.length() - 1);
        listadoJSON += "]";
        // resp.enviarRespuestaDatos(200, resp.getInitPage("Hola Mundo !!!"));
        Sout(gson.toJson((clienteDTO) lib));
        PaginaInicio = resp.getInitPage(gson.toJson((clienteDTO) lib));
        resp.imprimirSalida(resp.getHeader());
        resp.imprimirSalida(listadoJSON); 

        }
        
        if (hacer.trim().equalsIgnoreCase("Buscar")) {
          Sout("estoy en Buscar del Post");
          clienteDTO clie = new clienteDTO();
          clienteDAO cdao = new clienteDAO();
          List<clienteDTO> listadoClientes;
          Gson gson = new Gson();
          String pp=req.getParametrosPost();
          JsonObject postData = new JsonObject();
          if(pp!=null){
            postData = gson.fromJson(pp, JsonObject.class);
          }
          //listadoLibros = ldao.read(300);
          clienteDTO cliente = new clienteDTO();
          cliente.setDni(postData.get("dni").getAsString().replace("\"", ""));
          cliente.setActividad(postData.get("actividad").getAsString().replace("\"", ""));
          cliente=cdao.read(cliente);
          
          String listadoJSON = "[";

         

            listadoJSON += gson.toJson(cliente) + ",";
          
          listadoJSON = listadoJSON.substring(0, listadoJSON.length() - 1);
          listadoJSON += "]";
          
          // resp.enviarRespuestaDatos(200, resp.getInitPage("Hola Mundo !!!"));
          Sout(gson.toJson((clienteDTO) clie));
          PaginaInicio = resp.getInitPage(gson.toJson((clienteDTO) clie));
          resp.imprimirSalida(resp.getHeader());
          resp.imprimirSalida(listadoJSON);
        }  // no piden ninguna accion enviamos un archivo, por defecto es index.html
        
        if (req.getAccion().equals(" ")) // no pidieron nada enviamos pagina principal
          {
            PaginaInicio = resp.getHeader();
            PaginaInicio += resp.getInitPage("hola desde el servidor IW2");
            resp.imprimirSalida(PaginaInicio);
          }
        
        
        }
     
      }
    
      resp.cerrar();
      conector.close();

    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }

  }
}