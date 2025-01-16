package org.openmrs.eip.fhir.routes.resources;

import static java.util.Base64.getEncoder;

import static org.openmrs.eip.fhir.Constants.HEADER_FHIR_EVENT_TYPE;
import static org.openmrs.eip.fhir.Constants.PROP_EVENT_OPERATION;
import static org.openmrs.eip.fhir.Constants.SUPPLY_REQUEST_ORDER_TYPE_UUID;

import java.util.Collections;

import org.apache.camel.LoggingLevel;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.SupplyRequest;
import org.openmrs.eip.fhir.FhirResource;
import org.openmrs.eip.fhir.routes.resources.models.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SupplyRequestRouter extends BaseFhirResourceRouter {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	SupplyRequestRouter() {
		super(FhirResource.SUPPLYREQUEST);
	}
	
	@Override
	public void configure() throws Exception {
		from(FhirResource.SUPPLYREQUEST.incomingUrl()).routeId("fhir-supplyrequest-router").filter(isSupportedTable()).toD(
		    "sql:SELECT ot.uuid as uuid from order_type ot join orders o on o.order_type_id = ot.order_type_id where o.uuid ='${exchangeProperty.event.identifier}'?dataSource=#openmrsDataSource")
		        .filter(simple("${body[0]['uuid']} == '" + SUPPLY_REQUEST_ORDER_TYPE_UUID + "'"))
		        .log(LoggingLevel.INFO, "Processing SupplyRequestRouter ${exchangeProperty.event.tableName}")
		        .toD(
		            "sql:SELECT voided, order_action, previous_order_id FROM orders WHERE uuid = '${exchangeProperty.event.identifier}'?dataSource=#openmrsDataSource")
		        .choice().when(simple("${exchangeProperty.event.operation} == 'd' || ${body[0]['voided']} == 1"))
		        .setHeader(HEADER_FHIR_EVENT_TYPE, constant("d")).setBody(simple("${exchangeProperty.event.identifier}"))
		        .to(FhirResource.SUPPLYREQUEST.outgoingUrl()).otherwise().process(exchange -> {
			        String username = exchange.getContext().resolvePropertyPlaceholders("{{openmrs.username}}");
			        String password = exchange.getContext().resolvePropertyPlaceholders("{{openmrs.password}}");
			        String auth = username + ":" + password;
			        String base64Auth = getEncoder().encodeToString(auth.getBytes());
			        exchange.getIn().setHeader("Authorization", "Basic " + base64Auth);
		        }).setHeader("CamelHttpMethod", constant("GET"))
		        //TODO: Replace with {{openmrs.baseUrl}}
		        .toD("http://openmrs:8080/openmrs/ws/rest/v1/order/${exchangeProperty.event.identifier}")
		        .process(exchange -> {
			        Order order = objectMapper.readValue(exchange.getIn().getBody(String.class), Order.class);
			        exchange.getMessage().setBody(mapOrderToSupplyRequest(order));
		        }).setHeader(HEADER_FHIR_EVENT_TYPE, simple("${exchangeProperty." + PROP_EVENT_OPERATION + "}"))
		        .to(FhirResource.SUPPLYREQUEST.outgoingUrl()).endChoice().end();
	}
	
	private SupplyRequest mapOrderToSupplyRequest(Order order) {
		SupplyRequest supplyRequest = new SupplyRequest();
		supplyRequest.setId(order.getUuid());
		supplyRequest.setItem(new Reference().setReference("MedicalSupply/" + order.getConcept().getUuid())
		        .setDisplay(order.getConcept().getDisplay()));
		supplyRequest.setReasonReference(Collections.singletonList(
		    new Reference().setType("Encounter").setReference("Encounter/" + order.getEncounter().getUuid())));
		supplyRequest.setQuantity(new Quantity().setValue(order.getQuantity()).setCode(order.getQuantityUnits().getUuid()));
		supplyRequest.setRequester(
		    new Reference().setReference(order.getOrderer().getUuid()).setDisplay(order.getOrderer().getDisplay()));
		supplyRequest.setDeliverTo(new Reference().setReference("Patient/" + order.getPatient().getUuid())
		        .setDisplay(order.getPatient().getDisplay()));
		supplyRequest.setStatus(SupplyRequest.SupplyRequestStatus.ACTIVE);
		
		return supplyRequest;
	}
}