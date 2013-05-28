package com.corejsf.util;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class Renderers {
	public static Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {

		if (submittedValue instanceof String) {
			Converter converter = getConverter(context, component);
			if (converter != null) {
				return converter.getAsObject(context, component, (String)submittedValue);
			}
		}
		
		return submittedValue;
	}

	private static Converter getConverter(FacesContext context,
			UIComponent component) {
		if (!(component instanceof ValueHolder)) {
			return null;
		}
		ValueHolder holder = (ValueHolder)component;
		
		Converter converter = holder.getConverter();
		if (converter != null) {
			return converter;
		}
		
		ValueExpression expression = component.getValueExpression("value");
		if (expression == null) {
			return null;
		}
		
		Class<?> targetType = expression.getType(context.getELContext());
		if (targetType == null) {
			return null;
		}
		
		Application app = context.getApplication();
		return app.createConverter(targetType);
	}
	
	public static String getFormId(FacesContext context, UIComponent component) {
		UIComponent parent = component;
		while(!(parent instanceof UIForm)) {
			parent = parent.getParent();
		}
		
		return parent.getClientId(context);
	}
	
}
