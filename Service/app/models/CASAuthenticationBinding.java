package models;

import javax.persistence.Entity;

import play.data.validation.Required;

@Entity
public class CASAuthenticationBinding extends AuthenticationBinding {

	@Required
	public String service;
	
	@Required
	public String externalIdentifier;
	
}
