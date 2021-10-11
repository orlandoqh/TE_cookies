package com.emergentes;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CookieServlet", urlPatterns = {"/CookieServlet"})
public class CookieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     String mensaje =null;
        boolean nuevaVisita=true;
        
        Cookie[]cookies=request.getCookies();
        Cookie contador = buscaCookie("contador", cookies);
        
        
        if (cookies!=null){
            for(Cookie c:cookies){
                if((c.getName().equals("visitante"))&& c.getValue().equals("SI")){
                    nuevaVisita=false;
                    break;
                }
            }
        }
        
        if (contador == null){
    
          if(nuevaVisita){
       Cookie cookie = new Cookie ("contador", "1");
       Cookie ck=new Cookie("visitante","SI");
       cookie.setMaxAge(60);
       response.addCookie(cookie);
            ck.setMaxAge(60);
            ck.setComment("Control de nuevos visitantes");
            response.addCookie(ck);
            mensaje="Gracias por visitar nuestra página";
       // Mostramos el mensaje de primera visita
 
       PrintWriter out = response.getWriter();          
       out.println ("Primera visita"); 
           
       }
    } else {
             
       int cont = Integer.parseInt(contador.getValue());
       cont++;
               
       Cookie cookie = new Cookie ("contador", "" + cont);
       cookie.setMaxAge(60);
       response.addCookie(cookie);
 
       PrintWriter out = response.getWriter();
       out.println ("Visita numero " + cont);
       
       mensaje="Estamos agradecidos por tenerlo nuevamente";
    }       
       
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        out.println("<h1>"+mensaje + "<h1>");
        out.println("<a href='index.jsp'>Ir al inicio</a>");
        
    }
     //esto viene despues del DOGET
    //buscador de cookies 

   private Cookie buscaCookie(String nombre,  Cookie[] cookies)
   {
    if (cookies == null)
       return null;
     
    for (int i = 0; i < cookies.length; i++)
       if (cookies[i].getName().equals(nombre))
        return cookies[i];
     
    return null;
   }
}