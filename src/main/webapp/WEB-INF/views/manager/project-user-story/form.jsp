

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<jstl:choose>	 
	<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
		<h2>
			<acme:message code="manager.projectUserStory.form.userStory.delete.info"/>
		</h2>
	</jstl:when>
	<jstl:when test="${_command == 'create'}">
		<h2>
			<acme:message code="manager.projectUserStory.form.userStory.info"/>
		</h2>

	</jstl:when>		
	


</jstl:choose>
	<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.projectUserStory.form.userStory.title"/>
		</th>
		<td>
			<acme:print value="${userStory.getTitle()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.projectUserStory.form.userStory.description"/>
		</th>
		<td>
			<acme:print value="${userStory.getDescription()}"/>
		</td>
	</tr>
	</table>

<acme:form>
	<acme:input-select code="manager.projectUserStory.form.label.project" path="project" choices="${projects}"/>	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'delete') && !proyectos.isEmpty()}">
			<acme:submit code="manager.projectUserStory.form.button.delete" action="/manager/project-user-story/delete?userStoryId=${userStoryId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.projectUserStory.form.button.create" action="/manager/project-user-story/create?userStoryId=${userStoryId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>