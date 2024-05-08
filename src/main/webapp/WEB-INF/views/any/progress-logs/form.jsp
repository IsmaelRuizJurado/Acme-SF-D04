<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.progress-logs.form.label.recordId" path="recordId" placeholder="PG-XX-000"/>	
	<acme:input-textbox code="any.progress-logs.form.label.completeness" path="completeness"/>	
	<acme:input-textbox code="any.progress-logs.form.label.comment" path="comment"/>	
	<acme:input-money code="any.progress-logs.form.label.registrationMoment" path="registrationMoment"/>	
	<acme:input-textbox code="any.progress-logs.form.label.responsiblePerson" path="responsiblePerson"/>	
</acme:form>