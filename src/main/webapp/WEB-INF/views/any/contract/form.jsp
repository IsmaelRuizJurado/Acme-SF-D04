<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.contract.form.label.code" path="code"/>	
	<acme:input-textbox code="any.contract.form.label.providerName" path="providerName"/>	
	<acme:input-textbox code="any.contract.form.label.customerName" path="customerName"/>	
	<acme:input-money code="any.contract.form.label.goals" path="goals"/>	
	<acme:input-textbox code="any.contract.form.label.budget" path="budget"/>	
	<acme:input-money code="any.contract.form.label.money" path="money" readonly="true"/>
	<jstl:choose>
		<jstl:when test="${hasProgressLogs}">
			<acme:button code="any.contract.progressLogs" action="/any/progress-logs/list?masterId=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>