
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.training_module.list.label.creationTime" path="creationTime"  width="40%"/>
	<acme:list-column code="developer.training_module.list.label.details" path="details" width="40%" />
	<acme:list-column code="developer.training_module.list.label.basicLevel" path="basicLevel" width="20%" />


</acme:list>
<acme:button code="developer.training_module.create" action="/developer/training-module/create"/>