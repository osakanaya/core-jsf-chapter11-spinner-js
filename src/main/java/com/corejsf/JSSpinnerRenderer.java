package com.corejsf;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import javax.faces.application.ResourceDependency;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.corejsf.util.Renderers;

@FacesRenderer(componentFamily = "javax.faces.Input", rendererType = "com.corejsf.JSSpinner")
@ResourceDependency(library="javascript", name="spinner.js")
public class JSSpinnerRenderer extends Renderer {
	@Override
	public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
			throws ConverterException {
		
		return Renderers.getConvertedValue(context, component, submittedValue);
	}

	@Override
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {

		ResponseWriter writer = context.getResponseWriter();
		String clientId = component.getClientId(context);
		String formId = Renderers.getFormId(context, component);

		///
		UIInput spinner = (UIInput)component;
		String min = component.getAttributes().get("minimum").toString();
		String max = component.getAttributes().get("maximum").toString();
		String size = component.getAttributes().get("size").toString();
		
		writer.startElement("input", spinner);
		writer.writeAttribute("type", "text", null);
		writer.writeAttribute("name", clientId, null);
		writer.writeAttribute("value", spinner.getValue().toString(), "value");
		if (size != null) {
			writer.writeAttribute("size", size, "size");
		}
		writer.endElement("input");
		
		///
		writer.startElement("script", spinner);
		writer.writeAttribute("language", "JavaScript", null);
		if (min != null) {
			writer.write(MessageFormat.format("document.forms[''{0}''][''{1}''].min = {2};", 
					formId, clientId, min));
		}
		
		if (max != null) {
			writer.write(MessageFormat.format("document.forms[''{0}''][''{1}''].max = {2};", 
					formId, clientId, max));
		}
		writer.endElement("script");
		
		///
		writer.startElement("input", spinner);
		writer.writeAttribute("type", "button", null);
		writer.writeAttribute("value", "<", "value");
		writer.writeAttribute("onclick", MessageFormat.format(
				"com.corejsf.spinner.spin(document.forms[''{0}''][''{1}''], -1);", 
				formId, clientId), null);
		writer.endElement("input");
		
		///
		writer.startElement("input", spinner);
		writer.writeAttribute("type", "button", null);
		writer.writeAttribute("value", ">", "value");
		writer.writeAttribute("onclick", MessageFormat.format(
				"com.corejsf.spinner.spin(document.forms[''{0}''][''{1}''], 1);", 
				formId, clientId), null);
		writer.endElement("input");
		
	}
	
	@Override
	public void decode(FacesContext context, UIComponent component) {
		EditableValueHolder spinner = (EditableValueHolder)component;
		Map<String, String> requestMap = context.getExternalContext().getRequestParameterMap();
		String clientId = component.getClientId();
		
		spinner.setSubmittedValue((String)requestMap.get(clientId));
		spinner.setValid(true);
	}
}
