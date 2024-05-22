<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.training-module.form.label.code" path="code"/>	
	<acme:input-moment code="any.training-module.form.label.creationTime" path="creationTime"/>	
	<acme:input-textbox code="any.training-module.form.label.details" path="details"/>	
	<acme:input-textbox code="any.training-module.form.label.basicLevel" path="basicLevel"/>
	<acme:input-moment code="any.training-module.form.label.updateMoment" path="updateMoment"/>	
	<acme:input-url code="any.training-module.form.label.link" path="optionalLink"/>	
	<acme:input-textbox code="any.training-module.form.label.developer" path="degree"/>
	<acme:input-textbox code="any.training-module.form.label.estimatedTotalTime" path="estimatedTotalTime" readonly="true"/>
	<acme:input-textbox code="developer.training-module.form.label.project" path="projectCode" readonly="true"/>
</acme:form>