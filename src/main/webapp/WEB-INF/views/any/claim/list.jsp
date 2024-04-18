
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.claim.list.label.instantiationMoment" path="instantiationMoment"  width="40%"/>
	<acme:list-column code="authenticated.claim.list.label.description" path="description" width="40%" />
	<acme:list-column code="authenticated.claim.list.label.deparment" path="department" width="20%" />


</acme:list>