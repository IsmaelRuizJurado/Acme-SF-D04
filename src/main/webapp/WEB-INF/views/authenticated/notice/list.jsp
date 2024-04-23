<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.notice.list.label.title" path="title" />
	<acme:list-column code="authenticated.notice.list.label.message" path="message" />
	<acme:list-column code="authenticated.notice.list.label.link" path="link" />
</acme:list>
	<acme:button code="authenticated.notice.create" action="/authenticated/notice/create"/>