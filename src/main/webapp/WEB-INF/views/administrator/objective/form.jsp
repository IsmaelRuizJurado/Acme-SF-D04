

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.objective.form.label.title" path="title" />
	<acme:input-textbox code="administrator.objective.form.label.description" path="description" />
	<acme:input-moment code="administrator.objective.form.label.instantiationMoment" path="instantiationMoment"/>	
	<acme:input-moment code="administrator.objective.form.label.startPeriod" path="startPeriod" />
	<acme:input-moment code="administrator.objective.form.label.endPeriod" path="endPeriod" />
	<jstl:if test="${_command == 'create'}">
		<acme:input-select code="administrator.objective.form.label.priority" path="priority" choices="${priorities}"/>
	</jstl:if>
	<acme:input-checkbox code="administrator.objective.form.label.critical" path="critical" />
	<acme:input-checkbox code="administrator.objective.form.label.confirmation" path="confirmation" />
	<acme:input-url code="administrator.objective.form.label.link" path="link" />
	
	
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="administrator.objective.form.button.create" action="/administrator/objective/create"/>
	</jstl:if>	
</acme:form>