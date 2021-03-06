/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author javie
 */
public class EmailValidator {

    String regexGeneral = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    String regexCeu1 = "^([a-zA-Z0-9_\\-\\.]+)@usp.ceu.es$";
    String regexCeu2 = "^([a-zA-Z0-9_\\-\\.]+)@ceu.es$";
    Pattern regix = null;
    Matcher matcher = null;

    public boolean validadorCeu(String email) {
        regix = Pattern.compile(regexCeu1);
        Pattern regix2 = Pattern.compile(regexCeu2);
        matcher = regix.matcher(email);
        Matcher matcher2 = regix2.matcher(email);
        if (matcher.find() == true || matcher2.find() == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validadorGeneral(String email) {
        regix = Pattern.compile(regexGeneral);
        matcher = regix.matcher(email);
        if (matcher.find() == true) {
            return true;
        } else {
            return false;
        }

    }
}
