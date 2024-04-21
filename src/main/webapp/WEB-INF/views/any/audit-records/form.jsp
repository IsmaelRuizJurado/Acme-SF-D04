<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.audit-records.form.label.code" path="code"/>	
	<acme:input-moment code="any.audit-records.form.label.startPeriod" path="startPeriod"/>	
	<acme:input-moment code="any.audit-records.form.label.endPeriod" path="endPeriod"/>	
	<acme:input-textbox code="any.audit-records.form.label.mark" path="mark"/>	
	<acme:input-url code="any.audit-records.form.label.link" path="link"/>
</acme:form>