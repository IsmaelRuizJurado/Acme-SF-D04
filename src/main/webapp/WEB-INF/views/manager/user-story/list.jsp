
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.user-story.list.label.title" path="title"  width="40%"/>
	<acme:list-column code="manager.user-story.list.label.description" path="description" width="40%" />
	<acme:list-column code="manager.user-story.list.label.estimatedCostPerHour" path="estimatedCostPerHour" width="20%" />
</acme:list>

<jstl:if test="${masterId==null}">
	<acme:button code="manager.user-story.list.button.create" action="/manager/user-story/create"/>
</jstl:if>