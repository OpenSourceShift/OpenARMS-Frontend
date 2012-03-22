package controllers;
import play.mvc.Controller;
import java.util.regex.*;


import com.google.gson.JsonParseException;

public class RegisterUser extends Controller {
	public static void getdata(String email, String passw, String confpassw){
        if(!checkpassw(passw, confpassw)){
            validation.addError("passwDontMatch", "Passwords doesn't match");
            validation.keep();
            showform();
        } else if(!checkemail(email)){
            validation.addError("email", "Wrong email");
            validation.keep();
            showform();
        } else if(!validatePass(passw)){
            validation.addError("validatePass", "Password is short");
            validation.keep();
            showform();
        } else {
            success();
        }
	}
    public static void showform(){
        render();
    }
    public static void success(){
        render();
    }

    //does passwords match?
    public static boolean checkpassw(String passw, String confpassw){
        return passw.equals(confpassw);
    }
    //check wrong symbols and length
    public static boolean validatePass(String passw){
        int length = passw.length();
        return (length > 4);
    }
    public static boolean checkemail(String email) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        boolean result = m.matches();
        return result;
    }
}
