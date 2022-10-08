package blue.endless.glow.model;

import java.util.HashMap;
import java.util.Map;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.collect.ImmutableMap;

public class AbstractShaderAttributeHolder implements ShaderAttributeHolder {
	protected Map<ShaderAttribute<?>, Object> attributes = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public @Nullable <T> T get(ShaderAttribute<T> attribute) {
		return (T) attributes.get(attribute);
	}
	
	public <T> void put(ShaderAttribute<T> attribute, T value) {
		attributes.put(attribute, value);
	}
	
	@Override
	public ImmutableMap<ShaderAttribute<?>, Object> getAll() {
		return ImmutableMap.copyOf(attributes);
	}
	
	public void putAll(ShaderAttributeHolder other) {
		//Assumes other isn't poisoned with wrongly typed data
		for(Map.Entry<ShaderAttribute<?>, Object> entry : other.getAll().entrySet()) {
			attributes.put(entry.getKey(), entry.getValue());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("{ ");
		
		if (!attributes.isEmpty()) {
			for(Map.Entry<ShaderAttribute<?>, Object> entry : attributes.entrySet()) {
				result.append("\"");
				result.append(entry.getKey().getName());
				result.append("\": ");
				result.append(entry.getValue().toString());
				result.append(", ");
			}
			
			result.deleteCharAt(result.length()-1); //Trim off trailing space
			result.deleteCharAt(result.length()-1); //Trim off trailing comma
			
			result.append(" ");
		}
		
		result.append("}");
		
		return result.toString();
	}
}
