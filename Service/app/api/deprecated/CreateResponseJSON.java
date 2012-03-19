/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.deprecated;

/**
 *
 * @author veri
 */
@Deprecated
public class CreateResponseJSON {

    public String token;
    public String adminKey;

    public CreateResponseJSON(String token, String adminKey) {
        this.token = token;
        this.adminKey = adminKey;
    }
}
