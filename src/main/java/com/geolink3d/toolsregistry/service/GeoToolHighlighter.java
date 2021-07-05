package com.geolink3d.toolsregistry.service;

import java.util.ArrayList;
import java.util.List;
import com.geolink3d.toolsregistry.model.GeoTool;

public class GeoToolHighlighter {

	private List<GeoTool> highlightedGeoToolStore;
	private String searchedExpression;
	private List<Integer> beginIndexStore;
	private List<Integer> endIndexStore;
	private final String preTag = "<span style=\"background-color: #3996f3;\">";
	private final String postTag = "</span>";


	public GeoToolHighlighter(List<GeoTool> inputGeoTools) {
		this.highlightedGeoToolStore = inputGeoTools;
	}	
	
	public List<GeoTool> getHighlightedGeoToolStore() {
		return highlightedGeoToolStore;
	}

	public void setSearchedExpression(String searchedExpression) {
		this.searchedExpression = searchedExpression.toLowerCase();
	}


	public void createHighlightedGeoToolStore() {
		

		for (int i = 0; i < highlightedGeoToolStore.size(); i++) {

			if (highlightedGeoToolStore.get(i).getToolName().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(getHighlightedGeoToolStore().get(i).getToolName().toLowerCase());
				createEndIndexStore();
				highlightedGeoToolStore.get(i).setToolName(createHighlightedString(highlightedGeoToolStore.get(i).getToolName()));
			}
		}

	}

	private void createBeginIndexStore(String containerText) {

		beginIndexStore = new ArrayList<>();

		for (int i = 0; i <= containerText.length() - searchedExpression.length(); i++) {

			if (containerText.charAt(i) == searchedExpression.charAt(0)
					&& containerText.substring(i, i + searchedExpression.length()).equals(searchedExpression)) {

				beginIndexStore.add(i);

			}

		}

	}

	private void createEndIndexStore() {
		
		endIndexStore = new ArrayList<>();
		
		for(int i = 0; i < beginIndexStore.size(); i++) {
			
		int endIndex = beginIndexStore.get(i) + searchedExpression.length() - 1;
			
		if(i + 1 < beginIndexStore.size() && endIndex >= beginIndexStore.get(i + 1)) {
			
			continue;
		
		}
			
		endIndexStore.add(endIndex);
		
		}
			
	}
	
	private String createHighlightedString(String text) {

		char[] container = text.toCharArray();
		StringBuilder builder = new StringBuilder();
		boolean isOpenTag = false;

		for (int i = 0; i < container.length; i++) {

			
		 if (beginIndexStore.contains(i) && !isOpenTag) {

				builder.append(preTag);
				isOpenTag = true;

			} 
			 	 
		 builder.append(container[i]);	 
		 
		 if (endIndexStore.contains(i) && isOpenTag) {

				builder.append(postTag);
				isOpenTag = false;

			} 
					
	}


		return builder.toString();
	}

	
}
