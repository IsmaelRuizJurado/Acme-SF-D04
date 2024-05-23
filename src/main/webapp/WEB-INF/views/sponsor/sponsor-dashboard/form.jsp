

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.average-sponsorship-cost"/>
		</th>
		<td>
			<acme:print value="${averageSponsorshipAmount[0]}"/>
		</td>
		<td>
			<acme:print value="${averageSponsorshipAmount[1]}"/>
		</td>
		<td>
			<acme:print value="${averageSponsorshipAmount[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.min-sponsorship-cost"/>
		</th>
		<td>
			<acme:print value="${minSponsorshipAmount[0]}"/>
		</td>
		<td>
			<acme:print value="${minSponsorshipAmount[1]}"/>
		</td>
		<td>
			<acme:print value="${minSponsorshipAmount[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.max-sponsorship-cost"/>
		</th>
		<td>
			<acme:print value="${maxSponsorshipAmount[0]}"/>
		</td>
		<td>
			<acme:print value="${maxSponsorshipAmount[1]}"/>
		</td>
		<td>
			<acme:print value="${maxSponsorshipAmount[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.lin-dev-sponsorship-cost"/>
		</th>
		<td>
			<acme:print value="${desviationSponsorshipAmount[0]}"/>
		</td>
		<td>
			<acme:print value="${desviationSponsorshipAmount[1]}"/>
		</td>
		<td>
			<acme:print value="${desviationSponsorshipAmount[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.average-invoice-cost"/>
		</th>
		<td>
			<acme:print value="${averageInvoiceQuantity[0]}"/>
		</td>
		<td>
			<acme:print value="${averageInvoiceQuantity[1]}"/>
		</td>
		<td>
			<acme:print value="${averageInvoiceQuantity[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.max-invoice-cost"/>
		</th>
		<td>
			<acme:print value="${maxInvoiceQuantity[0]}"/>
		</td>
		<td>
			<acme:print value="${maxInvoiceQuantity[1]}"/>
		</td>
		<td>
			<acme:print value="${maxInvoiceQuantity[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.min-invoice-cost"/>
		</th>
		<td>
			<acme:print value="${minInvoiceQuantity[0]}"/>
		</td>
		<td>
			<acme:print value="${minInvoiceQuantity[1]}"/>
		</td>
		<td>
			<acme:print value="${minInvoiceQuantity[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.lin-dev-invoice-cost"/>
		</th>
		<td>
			<acme:print value="${desviationInvoiceQuantity[0]}"/>
		</td>
		<td>
			<acme:print value="${desviationInvoiceQuantity[1]}"/>
		</td>
		<td>
			<acme:print value="${desviationInvoiceQuantity[2]}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.21orless-invoice"/>
		</th>
		<td>
			<acme:print value="${numInvoicesWithTaxLessOrEqualThan21}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsorDashboard.form.label.link-sponsorship"/>
		</th>
		<td>
			<acme:print value="${numSponsorshipsWithLink}"/>
		</td>
	</tr>
</table>


<acme:return/>