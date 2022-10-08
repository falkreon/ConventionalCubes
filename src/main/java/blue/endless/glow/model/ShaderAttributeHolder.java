package blue.endless.glow.model;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.collect.ImmutableMap;

public interface ShaderAttributeHolder {
	public @Nullable <T> T get(ShaderAttribute<T> attribute);
	
	public default <T> T get(ShaderAttribute<T> attribute, T fallback) {
		T t = get(attribute);
		return (t==null) ? fallback : t;
	}
	
	public ImmutableMap<ShaderAttribute<?>, Object> getAll();
}
