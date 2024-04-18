<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.training_module.form.label.code" path="code"/>	
	<acme:input-moment code="developer.training_module.form.label.creationTime" path="creationTime"/>	
	<acme:input-textbox code="developer.training_module.form.label.details" path="details"/>
	<acme:input-select code="developer.training_module.form.label.basicLevel" path="basicLevel" choices="${levels}"/>	
	<acme:input-moment code="developer.training_module.form.label.updateMoment" path="updateMoment"/>
	<acme:input-url code="developer.training_module.form.label.link" path="link"/>
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">	
			<acme:button code="developer.training_module.training_session" action="/developer/training-session/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true && hasTrainingSessions}">	
			<acme:button code="developer.training_module.training_session" action="/developer/training-session/list?masterId=${id}"/>
			<acme:submit code="developer.training_module.form.button.update" action="/developer/training-module/update"/>
			<acme:submit code="developer.training_module.form.button.delete" action="/developer/training-module/delete"/>
			<acme:submit code="developer.training_module.form.button.publish" action="/developer/training-module/publish"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true }">
			<acme:button code="developer.training_module.training_session" action="/developer/training-session/list?masterId=${id}"/>
			<acme:submit code="developer.training_module.form.button.update" action="/developer/training-module/update"/>
			<acme:submit code="developer.training_module.form.button.delete" action="/developer/training-module/delete"/>
			<acme:submit code="developer.training_module.form.button.publish" action="/developer/training-module/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="developer.training_module.form.button.create" action="/developer/training-module/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>