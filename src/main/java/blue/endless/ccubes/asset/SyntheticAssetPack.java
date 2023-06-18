package blue.endless.ccubes.asset;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.io.ByteSource;
import com.google.gson.JsonObject;

import net.minecraft.resource.ResourceIoSupplier;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.pack.ResourcePack;
import net.minecraft.resource.pack.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class SyntheticAssetPack /*implements ResourcePack*/ {
	private final String name;
	private final Map<String, ByteSource> root = new HashMap<>();
	private final Map<Identifier, ByteSource> resources = new HashMap<>();
	private final Map<Identifier, ByteSource> data = new HashMap<>();
	
	public SyntheticAssetPack(String name) {
		this.name = name;
	}
	
	//@Override
	public InputStream openRoot(String fileName) throws IOException {
		//System.out.println("OpenRoot "+fileName);
		if (fileName.contains("/")||fileName.contains("\\")) throw new IllegalArgumentException("Root files must be root files, not paths");
		
		ByteSource file = root.get(fileName);
		if (file==null) throw new FileNotFoundException("Could not find root file '"+fileName+"'.");
		
		return file.openStream();
	}

	/*
	@Override
	public ResourceIoSupplier<InputStream> open(ResourceType type, Identifier id) throws IOException {
		//System.out.println("Open "+id);
		
		switch(type) {
		case CLIENT_RESOURCES:
			ByteSource resource = resources.get(id);
			if (resource==null) throw new FileNotFoundException("Could not find resource '"+id+"'.");
			return resource.openStream();
			
		case SERVER_DATA:
			ByteSource dataItem = data.get(id);
			if (dataItem==null) throw new FileNotFoundException("Could not find data '"+id+"'.");
			return dataItem.openStream();
			
		default:
			throw new FileNotFoundException("Unknown asset type '"+type.name()+"'.");
		}
	}*/

	//@Override
	public Collection<Identifier> findResources(ResourceType type, String namespace, String startingPath, Predicate<Identifier> pathFilter) {
		switch(type) {
		case CLIENT_RESOURCES:
			return resources.keySet().stream()
					.filter(it->it.getNamespace().equals(namespace))
					.filter(it->it.getPath().startsWith(startingPath))
					.toList();
		case SERVER_DATA:
			return data.keySet().stream()
					.filter(it->it.getNamespace().equals(namespace))
					.filter(it->it.getPath().startsWith(startingPath))
					.filter(pathFilter)
					.toList();
		default:
			return Collections.emptySet();
		}
	}

	//@Override
	public boolean contains(ResourceType type, Identifier id) {
		switch(type) {
		case CLIENT_RESOURCES:
			return resources.containsKey(id);
		case SERVER_DATA:
			return data.containsKey(id);
		default:
			return false;
		}
	}

	//@Override
	public Set<String> getNamespaces(ResourceType type) {
		switch(type) {
		case CLIENT_RESOURCES:
			return resources.keySet().stream()
					.map(it->it.getNamespace())
					.collect(Collectors.toSet());
		case SERVER_DATA:	
			return data.keySet().stream()
					.map(it->it.getNamespace())
					.collect(Collectors.toSet());
		default:
			return Collections.emptySet();
		}
	}

	//@Override
	public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
		JsonObject packObject = new JsonObject();
		packObject.addProperty("description", "Conventional Cubes synthetic assets");
		packObject.addProperty("pack_format", 9); //1.19.x
		
		JsonObject metadataObject = new JsonObject();
		metadataObject.add("pack", packObject);
		
		if (!metadataObject.has(metaReader.getKey())) {
			return null;
		} else {
			return metaReader.fromJson(JsonHelper.getObject(metadataObject, metaReader.getKey()));
		}
	}
	
	public void provideResource(Identifier id, ByteSource asset) {
		resources.put(id, asset);
	}
	
	public void provideData(Identifier id, ByteSource asset) {
		data.put(id, asset);
	}
	
	public void provideRootFile(String fileName, ByteSource asset) {
		root.put(fileName, asset);
	}
	
	public void provideAsset(ResourceType type, Identifier id, ByteSource asset) {
		switch (type) {
		case CLIENT_RESOURCES:
			provideResource(id, asset);
			break;
		case SERVER_DATA:
			provideData(id, asset);
			break;
		default:
			break;
		}
	}

	//@Override
	public String getName() {
		return name;
	}

	//@Override
	public void close() {
		//does nothing
	}
}
