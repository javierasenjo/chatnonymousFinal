/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacion;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author javie
 */
@WebListener
public class ListenerRequest implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest httpRequest = (HttpServletRequest) sre.getServletRequest();
        System.out.print("[Listener Request] Alguien con la ip" + httpRequest.getRemoteAddr() + " ha hecho algo");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
       
    }

}
