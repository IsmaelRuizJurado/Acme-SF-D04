

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.risk.form.label.reference" path="reference" />
	<acme:input-moment code="administrator.risk.form.label.identificationDate" path="identificationDate" />
	<acme:input-moment code="administrator.risk.form.label.impact" path="impact"/>	
	<acme:input-moment code="administrator.risk.form.label.probability" path="probability" />
	<acme:input-moment code="administrator.risk.form.label.description" path="description" />
	<acme:input-moment code="administrator.risk.form.label.link" path="link" />
	

		<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')  }">
			<acme:submit code="administrator.risk.form.button.delete" action="/administrator/risk/delete"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')  }">
			<acme:submit code="administrator.risk.form.button.delete" action="/administrator/risk/delete"/>
		</jstl:when>
				
	</jstl:choose>
</acme:form>
		