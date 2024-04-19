
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.training-module.list.label.creationTime" path="creationTime"  width="40%"/>
	<acme:list-column code="authenticated.training-module.list.label.details" path="details" width="40%" />
	<acme:list-column code="authenticated.training-module.list.label.basicLevel" path="basicLevel" width="20%" />


</acme:list>