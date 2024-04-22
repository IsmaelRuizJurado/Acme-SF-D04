<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.training_session.form.label.code" path="code"/>	
	<acme:input-moment code="developer.training_session.form.label.startPeriod" path="startPeriod"/>	
	<acme:input-moment code="developer.training_session.form.label.endPeriod" path="endPeriod"/>
	<acme:input-textbox code="developer.training_session.form.label.location" path="location"/>	
	<acme:input-textbox code="developer.training_session.form.label.instructor" path="instructor"/>
	<acme:input-email code="developer.training_session.form.label.contactEmail" path="contactEmail"/>
	<acme:input-url code="developer.training_session.form.label.link" path="optionalLink"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="developer.training_session.form.button.update" action="/developer/training-session/update"/>
			<acme:submit code="developer.training_session.form.button.delete" action="/developer/training-session/delete"/>
			<acme:submit code="developer.training_session.form.button.publish" action="/developer/training-session/publish"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true }">
			<acme:submit code="developer.training_session.form.button.update" action="/developer/training-session/update"/>
			<acme:submit code="developer.training_session.form.button.delete" action="/developer/training-session/delete"/>
			<acme:submit code="developer.training_session.form.button.publish" action="/developer/training-session/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training_session.form.button.create" action="/developer/training-session/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>