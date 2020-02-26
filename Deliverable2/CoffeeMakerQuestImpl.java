import java.util.*;

enum Item {
	NONE,
	COFFEE,
	CREAM,
	SUGAR
}

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {
	
	private static Player player;
	private static ArrayList<Room> roomList;
	private static Integer currentRoom;
	private static Boolean drank;
	
	CoffeeMakerQuestImpl() {
		// TODO
		roomList = new ArrayList<Room>(5);
		currentRoom = null;
		drank = false;
	}
	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() {
		// TODO
		return(drank);
	}

	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) {
		// TODO
		player = p;
	}
	
	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) {
		// TODO
		if(room == null || !roomList.isEmpty()) {
			return false;
		}
		roomList.add(room);
		//System.out.println(roomList[0].toString());
		return true;
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the northern-most room
	 * @param northDoor string to label the south door of room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) {
		if (room == null || northDoor == null || southDoor == null || roomList.isEmpty()) {
			return false;
		}
		for(Room vroom : roomList) {
			if(room.getAdjective() == vroom.getAdjective() || room.getFurnishing() == vroom.getFurnishing()) {
				return false;
			}
		}

		roomList.get(roomList.size() - 1).setNorthDoor(northDoor);
		roomList.add(room);
		room.setSouthDoor(southDoor);
		return true;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */ 
	public Room getCurrentRoom() {
		if(currentRoom == null) {
			return null;
		}
		return roomList.get(currentRoom);
	}
	
	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		if(!roomList.contains(room)) return false;
		currentRoom = roomList.indexOf(room);
		return true;
	}
	
	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return command prompt string
	 */
	public String getInstructionsString() {
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}
	
	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {
		// TODO
		
		String str = "";
		switch(cmd) {
		case "I":
		case "i":
			str = "";
			if(player.checkCoffee()) {
				str += "You have a cup of delicious coffee.\n";
			}
			else {
				str += "YOU HAVE NO COFFEE!\n";
			}
			if(player.checkCream()) {
				str += "You have some fresh cream.\n";
			}
			else {
				str += "YOU HAVE NO CREAM!\n";
			}
			if(player.checkSugar()) {
				str += "You have some tasty sugar.\n";
			}
			else {
				str += "YOU HAVE NO SUGAR!\n";
			}
			return str;
		
		case "L":
		case "l":
			switch(roomList.get(currentRoom).getItem().toString()) {
			case "CREAM":
				player.addItem(Item.CREAM);
				return "There might be something here...\nYou found some creamy cream!\n";
				
			}
			
		case "N":
		case "n":
			if(currentRoom + 1 == roomList.size()) {
				return "Can't go norther bro!\n";
			}
			currentRoom++;
			return "";
			
		case "D":
		case "d":
			drank = true;
			boolean Coffee = player.checkCoffee(), Cream = player.checkCream(), Sugar = player.checkSugar(); 
			
			return funResults(Coffee, Cream, Sugar);
			
//			if(Coffee) {
//				if(Cream){
//					if(Sugar) {					
//						return "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n";
//				
//						}
//					else {
//						return "You have a cup of delicious coffee.\nYou have some fresh cream.\n\nAn almost perfect coffee cup ruined by the fact that it tastes too much like coffee without sugar.\n You Lose!";
//					}
//				}
//				else {
//					if(Sugar) {
//						return "You have a cup of delicious coffee.\nYou have some tasty sugar.\n\nYou drink a cup of joe whose only flaw is that bitter taste left by the lack of cream.\n You Lose!";
//					}
//					else {
//						return "You have a cup of delicious coffee.\n\nIf only your soul were as dark as this coffee, you might actually enjoy it.\n You Lose!";
//					}
//				}
//			}
//			else {
//				if(Cream) {
//					if(Sugar) {
//						return "You have some fresh cream.\nYou have some tasty sugar.\n\nIronically enough, the two best parts of the coffee experience don't work well without the actual coffee.\n You Lose!";
//					}
//					else {
//						return "You have some fresh cream.\nYou're so thirsty, you're starting to find the cream tempting on its own.\n\nYou Lose!";
//					}
//				}
//				else {
//					if(Sugar) {
//						return "You have some tasty sugar.\n\nYou fixing for a sugar fix but you're not that desperate... yet.\n\nYou Lose!";
//					}
//					else {
//						return  "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";
//					}
//				}
//			}
		case "S":
		case "s":
			if(currentRoom == 0) {
				return "A door in that direction does not exist.\n";
			}
			currentRoom--;
			return "";
		
		case "H":
		case "h":
			return getInstructionsString();
			
		default:
			return "Please enter a valid command.\nFor a full list of commands enter \"H\"";
		}
	}
	
	private String funResults(boolean Coffee, boolean Cream, boolean Sugar) {
		String fun = "";
		fun += (Coffee ? 1 : 0);
		fun += (Cream ? 1 : 0);
		fun += (Sugar ? 1 : 0);
		
		switch(fun) {
		case "000":
			return "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";
		case "001":
			return "You have some tasty sugar.\n\nYou fixing for a sugar fix but you're not that desperate... yet.\n\nYou Lose!";
		case "010":
			return "You have some fresh cream.\nYou're so thirsty, you're starting to find the cream tempting on its own.\n\nYou Lose!";
		case "011":
			return "You have some fresh cream.\nYou have some tasty sugar.\n\nIronically enough, the two best parts of the coffee experience don't work well without the actual coffee.\n You Lose!";
		case "100":
			return "You have a cup of delicious coffee.\n\nIf only your soul were as dark as this coffee, you might actually enjoy it.\n You Lose!";
		case "101":
			return "You have a cup of delicious coffee.\nYou have some tasty sugar.\n\nYou drink a cup of joe whose only flaw is that bitter taste left by the lack of cream.\n You Lose!";
		case "110":
			return "You have a cup of delicious coffee.\nYou have some fresh cream.\n\nAn almost perfect coffee cup ruined by the fact that it tastes too much like coffee without sugar.\n You Lose!";
		case "111":
			return "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n";
		}
		return "You'll never see this if everything goes according to plan.";
		
	}

	
}
