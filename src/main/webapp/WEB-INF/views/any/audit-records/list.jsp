
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.audit-records.list.label.code" path="code"  width="20%"/>
	<acme:list-column code="any.audit-records.list.label.startPeriod" path="startPeriod" width="20%" />
	<acme:list-column code="any.audit-records.list.label.endPeriod" path="endPeriod" width="20%" />
	<acme:list-column code="any.audit-records.list.label.mark" path="mark" width="20%" />
	<acme:list-column code="any.audit-records.list.label.link" path="link" width="20%" />

</acme:list>