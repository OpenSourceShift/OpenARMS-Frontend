package api.entities;

import java.util.HashMap;

public class UserJSON extends BaseModelJSON {
	public Long id;
	public String name;
	public String email;
	public String secret;
    public String backend;
    public HashMap<String, String> attributes;
}
