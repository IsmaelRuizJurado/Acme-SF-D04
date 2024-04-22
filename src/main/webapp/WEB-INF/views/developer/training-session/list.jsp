
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.training_session.list.label.code" path="code"  width="40%"/>
	<acme:list-column code="developer.training_session.list.label.location" path="location" width="40%" />
	<acme:list-column code="developer.training_session.list.label.instructor" path="instructor" width="20%" />
</acme:list>

<jstl:if test="${masterId==null}">
	<acme:button code="manager.user-story.list.button.create" action="/manager/user-story/create"/>
</jstl:if>