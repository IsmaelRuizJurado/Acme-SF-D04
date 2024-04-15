

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.objective.form.label.title" path="title" />
	<acme:input-textbox code="authenticated.objective.form.label.description" path="description" />
	<acme:input-moment code="authenticated.objective.form.label.instantiationMoment" path="instantiationMoment"/>	
	<acme:input-moment code="authenticated.objective.form.label.startPeriod" path="startPeriod" />
	<acme:input-moment code="authenticated.objective.form.label.endPeriod" path="endPeriod" />
	<jstl:if test="${_command == 'create'}">
		<acme:input-select code="authenticated.objective.form.label.priority" path="priority" choices="${priorities}"/>
	</jstl:if>
	<jstl:if test="${_command == 'show'}">
		<acme:input-textbox code="authenticated.objective.form.label.priority" path="priorityTitle"/>
	</jstl:if>
	<acme:input-checkbox code="authenticated.objective.form.label.critical" path="critical" />
	<acme:input-url code="authenticated.objective.form.label.link" path="link" />
	
	
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="authenticated.client.form.button.create" action="/authenticated/objective/create"/>
	</jstl:if>	
</acme:form>