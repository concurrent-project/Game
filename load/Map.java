package project.load;

import java.util.ArrayList;

import project.entities.Boundary;
import project.entities.Item;

public class Map {

	public ArrayList<Boundary> boundaries;
	public ArrayList<Item> items;
	public String name;
	
	public Map(ArrayList<Boundary> boundaries, ArrayList<Item> items, String name){
		this.boundaries = boundaries;
		this.items = items;
		this.name = name;
	}

}
