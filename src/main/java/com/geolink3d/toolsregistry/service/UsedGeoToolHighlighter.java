package com.geolink3d.toolsregistry.service;

import java.util.ArrayList;
import java.util.List;
import com.geolink3d.toolsregistry.model.UsedGeoTool;

public class UsedGeoToolHighlighter {

	private List<UsedGeoTool> highlightedUsedGeoToolStore;
	private String searchedExpression;
	private List<Integer> beginIndexStore;
	private List<Integer> endIndexStore;
	private final String preTag = "<span style=\"background-color: #3996f3;\">";
	private final String postTag = "</span>";


	public UsedGeoToolHighlighter(List<UsedGeoTool> inputUsedGeoTools) {
		this.highlightedUsedGeoToolStore = inputUsedGeoTools;
	}	
	

	public List<UsedGeoTool> getHighlightedUsedGeoToolStore() {
		return highlightedUsedGeoToolStore;
	}



	public void setSearchedExpression(String searchedExpression) {
		this.searchedExpression = searchedExpression.toLowerCase();
	}


	public void createHighlightedUsedGeoToolStore() {
		

		for (int i = 0; i < highlightedUsedGeoToolStore.size(); i++) {

			if (highlightedUsedGeoToolStore.get(i).getToolname().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(getHighlightedUsedGeoToolStore().get(i).getToolname().toLowerCase());
				createEndIndexStore();
				highlightedUsedGeoToolStore.get(i).setToolname(createHighlightedString(highlightedUsedGeoToolStore.get(i).getToolname()));
			}
			if (highlightedUsedGeoToolStore.get(i).getWorkername().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(getHighlightedUsedGeoToolStore().get(i).getWorkername().toLowerCase());
				createEndIndexStore();
				highlightedUsedGeoToolStore.get(i).setWorkername(createHighlightedString(highlightedUsedGeoToolStore.get(i).getWorkername()));
			}
			if (highlightedUsedGeoToolStore.get(i).getPickUpPlace().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(getHighlightedUsedGeoToolStore().get(i).getPickUpPlace().toLowerCase());
				createEndIndexStore();
				highlightedUsedGeoToolStore.get(i).setPickUpPlace(createHighlightedString(highlightedUsedGeoToolStore.get(i).getPickUpPlace()));
			}
			if (highlightedUsedGeoToolStore.get(i).getPutDownPlace().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(getHighlightedUsedGeoToolStore().get(i).getPutDownPlace().toLowerCase());
				createEndIndexStore();
				highlightedUsedGeoToolStore.get(i).setPutDownPlace(createHighlightedString(highlightedUsedGeoToolStore.get(i).getPutDownPlace()));
			}
			if (highlightedUsedGeoToolStore.get(i).getComment().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(getHighlightedUsedGeoToolStore().get(i).getComment().toLowerCase());
				createEndIndexStore();
				highlightedUsedGeoToolStore.get(i).setComment(createHighlightedString(highlightedUsedGeoToolStore.get(i).getComment()));
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
