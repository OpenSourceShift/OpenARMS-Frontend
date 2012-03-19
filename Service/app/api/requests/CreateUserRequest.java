package api.requests;

public class CreateUserRequest extends Request {
	
	@Override
	public String getURL() {
		return "/user";
	}
/*
 * { "user": [ "name": "myaccount",
            "email": "test@hello.com",
            "backend": "facebook",
            "fbid": "32493284298" ]
}
 */
}
