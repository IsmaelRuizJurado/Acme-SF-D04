
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.training_module.list.label.code" path="code"  width="25%"/>
	<acme:list-column code="developer.training_module.list.label.details" path="details" width="35%" />
	<acme:list-column code="developer.training_module.list.label.basicLevel" path="basicLevel" width="20%" />
	<acme:list-column code="developer.training_module.list.label.project" path="projectCode" width="20%"/>


</acme:list>
<acme:button code="developer.training_module.create" action="/developer/training-module/create"/>