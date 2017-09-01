package com.dayee.writtenExam.pc.filter;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class RolesOrAuthorizationFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to checkToken - allow access.
            return false;
        }

        Set<String> roles = CollectionUtils.asSet(rolesArray);
        for(String role:roles){
        	if(subject.hasRole(role)){
        		return true;
        	}
        }
        return false;
	}

}
