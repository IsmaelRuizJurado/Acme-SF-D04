
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.training-module.list.label.code" path="code"  width="25%"/>
	<acme:list-column code="authenticated.training-module.list.label.details" path="details" width="30%" />
	<acme:list-column code="authenticated.training-module.list.label.basicLevel" path="basicLevel" width="20%" />
	<acme:list-column code="authenticated.training-module.list.label.projectCode" path="projectCode" width="25%" />


</acme:list>