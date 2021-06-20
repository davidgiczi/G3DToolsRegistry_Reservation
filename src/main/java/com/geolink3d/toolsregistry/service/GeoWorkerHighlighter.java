package com.geolink3d.toolsregistry.service;

import java.util.ArrayList;
import java.util.List;
import com.geolink3d.toolsregistry.model.GeoWorker;


public class GeoWorkerHighlighter {
	
	private List<GeoWorker> highlightedGeoWorkerStore;
	private String searchedExpression;
	private List<Integer> beginIndexStore;
	private List<Integer> endIndexStore;
	private final String preTag = "<span style=\"background-color: #3996f3;\">";
	private final String postTag = "</span>";


	public GeoWorkerHighlighter(List<GeoWorker> inputWorkers) {
		highlightedGeoWorkerStore = inputWorkers;
	}
			
	public List<GeoWorker> getHighlightedGeoWorkerStore() {
		return highlightedGeoWorkerStore;
	}

	public void setSearchedExpression(String searchedExpression) {
		this.searchedExpression = searchedExpression.toLowerCase();
	}


	public void createHighlightedGeoWorkerStore() {
		

		for (int i = 0; i < highlightedGeoWorkerStore.size(); i++) {

			if (highlightedGeoWorkerStore.get(i).getFirstname().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedGeoWorkerStore.get(i).getFirstname().toLowerCase());
				createEndIndexStore();
				highlightedGeoWorkerStore.get(i).setFirstname(createHighlightedString(highlightedGeoWorkerStore.get(i).getFirstname()));
			}
		    if(highlightedGeoWorkerStore.get(i).getLastname().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedGeoWorkerStore.get(i).getLastname().toLowerCase());
				createEndIndexStore();
				highlightedGeoWorkerStore.get(i).setLastname(createHighlightedString(highlightedGeoWorkerStore.get(i).getLastname()));
			}
			if(highlightedGeoWorkerStore.get(i).getUsername().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedGeoWorkerStore.get(i).getUsername().toLowerCase());
				createEndIndexStore();
				highlightedGeoWorkerStore.get(i).setUsername(createHighlightedString(highlightedGeoWorkerStore.get(i).getUsername()));
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
