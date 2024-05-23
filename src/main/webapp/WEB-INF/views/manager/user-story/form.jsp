<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.user-story.form.label.title" path="title"/>	
	<acme:input-textarea code="manager.user-story.form.label.description" path="description"/>	
	<acme:input-double code="manager.user-story.form.label.estimatedCostPerHour" path="estimatedCostPerHour"/>	
	<acme:input-textarea code="manager.user-story.form.label.acceptanceCriteria" path="acceptanceCriteria"/>	
	<acme:input-select code="manager.user-story.form.label.priority" path="priority" choices="${priorities}"/>	
	<acme:input-url code="manager.user-story.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == false}">	 
			<acme:input-money code="manager.user-story.form.label.money" path="money" readonly="true"/>	
			<acme:button code="manager.user-story.list.button.add" action="/manager/project-user-story/create?userStoryId=${id}"/>
			<acme:button code="manager.user-story.list.button.deleteFromProject" action="/manager/project-user-story/delete?userStoryId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:input-money code="manager.user-story.form.label.money" path="money" readonly="true"/>				
			<acme:submit code="manager.user-story.form.button.update" action="/manager/user-story/update"/>
			<acme:submit code="manager.user-story.form.button.delete" action="/manager/user-story/delete"/>
			<acme:submit code="manager.user-story.form.button.publish" action="/manager/user-story/publish"/>
			<acme:button code="manager.user-story.list.button.add" action="/manager/project-user-story/create?userStoryId=${id}"/>
			<acme:button code="manager.user-story.list.button.deleteFromProject" action="/manager/project-user-story/delete?userStoryId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.user-story.form.button.create" action="/manager/user-story/create"/>
		</jstl:when>		
	</jstl:choose>
	
</acme:form>