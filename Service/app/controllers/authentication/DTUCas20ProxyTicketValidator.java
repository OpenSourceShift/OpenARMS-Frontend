package controllers.authentication;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.jasig.cas.client.util.XmlUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;

import play.Logger;

public class DTUCas20ProxyTicketValidator extends Cas20ProxyTicketValidator {
	
	public DTUCas20ProxyTicketValidator(String casServerUrlPrefix) {
		super(casServerUrlPrefix);
	}

	@Override
	protected Map extractCustomAttributes(final String xml) {
		Map result = super.extractCustomAttributes(xml);
		if(result.equals(Collections.EMPTY_MAP)) {
			result = new HashMap();
		}
		result.put("email", XmlUtils.getTextForElement(xml, "mail"));
		result.put("name", XmlUtils.getTextForElement(xml, "cn"));
		result.put("affiliation", XmlUtils.getTextForElement(xml, "eduPersonPrimaryAffiliation"));
		result.put("identifier", XmlUtils.getTextForElement(xml, "eduPersonTargetedID"));
		return result;
	}
	
}
